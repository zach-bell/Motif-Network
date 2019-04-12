package main.core;

import main.MotifCombiner;
import main.MotifLauncher;
import tools.CP;

public class MotifThreadHandler {
	
	private char[][] input, motifKeys;
	private MotifThread[] threadList;
	private MotifThread[] activeThreads;
	
	private MotifCombiner combiner;
	private final int maxThreads = 4;
	
	public MotifThreadHandler(char[][] input, char[][] motifKeys) {
		this.input = input.clone();
		this.motifKeys = motifKeys;
		
		threadList = new MotifThread[input.length];
		activeThreads = new MotifThread[maxThreads];
		combiner = new MotifCombiner();
		
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
	
	// Will iterate through threads from that index, maxThreads at a time.
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
	
	/**<strong>consolidateLists()</strong>
	 * <p>Will combine all threads lists into a combiner, and give the output.</p>
	 * @return
	 */
	public MotifStruct[] consolidateLists() {
		int size = 0;
		for (MotifThread mt : threadList) {
			size += mt.getCompiledList().length;
		}
		MotifStruct[] sender = new MotifStruct[size];
		int index = 0;
		for (int i = 0; i < threadList.length; i++) {
			for (int j = 0; j < threadList[i].getCompiledList().length; j++) {
				sender[index] = threadList[i].getCompiledList()[j];
				index ++;
			}
		}
		return combiner.combineLikeMotifs(sender);
	}
}
