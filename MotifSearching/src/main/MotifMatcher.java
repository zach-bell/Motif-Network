package main;

public class MotifMatcher {
	/**<strong>getScore()</strong>
	 * <p>Returns a number from 0 to the length of the key to obtain a score on matching elements.</p>
	 * @param input char array to be checked against the key.
	 * @param key char array to check against the input.
	 * @return -1 is returned if the lengths do not match.
	 */
	public static int getScore(char[] input, char[] key) {
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
	public static int[] getScores(char[] input, char[][] keys) {
		int[] scores = new int[keys.length];
		// Check elements in the arrays
		for (int i = 0; i < keys.length; i++) {
			scores[i] = getScore(input, keys[i]);
		}
		return scores;
	}
}
