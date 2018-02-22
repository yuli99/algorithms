/************************************************************************** 
 * Author: Yuli Wei
 * 
 * Algorithm project: Percolation
 * using stdlib.jar, which is part of algs4.jar
 *
 * Compilation: javac PercolationStats.java
 * Execution: java PercolationStats N T
 * 
 ***********************************************************************/


public class PercolationStats {
	private int runTimes;
	private Percolation percolation;
	private double[] openFraction;
	
	//
	public PercolationStats(int N, int T) {
		if(N < 1 || T < 1 ) 
			throw new IllegalArgumentException("illegal gridSize or runTimes");
		
		runTimes = T;
		openFraction = new double[runTimes];
		
		for(int i=0; i < runTimes; i++) {
			int count = 0;
			percolation = new Percolation(N);
			
			while(!percolation.percolates()) {
				int row, col;
				
				do {
					row = StdRandom.uniform(1, N+1);
					col = StdRandom.uniform(1, N+1);
				} while(percolation.isOpen(row, col));
									
				percolation.open(row, col);				
	            count++;
			}
			
			openFraction[i] = (double)count / (double)(N*N);
		}
	}
	
	
	public double mean() {			
		return StdStats.mean(openFraction);
	}
	
	
	public double stddev() {		
		return StdStats.stddev(openFraction);
	}
	
	
	public double confidenceLo() {
		return mean() - 1.96*stddev()/Math.sqrt(runTimes);		
	}

	
	public double confidenceHi() {
		return mean() + 1.96*stddev()/Math.sqrt(runTimes);	
	}

	
	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		
		PercolationStats tests = new PercolationStats(N, T);
		
		StdOut.println("mean                    = " + tests.mean());
		StdOut.println("stddev                  = " + tests.stddev());
		StdOut.println("95% confidence interval = " + tests.confidenceLo()
				+ " , " + tests.confidenceHi());

	}

}
