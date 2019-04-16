package main.core;

import main.MotifCombiner;
import main.MotifLauncher;
import tools.CP;

/**<strong>MotifThreadHandler</strong>
 * <p>This class is designed to manage all threads involved with handling the matching process.</p>
 * @author Zach V
 */
public class MotifThreadHandler {
	
	/**<strong>Default of 4 threads to manage matching and combining.</strong><br>
 	Unless you know what you're doing, don't really mess with this.*/
	public int maxThreads = 4;
	
	// All other class variables
	private char[][] input, motifKeys;
	private MotifThread[] threadList;
	private MotifThread[] activeThreads;
	private MotifCombiner combiner;
	
	/**<strong>MotifThreadHandler()</strong>
	 * <p>This will create a motif thread handler.</p>
	 * @param input is needed for what to match the motif keys against.
	 * @param motifKeys is needed to match against the input.
	 */
	public MotifThreadHandler(char[][] input, char[][] motifKeys) {
		this.input = input.clone();	// Data will be moved around inside this meaning it is important to clone.
		this.motifKeys = motifKeys;
		
		// Initialize everything
		threadList = new MotifThread[input.length];
		activeThreads = new MotifThread[maxThreads];
		combiner = new MotifCombiner();
		
		// Create threads immediately
		createThreads();
	}
	/**<strong>MotifThreadHandler()</strong>
	 * <p>This will create a motif thread handler.</p>
	 * @param input is needed for what to match the motif keys against.
	 * @param motifKeys is needed to match against the input.
	 * @param maxThreads is used to change how many threads work at a time to match. (Don't touch unless you got it)
	 */
	public MotifThreadHandler(char[][] input, char[][] motifKeys, int maxThreads) {
		this.input = input.clone();	// Data will be moved around inside this meaning it is important to clone.
		this.motifKeys = motifKeys;
		this.maxThreads = maxThreads >= 1 ? maxThreads : 4;	// Make sure the user puts in a positive number at least.
		
		// Initialize everything
		threadList = new MotifThread[input.length];
		activeThreads = new MotifThread[maxThreads];
		combiner = new MotifCombiner();
		
		// Create threads immediately
		createThreads();
	}
	
	// Creates threads based on the number of n inputs from X in input[x][y]
	private void createThreads() {
		for (int i = 0; i < input.length; i++) {
			threadList[i] = new MotifThread(input[i], motifKeys);
		}
		CP.println("Created " + input.length + " threads.", MotifLauncher.logFileLocation);
	}
	
	/**<strong>runThreads()</strong>
	 * <p>Will run all threads in the thread list.<br>Make sure you populate first!</p>
	 */
	public void runThreads() {
		CP.println("Starting threads and waiting for completion...", MotifLauncher.logFileLocation);
		CP.println("Using " + maxThreads + " threads per-process.", MotifLauncher.logFileLocation);
		int index = 0;
		
		// Iterates through the active thread list to activate all until it is done.
		// It will then populate the active thread list again and again until all threads in
		// the thread list have started and completed.
		while (index < threadList.length) {
			populateActiveThreads(index);
			while (!checkActiveThreads(index)) {
				// Literally just wait
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					CP.println("Error sleeping for some reason.\n" + e.getMessage(), MotifLauncher.logFileLocation);
				}
			}
			CP.printToFile("Finished index: " + index, MotifLauncher.logFileLocation, true);
			index += maxThreads;
		}
		CP.println("Threads have finished! Happy day! You can consolidate lists now!", MotifLauncher.logFileLocation);
	}
	
	// Checks if threads are active so the handler can keep moving through the list
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
		CP.printToFile("Populating threads from: " + index, MotifLauncher.logFileLocation, true);
		for (int i = 0; i < maxThreads; i++) {
			if (index + i < threadList.length) {
				CP.printToFile(threadList[index + i].getName() + " activating.", MotifLauncher.logFileLocation, true);
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
		CP.println("Consolidating Lists...", MotifLauncher.logFileLocation);
		int size = 0;
		for (MotifThread mt : threadList) {
			size += mt.getCompiledList().length;
		}
		MotifStruct[] sender = new MotifStruct[size];
		int index = 0;
		for (int i = 0; i < threadList.length; i++) {
			for (int j = 0; j < threadList[i].getCompiledList().length; j++) {
				sender[index] = threadList[i].getCompiledList()[j];
				sender[index].setGlobalIndex(index);
				index ++;
			}
		}
		return combiner.combineLikeMotifs(sender);
	}
}
