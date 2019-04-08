package main.core;

import java.util.LinkedList;
import main.MotifLauncher;
import tools.CP;

public class MotifThreadHandler {
	
	private char[][] input, motifKeys;
	private LinkedList<MotifStruct[]> structLists;
	private MotifThread[] threadList;
	private MotifThread[] activeThreads;
	
	private final int maxThreads = 4;
	
	public MotifThreadHandler(char[][] input, char[][] motifKeys) {
		this.input = input.clone();
		this.motifKeys = motifKeys;
		
		structLists = new LinkedList<MotifStruct[]>();
		threadList = new MotifThread[input.length];
		activeThreads = new MotifThread[maxThreads];
		
		createThreads();
	}
	
	private void createThreads() {
		for (int i = 0; i < input.length; i++) {
			threadList[i] = new MotifThread(input[i], motifKeys);
		}
		CP.println("Created " + input.length + " threads.", MotifLauncher.logFileLocation);
	}
	
	public void runThreads() {
		CP.println("\nStarting threads and waiting for completion...", MotifLauncher.logFileLocation);
		
		int index = 0;
		
		while (index < threadList.length) {
			populateActiveThreads(index);
			while (!checkActiveThreads(index)) {
				// Literally just wait
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					CP.println("Error sleeping for some reason.", MotifLauncher.logFileLocation);
				}
			}
			CP.println("Finished index: " + index, MotifLauncher.logFileLocation);
			index += maxThreads;
		}
		CP.println("Threads have finished! Happy day! You can consolidate lists now!");
	}
	
	private boolean checkActiveThreads(int index) {
		try {
			for (MotifThread mt : activeThreads) {
				if (!mt.done)
					return false;
			}
		} catch (NullPointerException ne) {
			return false;
		}
		return true;
	}
	
	private void populateActiveThreads(int index) {
		CP.println("Populating threads from: " + index);
		for (int i = 0; i < maxThreads; i++) {
			if (index + i < threadList.length) {
				CP.printToFile(threadList[index + i].getName() + " activating.", MotifLauncher.logFileLocation);
				activeThreads[i] = threadList[index + i];
				activeThreads[i].start();
			} else {
				return;
			}
		}
	}
	
	public LinkedList<MotifStruct[]> consolidateLists() {
		for (MotifThread mt : threadList) {
			structLists.add(mt.getCompiledList());
		}
		return structLists;
	}
}
