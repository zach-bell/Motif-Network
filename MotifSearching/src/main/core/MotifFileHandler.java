package main.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import main.MotifLauncher;
import tools.CP;

public class MotifFileHandler {
	
	private BufferedReader fileReader;
	
	public char[][] getLineInFile(String fileLocation) {
		LinkedList<char[]> sender = new LinkedList<char[]>();
		try {
			fileReader = new BufferedReader(new FileReader(fileLocation));
			String line = fileReader.readLine();
			while (line != null) {
				sender.add(line.toCharArray());
				line = fileReader.readLine();
			}
		} catch (FileNotFoundException e) {
			CP.println("File not found exception.", MotifLauncher.logFileLocation);
			return null;
		} catch (IOException e) {
			CP.println("IO problems.", MotifLauncher.logFileLocation);
			return null;
		} // Try catch end
		return sender.toArray(new char[sender.size()][]);
	}
	
	public boolean toFile(MotifStruct[] list, String location) {
		CP.println("Printing to file... This will take awhile.", MotifLauncher.logFileLocation);
		for (MotifStruct struct : list) {
			// Print to file
			CP.printToFile(String.copyValueOf(struct.getMotif()) + "\t" +
				String.copyValueOf(struct.getMatchMotif()) + "\t" +
					struct.getScore() + "\t" + struct.getIndex() + 
					"\t" + struct.getThreadName(), location);
		}
		return true;
	}
}
