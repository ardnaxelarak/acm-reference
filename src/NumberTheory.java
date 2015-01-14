import java.util.*;

public class NumberTheory
{
	public static int gcd(int a, int b)
	{
		if (b > a)
			return gcd(b, a);
		int rem = a % b;
		while (rem > 0)
		{
			a = b;
			b = rem;
			rem = a % b;
		}
		return b;
	}
	
	public static int gcd(int... nums)
	{
		if (nums.length == 0)
			return 0;
		else if (nums.length == 1)
			return nums[0];
		int cur = gcd(nums[0], nums[1]);
		for (int i = 2; i < nums.length; i++)
			cur = gcd(cur, nums[i]);
		return cur;
	}

	public static int lcm(int a, int b)
	{
		return a * (b / gcd(a, b));
	}

	public static int lcm(int... nums)
	{
		int cur = 1;
		for (int i = 0; i < nums.length; i++)
			cur = lcm(cur, nums[i]);
		return cur;
	}

	/* returns an array bezout such that
	 * bezout[0] = gcd(a, b) and
	 * bezout[1] * a + bezout[2] * b = bezout[0] */
	public static int[] bezout(int a, int b)
	{
		if (b < 0)
		{
			int[] flip = bezout(a, -b);
			return new int[] {flip[0], flip[1], -flip[2]};
		}
		if (b > a)
		{
			int[] flip = bezout(b, a);
			return new int[] {flip[0], flip[2], flip[1]};
		}
		int rem = a % b;
		int quot = a / b;
		int s3, s2, s1, t3, t2, t1;
		s2 = 1;
		s1 = 0;
		t2 = 0;
		t1 = 1;
		while (rem > 0)
		{
			t3 = t2;
			t2 = t1;
			t1 = t3 - t2 * quot;
			s3 = s2;
			s2 = s1;
			s1 = s3 - s2 * quot;
			a = b;
			b = rem;
			rem = a % b;
			quot = a / b;
		}
		return new int[] {b, s1, t1};
	}

	/* Returns an array enumerating the solutions to the dipohantine
	 * equation ax + by = c, or null if there are no solutions.
	 * for all integers t,
	 * a * (dioph[0] + t * dioph[1]) + b * (dioph[2] - t * dioph[3]) = c */
	public static int[] dioph(int a, int b, int c)
	{
		int[] bez = bezout(a, b);
		int d = bez[0];
		if (c % d != 0)
			return null;
		int x0, y0;
		int[] dioph = new int[4];
		dioph[0] = bez[1] * (c / d); // x0
		dioph[1] = b / d;
		dioph[2] = bez[2] * (c / d); // y0
		dioph[3] = a / d;
		return dioph;
	}

	/* Returns the inverse of a mod n, or 0 if no such inverse exists */
	public static int inverse(int a, int n)
	{
		int[] dioph = dioph(a, n, 1);
		if (dioph == null)
			return 0;
		int inv = dioph[0] % dioph[1];
		if (inv < 0)
			inv += dioph[1];
		return inv;
	}

	/* Returns the smallest nonnegative solution to the Chinese Remainder Theorem
	 * system of equations, or -1 if no solution exists */
	public static int crt(int[] a, int[] n)
	{
		int len = a.length;
		if (n.length != len)
			return -1;
		int N = 1;
		for (int i = 0; i < len; i++)
			N *= n[i];
		int sol = 0;
		for (int i = 0; i < len; i++)
		{
			int Nk = N / n[i];
			int yk = inverse(Nk, n[i]);
			if (yk == 0)
				return -1;
			sol += a[i] * yk * Nk;
			sol %= N;
		}
		return sol;
	}

	public static int[] triple(int m, int n)
	{
		int[] ret = new int[3];
		ret[0] = m * m - n * n;
		ret[1] = 2 * m * n;
		ret[2] = m * m + n * n;
		return ret;
	}
	
	// returns a list of all primes less than max
	public static int[] primelist(int max)
	{
		LinkedList<Integer> potential = new LinkedList<Integer>();
		LinkedList<Integer> primes = new LinkedList<Integer>();
		primes.add(2);
		for (int i = 1; i < max / 2; i++)
			potential.add(2 * i + 1);
		int cur;
		ListIterator<Integer> li;
		while (!potential.isEmpty())
		{
			cur = potential.poll();
			primes.add(cur);
			li = potential.listIterator(0);
			while (li.hasNext())
			{
				if (li.next() % cur == 0)
					li.remove();
			}
		}
		int[] list = new int[primes.size()];
		li = primes.listIterator(0);
		int k = 0;
		while (li.hasNext())
			list[k++] = li.next();
		return list;
	}

	/* returns the power of p in the prime factorization of n!
	 * note that this is only valid if p is prime */
	public static int powerofp(int p, int n)
	{
		int count = 0;
		while (n > 0)
		{
			n /= p;
			count += n;
		}
		return count;
	}

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int p = sc.nextInt();
		System.out.println(powerofp(p, n));
	}
}
