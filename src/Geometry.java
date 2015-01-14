import java.util.*;

public class Algorithms
{
	public static double polygonArea(double[] X, double[] Y) 
	{
		double area = 0;
		int j = X.length - 1;

		for (int i = 0; i < X.length; i++)
		{
			area += (X[j] + X[i]) * (Y[j] - Y[i]);
			j = i;
		}

		return Math.abs(area / 2);
	}

	public static class Vector
	{
		int x, y;
		public Vector(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		public double angle()
		{
			return Math.atan2(y, x);
		}
		
		public double angleTo(Vector v) // returns positive clockwise angle
		{
			double ans = angle() - v.angle();
			if (ans < 0)
				ans += 2 * Math.PI;
			return ans;
		}
		
		public int mag2()
		{
			return y * y + x * x;
		}
		
		@Override
		public String toString()
		{
			return "<" + x + ", " + y + ">";
		}
	}

	public static class Point implements Comparable<Point>
	{
		int x, y;
		public Point(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		public Vector to(Point p)
		{
			return new Vector(p.x - x, p.y - y);
		}
		
		@Override
		public boolean equals(Object o)
		{
			Point p = (Point)o;
			return (p.x == x && p.y == y);
		}
		
		@Override
		public String toString()
		{
			return "(" + x + ", " + y + ")";
		}

		@Override
		public int compareTo(Point p)
		{
			if (y < p.y || y == p.y && x < p.x)
				return -1;
			if (y > p.y || y == p.y && x > p.x)
				return 1;
			return 0;
		}
	}
	
	public static LinkedList<Point> giftWrapping(Point[] points)
	{
		int n = points.length;
		LinkedList<Point> hull = new LinkedList<Point>();
		int min, miny;
		min = 0;
		miny = points[0].y
		for (int i = 1; i < n; i++)
		{
			if (points[i].y < miny < 0)
			{
				min = i;
				miny = points[i].y;
			}
		}
		Point first = points[min];
		Point cur = first;
		Vector curvec = new Vector(1, 0);
		while (!cur.equals(first) || hull.isEmpty())
		{
			hull.add(cur);
			double minang = 3 * Math.PI;
			int minmag = 0;
			for (int i = 0; i < n; i++)
			{
				Point p = points[i];
				if (p.equals(cur))
					continue;
				double angle = curvec.angleTo(cur.to(p));
				if ((angle > 0 && angle < minang) ||
					(angle == minang && cur.to(p).mag2() > minmag))
				{
					min = i;
					minang = angle;
					minmag = cur.to(p).mag2();
				}
			}
			curvec = points[min].to(cur);
			cur = points[min];
		}
		return hull;
	}

	public static class Line
	{
		double x1, y1, x2, y2;

		public Line(double x1, double y1, double x2, double y2)
		{
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}

		public Point intersection(Line ol)
		{
			double x3 = ol.x1, y3 = ol.y1;
			double x4 = ol.x2, y4 = ol.y2;

			double d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
			if (d == 0)
				return null; // lines are parallel

			double Px = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
			double Py = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - xy) * (x3 * y4 - y3 * x4)) / d;
			return new Point(Px, Py);
		}
	}

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		int k = sc.nextInt();
		int[] n = new int[k], a = new int[k];
		for (int i = 0; i < k; i++)
		{
			a[i] = sc.nextInt();
			n[i] = sc.nextInt();
		}
		System.out.println(crt(a, n));
	}
}
