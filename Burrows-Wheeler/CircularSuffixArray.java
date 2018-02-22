/*************************************************************************
 * Name:  Yuli Wei
 * 
 * Algorithm Project: Burrows-Wheeler Data Compression
 * Using stdlib.jar, which is part of the algs4.jar
 *
 * Compilation:  javac CircularSuffixArray.java
 * Execution:  
 * Dependencies:  
 *
 * Description:  a data structure to describe the abstraction of 
 * a sorted array of the N circular suffixes of a string
 *
 *************************************************************************/

import java.util.Arrays;
import java.util.Comparator;


public class CircularSuffixArray {
	private String input;
	private int N;
	private Integer[] index;
	
	public CircularSuffixArray(String s)  {
		input = s;
		if (input == null)
			throw new NullPointerException();
		
		N = input.length();
		index = new Integer[N];
		for (int i = 0; i < N; i++)  {
			index[i] = i;
		}
				
		class SuffixOrder implements Comparator<Integer>  {
			public int compare(Integer s1, Integer s2)  {
				int id1 = s1;
				int id2 = s2;
				for (int i = 0; i < N; i++)  {
					char c1 = input.charAt(id1);
					char c2 = input.charAt(id2);
					
					if (c1 < c2)  return -1;
					else if (c1 > c2) return 1;
					
				    id1 = (id1 > N - 2) ? 0 : id1 + 1;
				    id2 = (id2 > N - 2) ? 0 : id2 + 1;
				}
				return 0;
			}
		}
		
		final Comparator<Integer> SUFFIX_ORDER = new SuffixOrder();		
		Arrays.sort(index, SUFFIX_ORDER);		
	}
	
	
	public int length()  {
		return N;
	}
	
	
	// return the ith sorted suffix
	public int index(int i)  {
		if (i < 0 || i > N - 1) 
			throw new IndexOutOfBoundsException();
		return index[i];
	}
	
	
	// unit testing
	public static void main(String[] args) {
		CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
		for (int i = 0; i < csa.length(); i++) 
			StdOut.println(csa.index(i));

	}

}
