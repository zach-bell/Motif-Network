package main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import main.core.MotifFileHandler;
import main.core.MotifThreadHandler;
import tools.CP;
import tools.Timer;

public class MotifLauncher {
	
	// The length of the motif string to create and check.
	public static final int MOTIF_LENGTH = 8;
	// File to check. This is the input.
	public static final String FILE_IN = "promoters_data_clean.txt";
	
	public static String logFileLocation;

	public static void main(String[] args) {
		CP.println("Starting MotifMatching on: " + FILE_IN + "\n");
		
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy--HH-mm-ss");
		Date date = new Date();
		
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
		date = new Date();	// Refresh date
		timerSmall.startTimingM();// Start timer
		if (mfh.toFile(motifThreadHandler.consolidateLists(), "OUTPUT--" + dateFormat.format(date) + ".txt")) {
			CP.println("Printed to file: " + "OUTPUT--" + dateFormat.format(date) + ".txt\n", logFileLocation);
		}
		
		// Clean up with console prints
		CP.println("Time taken: " + timerSmall.stopTimeM() + "ms.\n", logFileLocation);
		timeSum += timerSmall.timeTaken;
		CP.println("Finished everything.\nTotal time taken: " + ((timeSum / 1000) % 60) + " seconds.", logFileLocation);
	}
}
