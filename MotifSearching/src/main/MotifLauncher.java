package main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import main.core.MotifFileHandler;
import main.core.MotifStruct;
import main.core.MotifThreadHandler;
import tools.CP;
import tools.Timer;

public class MotifLauncher {
	
	// The length of the motif string to create and check.
	public static final int MOTIF_LENGTH = 8;
	
	// File to check. This is the input.
	public static String FILE_IN = "promoters_data_clean.txt";
	
	// File to print output of results.
	public static String FILE_OUT = "OUTPUT--";
	private static boolean setFileOut = false;	// Is to work with arguments into the program.
	
	// Sets the number of threads to use for distributive workload.
	public static int cores = 4;	// This means the program will use 5 threads! including the main thread!
	
	// Used to create extended log files as to not clutter up the console during runtime.
	public static String logFileLocation;

	public static void main(String[] args) {
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy--HH-mm-ss");
		Date date = new Date();
		
		// --------------------------------
		// Handle program arguments
		// --------------------------------
		try {
			if (args[0] != null) {
				FILE_IN = args[0];
			}
			if (args[1] != null) {
				setFileOut = true;
				FILE_OUT = args[1];
			}
			if (args[2] != null) {
				cores = Integer.parseInt(args[2]);
			}
		} catch (NumberFormatException e) {
			CP.println("So that cores number seemed to not actually be a number.");
		} catch (Exception e) {
			CP.println("\nOne or more arguments had a problem. It might still work?\n");
		}
		
		// --------------------------------
		// Startup initializers and messages
		// --------------------------------
		
		CP.println("Starting MotifMatching on: " + FILE_IN + "\n");
		
		if (!setFileOut)
			FILE_OUT = FILE_OUT + dateFormat.format(date) +  ".txt";
		
		// Create an external log file if need be.
		logFileLocation = "Motif_Log(" + dateFormat.format(date) + ").txt";
		CP.println("Creating log file: " + logFileLocation, logFileLocation);
		
		// Time how many milliseconds it takes to run each section.
		int timeSum = 0;			// Total timer sum
		Timer timerSmall = new Timer();		// Each section timer
		
		// --------------------------------
		// Generating Motifs
		// --------------------------------
		timerSmall.startTimingM();	// Start timer
		CP.println("Generating Motifs to test.", logFileLocation);
		char[][] motifKeys = MatchMotifGenerator.getMotifs(MOTIF_LENGTH);
		CP.println("Motif Generation time taken: " + timerSmall.stopTimeM() + "ms.", logFileLocation);
		timeSum += timerSmall.timeTaken;
		
		// --------------------------------
		// File input handling
		// --------------------------------
		MotifFileHandler mfh = new MotifFileHandler();
		char[][] input = mfh.getLineInFile(FILE_IN);
		timerSmall.startTimingM();	// Start timer
		CP.println("Matching motifs...", logFileLocation);
		
		// --------------------------------
		// Motif Matching and Thread Handling
		// --------------------------------
		MotifThreadHandler motifThreadHandler = new MotifThreadHandler(input, motifKeys);
		motifThreadHandler.runThreads();
		CP.println("Finished matching motifs with: " + timerSmall.stopTimeM() + "ms taken.\n", logFileLocation);
		timeSum += timerSmall.timeTaken;
		
		// --------------------------------
		// File output handling (current bottleneck)
		// --------------------------------
		timerSmall.startTimingM();// Start timer
		MotifStruct[] unsorted = motifThreadHandler.consolidateLists();		// Will give out an unsorted list.
		MotifStruct[] sorted = worstSort(unsorted);			// The sorting algorithm is broken at the moment.
		CP.println("Sorting algorithm is broke atm. sorry.", logFileLocation);
		if (mfh.toFile(unsorted, FILE_OUT)) {
			CP.println("Printed to file: " + FILE_OUT + "\n", logFileLocation);
		} else {
			CP.println("File output printing process INCOMPLETE. Please run again.\n\n", logFileLocation);
		}
		
		// Clean up with console prints
		CP.println("Time taken: " + timerSmall.stopTimeM() + "ms.\n", logFileLocation);
		timeSum += timerSmall.timeTaken;
		CP.println("Finished everything.\nTotal time taken: " + ((timeSum / 1000) % 60) + " seconds.", logFileLocation);
	}
	
	/**<storng>worstSort()</strong>
	 * <p>Will horribly sort the list of MotifStructs by the score it had gotten.</p>
	 * @param list to sort;.
	 * @return sorted list.
	 */
	public static MotifStruct[] worstSort(MotifStruct[] list) {
		List<MotifStruct> sortedList = new Stack<MotifStruct>();
		int index = 0;		// Creates the moving starting point of the next search.
		int pointer = 0;	// Iterates through from the index position to the end.
		
		while (index < list.length - 1) {
			MotifStruct highestScored = list[index];
			for (pointer = index + 1; pointer < list.length; pointer ++) {
				if (list[pointer].getScore() > highestScored.getScore())
					highestScored = list[pointer];
			}
			sortedList.add(highestScored);
			index ++;
		}
		
		return sortedList.toArray(new MotifStruct[sortedList.size()]);
	}
}
