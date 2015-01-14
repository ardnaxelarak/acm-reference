import java.util.*;

public class Combinatorics
{
	public static int[][] binom(int max)
	{
		int[][] coef = new int[max][];
		for (int n = 0; n < max; n++)
		{
			coef[n] = new int[n + 1];
			coef[n][0] = 1;
			coef[n][n] = 1;
		}
		for (int n = 2; n < max; n++)
			for (int k = 1; k < n; k++)
				coef[n][k] = coef[n - 1][k - 1] + coef[n - 1][k];
		return coef;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		int max = sc.nextInt();
		int[][] binom = binom(max);
		for (int n = 0; n < max; n++)
		{
			for (int k = 0; k <= n; k++)
				System.out.printf("%3d ", binom[n][k]);
			System.out.println();
		}
		System.out.println(binom[5][2]);
	}
}
