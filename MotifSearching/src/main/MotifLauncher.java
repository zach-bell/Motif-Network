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
	// File to check.
	public static final String FILE_IN = "promoters_data_clean.txt";
	
	public static String logFileLocation;

	public static void main(String[] args) {
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy--HH-mm-ss");
		Date date = new Date();
		
		logFileLocation = "Motif_Log(" + dateFormat.format(date) + ").txt";
		
		Timer timer = new Timer();
		Timer timerSmall = new Timer();
		timer.startTimingM();
		
		MotifFileHandler mfh = new MotifFileHandler();
		
		timerSmall.startTimingM();	// Start timer
		CP.println("Generating Motifs to test.", logFileLocation);
		char[][] motifKeys = MatchMotifGenerator.getMotifs(MOTIF_LENGTH);
		CP.println("Motif Generation time taken: " + timerSmall.stopTimeM() + "ms.", logFileLocation);
		
		char[][] input = mfh.getLineInFile(FILE_IN);
		timerSmall.startTimingM();	// Start timer
		CP.println("\nMatching motifs...", logFileLocation);
		
		MotifThreadHandler motifThreadHandler = new MotifThreadHandler(input, motifKeys);
		motifThreadHandler.runThreads();
		
		CP.println("Finished matching motifs with: " + timerSmall.stopTimeM() + "ms taken.\n", logFileLocation);
		
		timerSmall.startTimingM();// Start timer
		if (mfh.toFile(motifThreadHandler.consolidateLists(), "OUTPUT--" + dateFormat.format(date) + ".txt")) {
			CP.print("Printed to file. ");
		}
		CP.println("Time taken: " + timerSmall.stopTimeM() + "ms.", logFileLocation);
		
		CP.println("\nFinished everything.\nTotal time taken: " + timer.stopTimeM() + "ms.", logFileLocation);
	}
}
