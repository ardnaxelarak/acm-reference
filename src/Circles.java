import java.util.*;

public class Circles
{
    static class Point
    {
        public double x, y;
        public Point(double x, double y)
        {
            this.x = x;
            this.y = y;
        }
        public double dist2to(Point p1)
        {
            return (x - p1.x) * (x - p1.x) + (y - p1.y) * (y - p1.y);
        }
        public Point offset(double xo, double yo)
        {
            return new Point(x + xo, y + yo);
        }
        public static Point midpoint(Point p1, Point p2)
        {
            return new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
        }
    }

    public static Point[] intersections(Point c1, double r1,
                                        Point c2, double r2)
    {
        double a, h;
        double d, d2;
        Point c3;
        
        d2 = c1.dist2to(c2);
        d = Math.sqrt(d2);
        
        if (d > r1 + r2)
            return new Point[0];
        else if (d < Math.abs(r1 - r2))
            return new Point[0];
        else if (d == 0)
            return new Point[0];
        
        a = (r1 * r1 - r2 * r2 + d2) / (2 * d);
        h = Math.sqrt(r1 * r1 - a * a);

        c3 = c1.offset(a * (c2.x - c1.x) / d, a * (c2.y - c1.y) / d);

        if (h == 0)
            return new Point[] {c3};
        else
            return new Point[] {c3.offset(h * (c2.y - c1.y) / d,
                                         -h * (c2.x - c1.x) / d),
                                c3.offset(-h * (c2.y - c1.y) / d,
                                           h * (c2.x - c1.x) / d)};
    }

    public static Point[] tangents(Point c, double r, Point p)
    {
        double d, d2;
        
        d2 = c.dist2to(p);
        d = Math.sqrt(d2);
        
        if (d < r)
            return new Point[0];
        
        return intersections(c, r, Point.midpoint(p, c), d / 2);
    }

    public static Point[] internaltangents(Point c1, double r1,
                                           Point c2, double r2)
    {
        Point hcen = internalHomotheticCenter(c1, r1, c2, r2);
        if (hcen == null)
            return new Point[0];
        Point[] tan1 = tangents(c1, r1, hcen);
        Point[] tan2 = tangents(c2, r2, hcen);
        Point[] ret = new Point[2 * tan1.length];
        for (int i = 0; i < tan1.length; i++)
        {
            ret[2 * i] = tan1[i];
            ret[2 * i + 1] = tan2[i];
        }
        return ret;
    }
    
    public static Point[] externaltangents(Point c1, double r1,
                                           Point c2, double r2)
    {
        Point hcen = externalHomotheticCenter(c1, r1, c2, r2);
        if (hcen == null)
            return new Point[0];
        Point[] tan1 = tangents(c1, r1, hcen);
        Point[] tan2 = tangents(c2, r2, hcen);
        Point[] ret = new Point[2 * tan1.length];
        for (int i = 0; i < tan1.length; i++)
        {
            ret[2 * i] = tan1[i];
            ret[2 * i + 1] = tan2[i];
        }
        return ret;
    }

    public static Point intersection(Point l1p1, Point l1p2,
                                     Point l2p1, Point l2p2)
    {
        if (l1p1.x == l1p2.x)
        {
            if (l2p1.x == l2p2.x)
                return null;
            return new Point(l1p1.x, (l2p1.y - l2p2.y) / (l2p1.x - l2p2.x) *
                                     (l1p1.x - l2p1.x) + l2p1.y);
        }
        else if (l2p1.x == l2p2.x)
        {
            return new Point(l2p1.x, (l1p1.y - l1p2.y) / (l1p1.x - l1p2.x) *
                                     (l2p1.x - l1p1.x) + l1p1.y);
        }
        else
        {
            double m1, m2;
            m1 = (l1p1.y - l1p2.y) / (l1p1.x - l1p2.x); 
            m2 = (l2p1.y - l2p2.y) / (l2p1.x - l2p2.x);
            if (m1 == m2)
                return null;
            double x1, y1, x2, y2, x, y;
            x1 = l1p1.x;
            y1 = l1p1.y;
            x2 = l2p1.x;
            y2 = l2p1.y;
            x = (m1 * x1 - y1 - m2 * x2 + y2) / (m1 - m2);
            y = (m1 * x - m1 * x1) + y1;
            return new Point(x, y);
        }
    }
    
    public static Point internalHomotheticCenter(Point c1, double r1,
                                                 Point c2, double r2)
    {
        return intersection(c1.offset(0, -r1), c2.offset(0, r2),
                            c1.offset(0, r1), c2.offset(0, -r2));
    }
    
    public static Point externalHomotheticCenter(Point c1, double r1,
                                                 Point c2, double r2)
    {
        return intersection(c1.offset(0, r1), c2.offset(0, r2),
                            c1.offset(0, -r1), c2.offset(0, -r2));
    }
}
