package main;

public class MotifMatcher {
	/**<strong>getScore()</strong>
	 * <p>Returns a number from 0 to the length of the key to obtain a score on matching elements.</p>
	 * @param input char array to be checked against the key.
	 * @param key char array to check against the input.
	 * @return -1 is returned if the lengths do not match.
	 */
	public static int getScore(char[] input, char[] key) {
		if (input.length != key.length)
			return -1;
		int score = 0;
		for (int i = 0; i < key.length; i++) {
			if (key[i] == input[i])
				score ++;
		}
		return score;
	}
	
	public static int[] getScores(char[] input, char[][] key) {
		
		return null;
	}
}
