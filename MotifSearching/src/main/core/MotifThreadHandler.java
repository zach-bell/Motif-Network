package main.core;

import java.util.LinkedList;

import tools.CP;

public class MotifThreadHandler {
	
	private char[][] input, motifKeys;
	private LinkedList<MotifStruct[]> structLists;
	private MotifThread[] threadList;
	
	public MotifThreadHandler(char[][] input, char[][] motifKeys) {
		this.input = input;
		this.motifKeys = motifKeys;
		
		structLists = new LinkedList<MotifStruct[]>();
		threadList = new MotifThread[input.length];
		
		createThreads();
	}
	
	private void createThreads() {
		for (int i = 0; i < input.length; i++) {
			threadList[i] = new MotifThread(input[i], motifKeys);
		}
		CP.println("Created " + input.length + " threads.");
	}
	
	public void startThreads() {
		CP.println("\nStarting threads and waiting for completion...");
		for (MotifThread mt : threadList) {
			mt.start();
		}
		
		boolean waiting = true;
		while (waiting) {
			for (MotifThread mt : threadList) {
				if (mt.done) {
					waiting = false;
				} else {
					waiting = true;
					break;
				}
			}
		}
		CP.println("Threads have finished! Happy day! You can consolidate lists now!");
	}
	
	public LinkedList<MotifStruct[]> consolidateLists() {
		for (MotifThread mt : threadList) {
			structLists.add(mt.getCompiledList());
		}
		return structLists;
	}
}
