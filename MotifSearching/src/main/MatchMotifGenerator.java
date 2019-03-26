package main;

import java.util.LinkedList;

import tools.CP;

public class MatchMotifGenerator {
	
	/**<strong>MOTFI_ARR</strong>
	 * <p>The array list of letter combinations for the assignment.</p>
	 */
	public static final char[] MOTIF_ARR = {'a','c','g','t'};
	
	/**<strong>getMotifs()</strong>
	 * <p>This will return an array of arrays of all possible combinations
	 * of all elements in MOTIF_ARR to the desired length.</p>
	 * @param length
	 * @return
	 */
	public static char[][] getMotifs(int length) {
		LinkedList<char[]> senderList = new LinkedList<char[]>();
		int[] index_arr = new int[length];
		int max = MOTIF_ARR.length - 1;
		int currentPointerIndex = length;
		
		while (sumOfElements(index_arr) < (max * MOTIF_ARR.length)) {
			// Char generation
			char[] tmp = convertIndexToChar(MOTIF_ARR, index_arr);
			senderList.add(tmp);
			
			// Index incrementing
			
		}
		
		return senderList.toArray(new char[senderList.size()][length]);
	}
	
	/**<strong>convertIndexToChar()</strong>
	 * <p>Uses an array of indexes to indicate the char indexes and build a new char list of that length.</p>
	 * @param lettersList the dictionary used to build the new char list from.
	 * @param indexList contains which letter to use from that letter list.
	 * @return new list the length of indexList with appropriate letters indicated from the list.
	 */
	public static char[] convertIndexToChar(char[] lettersList, int[] indexList) {
		char[] sender = new char[indexList.length];
		try {
			for (int i = 0; i < indexList.length; i++) {
				sender[i] = lettersList[indexList[i]];
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			CP.println("You had to do it didn't you...\n" + e.getMessage());
			return null;
		}
		return sender;
	}
	
	/**<strong>sumOfElements()</strong>
	 * <p>Will sum all elements in an integer array.</p>
	 * @param list
	 * @return
	 */
	public static int sumOfElements(int[] list) {
		int sum = 0;
		for (int e : list) {
			sum += e;
		}
		return sum;
	}
}
