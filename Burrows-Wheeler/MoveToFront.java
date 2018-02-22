/*************************************************************************
 * Name:  Yuli Wei
 *
 * Algorithm Project: Burrows-Wheeler Data Compression
 * Using stdlib.jar, which is part of the algs4.jar
 *
 * Compilation:  javac MoveToFront.java
 * Execution:  
 * Dependencies:  
 *
 * Description: maintain an ordered sequence of the 256 extended ASCII characters.
 * repeatedly read in a character from the input message, print out its position
 * in the sequence and move it to the front of the sequence.
 * 
 *************************************************************************/


public class MoveToFront {
	private static final int R = 256; 
	
	private static char[] initialSeq()  {
		char[] seq = new char[R];
		for (int i = 0; i < R; i++)  {
			seq[i] = (char) i;
		}
		return seq;
	}
	
	// apply move-to-front encoding, reading from standard input 
	// and writing to standard output
	public static void encode()  {
		char[] seq = initialSeq();
		char input;
		int index;
		
		while (!BinaryStdIn.isEmpty())  {
			input = BinaryStdIn.readChar();
			for (index = 0; index < R; index++)  {
				if (seq[index] == input)  {
					BinaryStdOut.write((char) index);
					break;
				}
			}
			
			System.arraycopy(seq, 0, seq, 1, index);
			seq[0] = input;
		}
		
		BinaryStdOut.close();
	}
	
	
	// apply move-to-front decoding, reading from standard input 
	// and writing to standard output 
	public static void decode()  {
		char[] seq = initialSeq();
		int input;
				
		while (!BinaryStdIn.isEmpty())  {
			input = BinaryStdIn.readInt(8);
			char c = seq[input];
			BinaryStdOut.write(c);			
			System.arraycopy(seq, 0, seq, 1, input);
			seq[0] = c;
		}
		
		BinaryStdOut.close();
	}
	
	
	// if args[0] is '-', apply move-to-front encoding
	// if args[0] is '+', apply move-to-front decoding
	public static void main(String[] args) {
		if (args[0].equals("-")) 
			encode();
		else if (args[0].equals("+"))
			decode();
		else 
		    throw new IllegalArgumentException();
	}

}
