/************************************************************************** 
 * Author: Yuli Wei
 * 
 * Algorithm project: Percolation
 * 
 * Using a 1D boolean array to track the state of each cell 
 *  -- Open == true ; Blocked == false;
 *  -- first element refers to virtual-top 
 *  -- last element refers to virtual-bottom
 *  Using additional data structure to prevent "Wash-back" problem
 * 
 ***********************************************************************/


public class Percolation {
	
	private int gridSize;
	private boolean[] cellStats;
	private WeightedQuickUnionUF wqu;
	private WeightedQuickUnionUF wquWashBack;
	
	//constructor
	public Percolation(int N) {
		if(N < 1) throw new IllegalArgumentException("illegal grid size");
		gridSize = N;
		cellStats = new boolean[N*N+2];
		wqu = new WeightedQuickUnionUF(N*N+2);
		wquWashBack = new WeightedQuickUnionUF(N*N+1);
		
		//Set virtual-top and virtual-bottom cells' states to be Open
		cellStats[0] = true;
		cellStats[N*N+1] = true;
	}
	
	
	public void open(int i, int j) {
		checkBoundary(i, j);
		if(cellStats[xyTo1D(i,j)]) return; 
		
		cellStats[xyTo1D(i,j)] = true;
		
		// connect to virtual top
		if(i == 1) {
			wqu.union(0, xyTo1D(i,j));
			wquWashBack.union(0, xyTo1D(i,j));
		}
		
		// connect to virtual bottom
		if(i == gridSize) {
			wqu.union(xyTo1D(i,j), gridSize*gridSize+1);
		}
		
		// connect to top neighbor
		if(i-1 >= 1 && cellStats[xyTo1D(i-1,j)]) {
			wqu.union(xyTo1D(i,j), xyTo1D(i-1,j));
			wquWashBack.union(xyTo1D(i,j), xyTo1D(i-1,j));
		}
		
		// connect to bottom neighbor
		if(i+1 <= gridSize && cellStats[xyTo1D(i+1,j)]) {
			wqu.union(xyTo1D(i,j), xyTo1D(i+1,j));
			wquWashBack.union(xyTo1D(i,j), xyTo1D(i+1,j));
		}
		
		// connect to left neighbor
		if(j-1 >= 1 && cellStats[xyTo1D(i,j-1)]) {
			wqu.union(xyTo1D(i,j), xyTo1D(i,j-1));
			wquWashBack.union(xyTo1D(i,j), xyTo1D(i,j-1));
		}
				
		// connect to right neighbor
		if(j+1 <= gridSize && cellStats[xyTo1D(i,j+1)]) {
			wqu.union(xyTo1D(i,j), xyTo1D(i,j+1));
			wquWashBack.union(xyTo1D(i,j), xyTo1D(i,j+1));
		}
				
	}

	
	public boolean isOpen(int i, int j) {
		checkBoundary(i, j);
		return cellStats[xyTo1D(i,j)];
	}
	
	
	public boolean isFull(int i, int j) {
		checkBoundary(i, j);
		return wquWashBack.connected(0, xyTo1D(i,j));
	}
	
	
	public boolean percolates() {
		return wqu.connected(0, gridSize * gridSize + 1);
	}
	
	
	private void checkBoundary(int i, int j) {
		if(i < 1 || i > gridSize ) 
            throw new IndexOutOfBoundsException("row index out of bounds");
		if(j < 1 || j > gridSize ) 
			throw new IndexOutOfBoundsException("column index out of bounds");
	}
	
	
	private int xyTo1D(int i, int j) {
		checkBoundary(i, j);
		int index1D = (i-1)*gridSize + j;
		return index1D;
	}
	
}
