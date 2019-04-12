package main.core;

import main.MotifCombiner;
import main.MotifLauncher;
import main.MotifMatcher;
import tools.CP;

public class MotifThread extends Thread {
	
	private char[] input;
	private char[][] motifKeys;
	private MotifStruct[] structArray = null;
	private MotifMatcher matcher;
	private MotifCombiner combiner;
	
	public boolean done = false;
	
	public MotifThread(char[] input, char[][] motifKeys) {
		this.input = input;
		this.motifKeys = motifKeys;
		
		matcher = new MotifMatcher();
		combiner = new MotifCombiner();
	}
	
	public void run() {
		structArray = matcher.scoreStreamInput(input, motifKeys, true, this.getName());
		CP.printToFile("Combining [" + this.getName() + "]", MotifLauncher.logFileLocation);
		structArray = combiner.combineLikeMotifs(structArray);
		done = true;
	}
	
	public MotifStruct[] getCompiledList() {
		return structArray;
	}
}
