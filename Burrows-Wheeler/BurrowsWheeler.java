/*************************************************************************
 * Name:  Yuli Wei
 * 
 * Algorithm Project: Burrows-Wheeler Data Compression
 * 
 * Using stdlib.jar, which is part of the algs4.jar
 * 
 * Compilation:  javac BurrowWheeler.java
 * Execution:  
 * Dependencies:  CircularSuffixArray.java
 *
 * Description:  to perform Burrows-Wheeler transform, which is the first step 
 * of the Burrows-Wheeler data compression algorithm. The following two steps 
 * are Move-to-front encoding (MoveToFront.java) and Huffman compression 
 * (Huffman.java available in algs4.jar)  
 * 
 *************************************************************************/

import java.util.HashMap;
import java.util.Arrays;

public class BurrowsWheeler {
	
	// apply Burrows-Wheeler encoding, reading from standard input
	// and writing to standard output
	public static void encode()  {
		String input = BinaryStdIn.readString();
		int N = input.length();
		CircularSuffixArray csa = new CircularSuffixArray(input);
		
		for (int i = 0; i < N; i++)  {
			if (csa.index(i) == 0)  {
				BinaryStdOut.write(i);
				break;
			}
		}
		
		for (int i = 0; i < N; i++)  {
			int index = (csa.index(i) == 0) ? N - 1 : csa.index(i) - 1;
			char c = input.charAt(index);
			BinaryStdOut.write(c, 8);
		}
		
		BinaryStdOut.close();
	}
	
	// apply Burrows-Wheeler decoding, reading from standard input
	// and writing to standard output
	public static void decode()  {
		int first = BinaryStdIn.readInt();
		String s = BinaryStdIn.readString();
		char[] t = s.toCharArray();
		int N = t.length;
		
		int[] next = new int[N];
		HashMap<Character, Queue<Integer>> pos = new HashMap<Character, Queue<Integer>>(); 
		for (int i = 0; i < N; i++)  {
			if (!pos.containsKey(t[i]))
				pos.put(t[i], new Queue<Integer>());
			pos.get(t[i]).enqueue(i);
		}
		
		Arrays.sort(t);
		for (int i = 0; i < N; i++)  {
			next[i] = pos.get(t[i]).dequeue();
		}
		
		int ori = first;
		for (int i = 0; i < N; i++)  {
			BinaryStdOut.write(t[ori]);
			ori = next[ori];
		}
		
		BinaryStdOut.close();
	}
	
	// if args[0] is '-', apply Burrows-Wheeler encoding
	// if args[0] is '+', apply Burrows-Wheeler decoding
	public static void main(String[] args) {
		if (args[0].equals("-")) 
			encode();
		else if (args[0].equals("+"))
			decode();
		else 
		    throw new IllegalArgumentException();
	}

}
