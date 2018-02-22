/*************************************************************************
 * Name:  Yuli Wei
 * 
 * Algorithm project: Boggle Game Solver
 * Using Queue.java in algs4.jar
 *
 * Compilation:  javac BoggleTrieST.java
 * Execution:  
 * Dependencies: none
 *
 * Description: a 26-way Tries based on TrieST.java
 *
 *************************************************************************/


public class BoggleTrieST<Value> {
	private static final int R =26; // From A to Z
	private static final int OFFSET = 65;  // offset of 'A'
	
	private Node root;  // root of trie
	private int N;  // number of keys
	
	private static class Node {
		private Object val;
		private Node[] next = new Node[R];
	}
	
	public BoggleTrieST()  {
		
	}
	
	public Value get(String key)  {
		Node x = get(root, key, 0);
		if (x == null) return null;
		return (Value) x.val;
	}
	
	private Node get(Node x, String key, int d)  {
		if (x == null) return null;
		if (d == key.length())  return x;
		char c = key.charAt(d);
		return get(x.next[c - OFFSET], key, d + 1);
	}
	
	public boolean contains(String key)  {
		return get(key) != null;
	}
	
	public void put(String key, Value val)  {
		if (val == null)  delete(key);
		else root = put(root, key, val, 0);
	}
	
	private Node put(Node x, String key, Value val, int d)  {
		if (x == null)  x = new Node();
		if (d == key.length())  {
			if (x.val == null) N++;
			x.val = val;
			return x;
		}
		char c = key.charAt(d);
		x.next[c - OFFSET] = put(x.next[c - OFFSET], key, val, d + 1);
		return x;
	}
	
	public void delete(String key)  {
		root = delete(root, key, 0);
	}
	
	private Node delete(Node x, String key, int d)  {
		if (x == null)  return null;
		if (d == key.length())  {
			if (x.val != null)  N--;
			x.val = null;
		}
		else  {
			char c = key.charAt(d);
			x.next[c - OFFSET] = delete(x.next[c - OFFSET], key, d + 1);
		}
		
		if (x.val != null)  return x;
		for (int c = 0; c < R; c++)  {
			if (x.next[c - OFFSET] != null)  {
				return x;
			}
		}
		return null;
	}
	
	public int size()  {
		return N;
	}
	
	public boolean isEmpty()  {
		return size() == 0;
	}
	
	public Iterable<String> keysWithPrefix(String prefix)  {
		Queue<String> results = new Queue<>();
		Node x = get(root, prefix, 0);
		collect(x, new StringBuilder(prefix), results);
		return results;
	}
	
	private void collect(Node x, StringBuilder prefix, Queue<String> results)  {
		if (x == null) return;
		if (x.val != null) results.enqueue(prefix.toString());
		for (char c = 0; c < R; c++)  {
			prefix.append(c + OFFSET);
			collect(x.next[c], prefix, results);
			prefix.deleteCharAt(prefix.length() - 1);
		}
	}
	
	public Iterable<String> keysThatMatch(String pattern)  {
		Queue<String> results = new Queue<>();
		collect(root, new StringBuilder(), pattern, results);
		return results;
	}
	
	private void collect(Node x, StringBuilder prefix, String pattern, Queue<String> results)  {
		if (x == null) return;
		int d = prefix.length();
		if (d == pattern.length() && x.val != null) 
			results.enqueue(prefix.toString());
		if (d == pattern.length())
			return;
		char c = pattern.charAt(d);
		if (c == '.')  {
		    for (char ch = 0; ch < R; ch++)  {
			    prefix.append(ch + OFFSET);
			    collect(x.next[c], prefix, pattern, results);
			    prefix.deleteCharAt(prefix.length() - 1);
		    }
		}
		else {
			prefix.append(c + OFFSET);
			collect(x.next[c], prefix, pattern, results);
			prefix.deleteCharAt(prefix.length() - 1);
		}
	}
	
	public String longestPrefixOf(String query)  {
		int len = longestPrefixOf(root, query, 0, -1);
		if (len == -1)  return null;
		else return query.substring(0, len);
	}
	
	private int longestPrefixOf(Node x, String query, int d, int len)  {
		if (x == null)  return len;
		if (x.val == null)  len = d;
		if (d == query.length())  return len;
		char c = query.charAt(d);
		return longestPrefixOf(x.next[c - OFFSET], query, d + 1, len);
	}
	
	public boolean[] validTest(String prefix)  {
		boolean[] res = new boolean[2];
		Node x = get(root, prefix, 0);
		if (x != null)  {
			res[0] = true;
			if (x.val != null)  {
				res[1] = true;
			}
		}		
		return res;
	}

}
