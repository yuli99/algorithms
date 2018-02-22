/*************************************************************************
 * Name:  Yuli Wei
 * 
 * Algorithm Project: Pattern Recognition
 * Using stdlib.jar
 *
 * Compilation:  javac Brute.java
 * Execution:   java Brute file.txt
 * Dependencies: Point.java
 *
 * Description: read the number of points N and their coordinates from a txt
 * file. Examines 4 points at a time and checks whether they all lie on the
 * same line segment, printing out any such line segments to standard output
 * and drawing them using standard drawing.  Running time for worst case should
 * be N^4 and the used space should be proportional to N.
 *
 *************************************************************************/

import java.util.Arrays;

public class Brute {

    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }
        
        Arrays.sort(points);
        
        for (int i = 0; i < N; i++) {
        	for (int j = i+1; j < N; j++) {
        		for (int k = j+1; k < N; k++) {
        			double pq = points[i].slopeTo(points[j]);
        			double pr = points[i].slopeTo(points[k]);
        			if (pq == pr) {
        				for (int l = k+1; l < N; l++) {
        					double ps = points[i].slopeTo(points[l]);
            				if (ps == pq) {
            					StdOut.println(points[i] + " -> " + points[j]
            							+ " -> " + points[k] + " -> " +points[l]);
            					points[i].drawTo(points[l]);
            				}
        				}
        			}
        		}
        	}
        }
        
        // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius(); 
        
	}

}
