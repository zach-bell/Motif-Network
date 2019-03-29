package main.core;

public class MotifStruct {
	
	private char[] motif;
	private char[] matchMotif;
	private int score;
	private int index;
	
	public MotifStruct(char[] motif, char[] matchMotif, int score, int index) {
		this.motif = motif;
		this.matchMotif = matchMotif;
		this.score = score;
		this.index = index;
	}

	public char[] getMotif() {
		return motif;
	}

	public char[] getMatchMotif() {
		return matchMotif;
	}

	public int getScore() {
		return score;
	}

	public int getIndex() {
		return index;
	}
}
