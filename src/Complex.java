public class Complex
{
	private double x, y;

	public Complex(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public Complex(Complex c)
	{
		x = c.x;
		y = c.y;
	}

	public static Complex fromPolarForm(double modulus, double argument)
	{
		return new Complex(modulus * Math.cos(argument), modulus * Math.sin(argument));
	}

	public static Complex add(Complex c1, Complex c2)
	{
		return new Complex(c1.x + c2.x, c1.y + c2.y);
	}

	public static Complex subtract(Complex c1, Complex c2)
	{
		return new Complex(c1.x - c2.x, c1.y - c2.y);
	}

	public static Complex multiply(Complex c1, Complex c2)
	{
		return new Complex(c1.x * c2.x - c1.y * c2.y, c1.x * c2.y + c1.y * c2.x);
	}

	public static Complex divide(Complex c1, Complex c2)
	{
		return Complex.fromPolarForm(c1.modulus() / c2.modulus(), c1.argument() - c2.argument());
	}

	public static Complex exp(Complex c)
	{
		return Complex.fromPolarForm(Math.exp(c.x), c.y);
	}

	public Complex log()
	{
		return new Complex(Math.log(modulus()), argument());
	}

	public Complex pow(Complex c)
	{
		Complex p = new Complex(this);
		p.takePow(c);
		return p;

	public void add(Complex c)
	{
		x += c.x;
		y += c.y;
	}

	public void subtract(Complex c)
	{
		x -= c.x;
		y -= c.y;
	}

	public void multiply(Complex c)
	{
		double real = x * c.x - y * c.y;
		y = x * c.y + y * c.x;
		x = real;
	}

	public void divide(Complex c)
	{
		double m = modulus() / c.modulus();
		double a = argument() - c.argument();
		x = m * Math.cos(a);
		y = m * Math.sin(a);
	}

	public void takeExp()
	{
		double m = Math.exp(x);
		double a = y;
		x = m * Math.cos(a);
		y = m * Math.sin(a);
	}

	public void takeLog()
	{
		double m = modulus();
		double a = argument();
		x = Math.log(m);
		y = a;
	}

	public void takePow(Complex c)
	{
		takeLog();
		multiply(c);
		takeExp();
	}

	public double modulus()
	{
		return Math.sqrt(x * x + y * y);
	}

	public double argument()
	{
		return Math.atan2(y,x);
	}

	public String toString()
	{
		if (x == 0 && y > 0)
			return String.format("i%f", y);
		else if (x == 0 && y < 0)
			return String.format("-i%f", -y);
		else if (x == 0)
			return "0";
		else if (y == 0)
			return String.format("%f", x);
		else if (y > 0)
			return String.format("%f + i%f", x, y);
		else
			return String.format("%f - i%f", x, -y);
	}

	public static void main(String[] args)
	{
		Complex c1 = new Complex(0, 1);
		Complex c2 = new Complex(c1);
		c1.takePow(c2);
		System.out.println(c1);
	}
}
