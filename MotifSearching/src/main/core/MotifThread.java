package main.core;

import main.MotifMatcher;

public class MotifThread extends Thread {
	
	private char[] input;
	private char[][] motifKeys;
	private MotifStruct[] structArray = null;
	private MotifMatcher matcher;
	
	public boolean done = false;
	
	public MotifThread(char[] input, char[][] motifKeys) {
		this.input = input;
		this.motifKeys = motifKeys;
		
		matcher = new MotifMatcher();
	}
	
	public void run() {
		structArray = matcher.scoreStreamInput(input, motifKeys, true, this.getName());
		done = true;
	}
	
	public MotifStruct[] getCompiledList() {
		return structArray;
	}
}
