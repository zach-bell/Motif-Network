package main.core;

public class MotifStruct {
	
	private char[] motif;
	private char[] matchMotif;
	private int score;
	private int index;
	private String threadName;
	
	public MotifStruct(MotifStruct clone) {
		this.motif = clone.motif.clone();
		this.matchMotif = clone.matchMotif.clone();
		this.score = clone.score;
		this.index = clone.index;
		this.threadName = String.copyValueOf(clone.threadName.toCharArray());
	}
	
	public MotifStruct(char[] motif, char[] matchMotif, int score, int index, String threadName) {
		this.motif = motif;
		this.matchMotif = matchMotif;
		this.score = score;
		this.index = index;
		this.threadName = threadName;
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
	
	public String getThreadName() {
		return threadName;
	}
}
