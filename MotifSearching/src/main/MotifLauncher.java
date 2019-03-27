package main;

import tools.CP;

public class MotifLauncher {

	public static void main(String[] args) {
		char[][] motifs = MatchMotifGenerator.getMotifs(8);
		CP.println("Key score for acgct: " + MotifMatcher.getScore(
				new char[]{'a','c','g','c','t'}, new char[] {'a','c','g','c'}));
	}
}
