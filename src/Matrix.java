import java.util.Arrays;

public class Matrix
{
    static class DimensionMismatchException extends Exception {}
    
    int[][] elements;
    int m, n;
    public Matrix(int m, int n)
    {
        this.m = m;
        this.n = n;
        elements = new int[m][n];
    }
    
    public Matrix(Matrix arg)
    {
        m = arg.m;
        n = arg.n;
        elements = new int[m][n];
        for (int i = 0; i < m; i++)
        {
            elements[i] = Arrays.copyOf(arg.elements[i], n);
        }
    }
    
    public Matrix transform(int A, int B)
    {
        Matrix ans = new Matrix(m, n);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
            {
                ans.elements[i][j] = A * elements[i][j] + B;
            }
        return ans;
    }
    
    public Matrix transpose()
    {
        Matrix ans = new Matrix(n, m);
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < m; j++)
            {
                ans.elements[i][j] = elements[j][i];
            }
        }
        return ans;
    }

    public Matrix add(int c)
    {
        return transform(0, c);
    }
    
    public Matrix add(Matrix arg) throws DimensionMismatchException
    {
        if (m != arg.m || n != arg.n)
            throw new DimensionMismatchException();
        Matrix ans = new Matrix(m, n);
        for (int i = 0; i < m; i++)
        {
            for (int j = 0; j < n; j++)
            {
                ans.elements[i][j] = elements[i][j] + arg.elements[i][j];
            }
        }
        return ans;
    }
    
    public Matrix multiply(int c)
    {
        return transform(c, 0);
    }
    
    public Matrix multiply(Matrix arg) throws DimensionMismatchException
    {
        if (n != arg.m)
            throw new DimensionMismatchException();
        Matrix ans = new Matrix(m, arg.n);
        for (int i = 0; i < m; i++)
        {
            for (int j = 0; j < arg.n; j++)
            {
                int sum = 0;
                for (int k = 0; k < n; k++)
                    sum += elements[i][k] * arg.elements[k][j];
                ans.elements[i][j] = sum;
            }
        }
        return ans;
    }
    
    // counterclockwise rotation; does not work for ints 
    public static Matrix rotationMatrix(double theta)
    {
        Matrix ans = new Matrix(2, 2);
        ans.elements[0][0] = (int)Math.cos(theta);
        ans.elements[0][1] = -(int)Math.sin(theta);
        ans.elements[1][0] = (int)Math.sin(theta);
        ans.elements[1][1] = (int)Math.cos(theta);
        return ans;
    }

    public int det() throws DimensionMismatchException
    {
        if (m != n)
            throw new DimensionMismatchException();
        
        switch (m)
        {
        case 2:
            return elements[0][0] * elements[1][1] -
                   elements[0][1] * elements[1][0];
        case 3:
            return  elements[0][0] * elements[1][1] * elements[2][2] + 
                    elements[0][1] * elements[1][2] * elements[2][0] +
                    elements[0][2] * elements[1][0] * elements[2][1] -
                    elements[0][0] * elements[1][2] * elements[2][1] -
                    elements[0][1] * elements[1][0] * elements[2][2] -
                    elements[0][2] * elements[1][1] * elements[2][0];
        default:
            int[] list = new int[n];
            for (int i = 0; i < n; i++)
                list[i] = i;
            int sum = 0;
            int product = 1;
            int parity = -1;
            for (int i = 0; i < n; i++)
                product *= elements[i][i];
            sum += product;
            while (Algorithms.nextPermutation(list))
            {
                product = parity;
                for (int i = 0; i < n; i++)
                    product *= elements[i][list[i]];
                sum += product;
                parity *= -1;
            }
            return sum;
        }
    }
    
    public int rows()
    {
        return m;
    }
    
    public int columns()
    {
        return n;
    }
}
