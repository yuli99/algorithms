/*************************************************************************
 * Name:  Yuli Wei
 * 
 * Algorithm project: Boggle Game Solver
 * Using stdlib.jar, which is part of algs4.jar
 *
 * Compilation:  javac BoggleSolver.java
 * Execution:  
 * Dependencies:  BoggleBoard.java, BoggleTrieST.java
 *
 * Description: an immutable data type to find all valid words in a given
 * Boggle board using a given dictionary.
 *
 *************************************************************************/


import java.util.HashSet;


public class BoggleSolver {
	private BoggleTrieST<Integer> dict;
	
	
	// Initializes the data structure.
	// Assume each word in the dictionary contains only A through Z
	public BoggleSolver(String[] dictionary)  {
		dict = new BoggleTrieST<Integer>();
		for (String s : dictionary)  {
			dict.put(s, 1);
		}
	}
	
	
	// return the set of all valid words in the given Boggle Board
	public Iterable<String> getAllValidWords(BoggleBoard board)  {
		HashSet<String> res = new HashSet<>();
		boolean[][] visited = new boolean[board.rows()][board.cols()];
		for (int i = 0; i < board.rows(); i++)  {
			for (int j = 0; j < board.cols(); j++)  {
				dfsWords(board, i, j, visited, "", res);
			}
		}		
		return res;
	}
	
	
	private void dfsWords(BoggleBoard board, int i, int j,
			boolean[][] visited, String prefix, HashSet<String> res)  {
		
		if (visited[i][j])  {
			return ;
		}
		
		char c = board.getLetter(i, j);
		String word = prefix;
		word += (c == 'Q') ? "QU" : c;
		
		boolean[] isValid = dict.validTest(word); 
		if (!isValid[0])  {
			return;
		}
		if (isValid[1] && word.length() > 2)  {
			res.add(word);
		}
		
		visited[i][j] = true;		
		for (int i2 = i - 1; i2 < i + 2; i2++)  {
			if (i2 >= 0 && i2 < board.rows() )  {
				for (int j2 = j - 1; j2 < j + 2; j2++)  {
					if (j2 >= 0 && j2 < board.cols())  {
						if (i2 == i && j2 == j)  continue;
						char ch = board.getLetter(i2, j2);
						dfsWords(board, i2, j2, visited, word, res);
					}
				}	
			}
		}
		visited[i][j] = false;
	}
	
	
	// return the score of the given word if it is in the dictionary, zero otherwise
	// assume the word contains only A through Z.
	public int scoreOf(String word)  {
		int len = word.length() - 3;
		if (word == null || len < 0) 
			return 0;
		
		int[] score = {1, 1, 2, 3, 5, 11};
		if (dict.contains(word))  {
		    if (len < 5)  {
		    	return score[len];
		    }
		    else {
		    	return score[5];
		    }
		}
		
		return 0;
	}
	
	
	public static void main(String[] args)  {
		In in = new In(args[0]);
		String[] dictionary = in.readAllStrings();
		BoggleSolver solver = new BoggleSolver(dictionary);
		BoggleBoard board = new BoggleBoard(args[1]);
		int score = 0;
		for (String word : solver.getAllValidWords(board))  {
			StdOut.println(word);
			score += solver.scoreOf(word);
		}
		StdOut.println("Score = " + score);
	}
	
}
