package main.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

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
			CP.println("File not found exception.");
			return null;
		} catch (IOException e) {
			CP.println("IO problems.");
			return null;
		} // Try catch end
		return sender.toArray(new char[sender.size()][]);
	}
	
	public boolean toFile(LinkedList<MotifStruct[]> list, String location) {
		CP.println("Printing to file... This will take awhile.");
		for (int i = 0; i < list.size(); i++) {
			CP.printToFile("New Struct matching: " + i + "\n", location);
			for (MotifStruct struct : list.get(i)) {
				// Print to file
				CP.printToFile(String.copyValueOf(struct.getMotif()) + "\t" +
					String.copyValueOf(struct.getMatchMotif()) + "\t" +
						struct.getScore() + "\t" + struct.getIndex() + 
						"\t" + struct.getThreadName(), location);
			}
		}
		return true;
	}
}
