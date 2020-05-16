/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (that.x == this.x && that.y == this.y) {
            return Double.NEGATIVE_INFINITY;
        }
        else if (that.y == this.y) {
            return +0;
        }
        else {
            return (that.y - this.y)/(that.x - this.x);
        }
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        if (this.y < that.y || (this.y == that.y && this.x < that.x)) {
            return -1;
        }
        else if (this.y > that.y || (this.y == that.y && this.x > that.x)) {
            return -1;
        }
        else {
            return 0;
        }
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new PointSlopeOrder();
    }

    private class PointSlopeOrder implements Comparator<Point> {
        public int compare(Point a, Point b) {
            Double aSlope = slopeTo(a);
            Double bSlope = slopeTo(b);

            if (aSlope < bSlope) {
                return -1;
            }
            else if (bSlope < aSlope) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        StdOut.println("Creating point A in (1,1)");
        Point aPoint = new Point(1,1);
        StdOut.println("Creating point B in (2,2)");
        Point bPoint = new Point(2,2);
        StdOut.println("Slope between A and B (must be 1: )");
        Double slopeAB = aPoint.slopeTo(bPoint);
        StdOut.println(slopeAB.toString());
        StdOut.println("Creating point C in (2,3)");
        Point cPoint = new Point(2,3);
        StdOut.println("Creating point D in (2,4)");
        Point dPoint = new Point(2,4);
        StdOut.println("Ordering B, C and D by their slope between A");
        Point[] points = new Point[]{ bPoint, dPoint, cPoint };
        StdOut.println("Printing ordered array, must be (2,2), (2,3) and (2,4)");
        Arrays.sort(points, aPoint.slopeOrder());
        for (int i = 0; i < points.length; i++) {
            StdOut.println(points[i].toString());
        }
    }
}