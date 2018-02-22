/*************************************************************************
 * Name:  Yuli Wei
 *
 * Algorithm Project: Pattern Recognition
 * Using stdlib.jar, which is part of the algs4.jar
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: none
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                             // y coordinate

    // My Definition of comparator
    private class SlopeOrder implements Comparator<Point>  {
    	public int compare(Point p, Point q) {
    		if (p == null || q == null) 
    			throw new NullPointerException();
    		
    		double diff = slopeTo(p) - slopeTo(q);
    		
    		if (diff > 0) 
    			return 1;
    		else if (diff < 0) 
    			return -1;
    		else 
    			return 0;
    	}
    }
    
    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
    	if (that == null) 
    		throw new NullPointerException();
    	
        double slope;
        int dx = that.x - this.x;
        int dy = that.y - this.y;
        
        if (dx == 0 && dy == 0)  
        	{ slope = Double.NEGATIVE_INFINITY; }       
        else if (dx == 0) 
        	{ slope = Double.POSITIVE_INFINITY; }
        else if (dy == 0)
        	{ slope = 0; }
        else 
        	{ slope = (double) dy / (double) dx; }
        
        return slope;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
    	if (that == null) 
    		throw new NullPointerException();
    	
    	int dx = that.x - this.x;
        int dy = that.y - this.y;
        
        if (dy > 0 || (dy == 0 && dx > 0)) 
        	return -1;
        else if (dy == 0 && dx == 0) 
        	return 0;
        else
        	return 1;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    	Point p1 = new Point(20, 50);
    	Point p2 = new Point(10, 40);
        
    	StdOut.println("point 1 compared to point 2:  " + p1.compareTo(p2));
    	StdOut.println("Slope of 2 points:  " + p1.slopeTo(p2));
    }
}
