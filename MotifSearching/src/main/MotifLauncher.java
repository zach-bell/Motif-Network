package main;

import tools.CP;

public class MotifLauncher {

	public static void main(String[] args) {
		char[][] motifKeys = MatchMotifGenerator.getMotifs(3);
		MotifStruct[] motifs = MotifMatcher.scoreStreamInput(
				new char[] {'a','c','a','t','g','a','c','c','t'}, motifKeys, true);
		CP.println("Motif\tMotifMatch\tScore\tIndex\n--------------------------------------");
		for (MotifStruct ms : motifs) {
			CP.println(String.copyValueOf(ms.getMotif()) + "\t" +
					String.copyValueOf(ms.getMatchMotif()) + "\t" + ms.getScore() + "\t" + ms.getIndex());
		}
		
	}
}
