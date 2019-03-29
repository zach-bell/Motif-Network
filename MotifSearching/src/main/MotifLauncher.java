package main;

import java.util.LinkedList;

import main.core.MotifFileHandler;
import main.core.MotifStruct;
import tools.CP;
import tools.Timer;

public class MotifLauncher {

	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.startTimingM();
		
		MotifFileHandler mfh = new MotifFileHandler();
		
		LinkedList<MotifStruct[]> structList = new LinkedList<MotifStruct[]>();
		char[][] motifKeys = MatchMotifGenerator.getMotifs(8);
		char[][] input = mfh.getLineInFile("promoters_data_clean.txt");
		try {
			for (int i = 0; i < input.length; i++) {
				structList.add(MotifMatcher.scoreStreamInput(input[i], motifKeys, true));
			}
		} catch (NullPointerException e) {
			CP.println("Yea, couldn't run the rest of the program.");
		}
		CP.println("\nFinished scoring.\nTime taken: " + timer.stopTimeM() + "ms.");
	}
}
