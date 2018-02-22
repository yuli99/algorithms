
/*************************************************************************
 * Name:  Yuli Wei
 *
 * Algorithm Project: Pattern Recognition
 * Using stdlib.jar
 *
 * Compilation:  javac Fast.java
 * Execution:   java Fast file.txt
 * Dependencies:  Point.java 
 *
 * Description: A faster, sorting-based solution for this problem. The order
 * of growth of the running time should be N^2logN in the worst case and it 
 * uses space proportional to N. 
 *
 *************************************************************************/

import java.util.Arrays;

public class Fast {
	
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
        	Point curr = points[i];
        	Point[] slopes = new Point[N-1];
        	
        	for (int j = 0; j < i; j++) {
        		slopes[j] = points[j];
        	}
        	for (int j = i; j < N-1; j++) {
        		slopes[j] = points[j+1];
        	}
        	
        	Arrays.sort(slopes, curr.SLOPE_ORDER);
        	
        	int index = 0;
        	int count = 1;
        	
        	while (index + count < N-1) {
        		if (curr.slopeTo(slopes[index + count]) == curr.slopeTo(slopes[index])) {
        			count++;
        		}
        		else {
        			Arrays.sort(slopes, index, index + count - 1);
        			if (count > 2 && curr.compareTo(slopes[index]) < 0) {
        				StdOut.print(curr);
        				for (int k = 0; k < count; k++) {
        					StdOut.print(" -> " + slopes[index + k]);
        				}
        				StdOut.println();
        				curr.drawTo(slopes[index + count - 1]);
        			}
        			
        			index += count;
        			count = 1;
        		}
        	}
        	
        	Arrays.sort(slopes, index, index + count - 1);
        	if (count > 2 && curr.compareTo(slopes[index]) < 0) {
				StdOut.print(curr);
				for (int k = 0; k < count; k++) {
					StdOut.print(" -> " + slopes[index + k]);
				}
				StdOut.println();
				curr.drawTo(slopes[index + count - 1]);
			}
        }
        
        // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius(); 
	}

}
