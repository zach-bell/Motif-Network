package main;

import java.util.LinkedList;

import main.core.MotifStruct;
import tools.CP;

public class MotifMatcher {
	
	/**<strong>getScore()</strong>
	 * <p>Returns a number from 0 to the length of the key to obtain a score on matching elements.</p>
	 * @param input char array to be checked against the key.
	 * @param key char array to check against the input.
	 * @return -1 is returned if the lengths do not match.
	 */
	public int getScore(char[] input, char[] key) {
		// Initial check as to not waste time
		if (input.length != key.length)
			return -1;
		
		int score = 0;
		// Check elements in the arrays
		for (int i = 0; i < key.length; i++) {
			if (key[i] == input[i])
				score ++;
		}
		return score;
	}
	
	/**<strong>getScores()</strong>
	 * <p>Will take in a large array of keys and return a score for each of the keys.</p>
	 * @param input char array to be checked against the key.
	 * @param key char array of char arrays to check against the input.
	 * @return -1 is returned for elements where the lengths of the arrays in key do not match.
	 */
	public int[] getScores(char[] input, char[][] keys) {
		int[] scores = new int[keys.length];
		// Check elements in the arrays
		for (int i = 0; i < keys.length; i++) {
			scores[i] = getScore(input, keys[i]);
		}
		return scores;
	}
	
	/**<strong>scoreStreamInput()</strong>
	 * <p>Will run checks on every element of the input array until a perfect match is found OR the end of the list is reached.</p>
	 * @param input long stream of chars to check against key.
	 * @param keys list of char arrays to check against input.
	 * @param checkAll will check all elements in the input.
	 * @return null if input is not longer than the first key.
	 */
	public MotifStruct[] scoreStreamInput(char[] input, char[][] keys, boolean checkAll, String threadName) {
		// Check if length is greater than
		if (input.length < keys[0].length)
			return null;
		
		// pointer moves along input to check each element
		int pointerIndex = 0;
		int keyLength = keys[0].length;
		LinkedList<MotifStruct> senderList = new LinkedList<MotifStruct>();
		
		while (pointerIndex < input.length) {
			// This means we hit the end of the input. Any further would put an array index out of bounds.
			if (pointerIndex + keyLength > input.length)
				break;
			
			// We create our motif
			char[] motif = createSubArray(input, pointerIndex, pointerIndex + keyLength);
			// We get our index([0]) and score([1]). Again, so I don't need to make another struct object.
			int[] indexAndScore = getHighestIndex(motif, keys);
			senderList.add(new MotifStruct(motif, keys[indexAndScore[0]], indexAndScore[1], pointerIndex, threadName));
			if (!checkAll)
				if (indexAndScore[1] == keyLength) {
					break;
				}
			pointerIndex ++;
		}
		return senderList.toArray(new MotifStruct[senderList.size()]);
	}
	
	/**<strong>getHighestIndex()</strong>
	 * <p>Will return the index of the highest key matched to the input, and the score it found.</p>
	 * @param input single key length check.
	 * @param keys all keys to check input against.
	 * @return null is returned if the key length doesn't match the input length.
	 */
	public int[] getHighestIndex(char[] input, char[][] keys) {
		// check for length
		if (input.length != keys[0].length)
			return null;
		
		int index = 0;
		int highestScore = 0;
		for (int i = 0; i < keys.length; i++) {
			int score = getScore(input, keys[i]);
			if (score == keys[i].length) {
				return new int[] {i, score};
			}
			if (score > highestScore) {
				index = i;
				highestScore = score;
			}
		}
		return new int[] {index, highestScore};	// This is so I don't need to make another struct object
	}
	
	private static char[] createSubArray(char[] array, int startingIndex, int endingIndex) {
		// Check if people can read
		if (startingIndex > endingIndex)
			return null;
		
		// Will probably refactor to not have a loop later.
		int index = 0;
		char[] sender = new char[endingIndex - startingIndex];
		try {
			for (int i = startingIndex; i < endingIndex; i++) {
				sender[index] = array[i];
				index ++;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			CP.println("MotifMatcher: createSubArray(): " + e.getMessage(), MotifLauncher.logFileLocation);
		}
		return sender;
	}
}
