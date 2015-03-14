import java.util.*;
import java.awt.Point;
import static java.lang.Math.max;

public class Algorithms
{
	public static void floodfill(int[][] array, Point node, int target, int replacement)
	{
		if (array[node.y][node.x] != target)
			return;
		Queue<Point> Q = new LinkedList<Point>();
		Q.add(node);
		while (!Q.isEmpty())
		{
			Point n = Q.poll();
			if (array[n.y][n.x] == target)
			{
				int w, e;
				w = n.x;
				e = n.x;
				while (w > 0 && array[n.y][w - 1] == target)
					w -= 1;
				while (e < array[n.y].length - 1 && array[n.y][e + 1] == target)
					e += 1;
				for (int i = w; i <= e; i++)
				{
					array[n.y][i] = replacement;
					if (n.y > 0 && array[n.y - 1][i] == target)
						Q.add(new Point(i, n.y - 1));
					if (n.y < array.length - 1 && array[n.y + 1][i] == target)
						Q.add(new Point(i, n.y + 1));
				}
			}
		}
	}

	public static void swap(int[] array, int i, int j)
	{
		int v = array[i];
		array[i] = array[j];
		array[j] = v;
	}

	public static void reverse(int[] array, int start)
	{
		int len = array.length - start;
		for (int i = 0; i < len / 2; i++)
		{
			swap(array, start + i, array.length - i - 1);
		}
	}

	public static boolean nextPermutation(int[] array)
	{
		int k = array.length - 2;
		while (k >= 0 && array[k] >= array[k + 1])
			k--;
		if (k < 0)
			return false;
		int l = array.length - 1;
		while (array[k] >= array[l])
			l--;
		swap(array, k, l);
		reverse(array, k + 1);
		return true;
	}
	
	public static long[] getUnorderedWays(int[] values, int max)
	{
		long[][] ways = new long[values.length][max + 1];
		for (int i = 0; i < values.length; i++)
		{
			int cur = values[i];
			ways[i][0] = 1;
			for (int j = 1; j <= max; j++)
			{
				int prev = j - cur;
				ways[i][j] = 0;
				if (prev >= 0)
					ways[i][j] += ways[i][prev];
				if (i > 0)
					ways[i][j] += ways[i - 1][j];
			}
		}
		return ways[values.length - 1];
	}

	public static long[] getOrderedWays(int[] values, int max)
	{
		long ways[] = new long[max + 1];
		int cur;
		ways[0] = 1;
		for (int j = 1; j <= max; j++)
		{
			cur = 0;
			for (int i = 0; i < values.length; i++)
			{
				int prev = j - values[i];
				if (prev >= 0)
					cur += ways[prev];
			}
			ways[j] = cur;
		}
		return ways;
	}

	public static int longestIncreasingSubsequence(int[] values)
	{
		int n = values.length;
		int[] best = new int[n];
//		int[] prev = new int[n];
		best[0] = 1;
//		prev[0] = -1;
		for (int i = 1; i < n; i++)
		{
			int cur = values[i];
			best[i] = 0;
			for (int j = 0; j < i; j++)
			{
				if (cur > values[j])
				{
					if (best[j] > best[i])
					{
						best[i] = best[j];
//						prev[i] = j;
					}
				}
			}
			best[i]++;
		}
		int maxind = 0;
		for (int j = 0; j < n; j++)
			if (best[j] > best[maxind])
				maxind = j;
		return best[maxind];
//		int[] path = new int[best[maxind]];
//		path[path.length - 1] = maxind;
//		for (int i = path.length - 2; i >= 0; i--)
//			path[i] = prev[i + 1];
//		return path;
	}

	public static int maximumSubarray(int[] values)
	{
		int maxHere = 0;
		int totalMax = 0;
		for (int x : values)
		{
			maxHere = max(0, maxHere + x);
			totalMax = max(totalMax, maxHere);
		}
		return totalMax;
	}

	public static int longestCommonSubsequence(char[] s1, char[] s2)
	{
		int m = s1.length, n = s2.length;
		int maxlen[][] = new int[m + 1][n + 1];
		for (int j = 0; j <= m; j++)
			maxlen[j][0] = 0;
		for (int i = 0; i <= n; i++)
			maxlen[0][i] = 0;
		for (int j = 0; j < m; j++)
		{
			for (int i = 0; i < n; i++)
			{
				if (s1[j] == s2[i])
					maxlen[j + 1][i + 1] = maxlen[j][i] + 1;
				else
					maxlen[j + 1][i + 1] = max(maxlen[j + 1][i], maxlen[j][i + 1]);
			}
		}
		return maxlen[m][n];
	}

	public static int longestCommonSubstring(char[] s1, char[] s2)
	{
		int m = s1.length, n = s2.length;
		int maxlen[][] = new int[m + 1][n + 1];
		int best = 0, cur;
		for (int j = 0; j <= m; j++)
			maxlen[j][0] = 0;
		for (int i = 0; i <= n; i++)
			maxlen[0][i] = 0;
		for (int j = 0; j < m; j++)
		{
			for (int i = 0; i < n; i++)
			{
				if (s1[j] == s2[i])
					cur = maxlen[j][i] + 1;
				else
					cur = 0;
				maxlen[j + 1][i + 1] = cur;
				best = max(best, cur);
			}
		}
		return best;
	}

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		String s1 = sc.next(), s2 = sc.next();
		System.out.println(longestCommonSubstring(s1.toCharArray(), s2.toCharArray()));
	}
}
