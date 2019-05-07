package main.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import main.MotifLauncher;
import tools.CP;

/**<strong>MotifFileHandler</strong>
 * <p>This class will handle the file reading and writing involving motif character matching.</p>
 * @author Zach V
 *
 */
public class MotifFileHandler {
	
	private BufferedReader fileReader;
	private static BufferedWriter writer = null;
	
	/**<strong>getLineInFile()</strong>
	 * <p>Will read a file, convert the file into a character array of character arrays.</p>
	 * Example of file:<br>
	 * [ [Line 1],[Line 2],[Line 3],[...],[Line n] ]
	 * @param fileLocation
	 * @return Each character array will 
	 */
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
	
	/**<strong>toFile()</strong>
	 * <p>Will print the motif struct array into a file each struct being its own line.</p>
	 * @param list being the struct list.
	 * @param location being the file location to print to (<strong>output</strong>).
	 * @return 
	 */
	public boolean toFile(MotifStruct[] list, String location) {
		try {
			writer = new BufferedWriter(new FileWriter(location, true));
		} catch (IOException e) {
			CP.println("OOPS, looks like theres a file missin.");
		}
		CP.println("Printing to file...", MotifLauncher.logFileLocation);
		CP.printToFile("Motif Searching File Output\n", location, writer);
		CP.printToFile("Input Motif\tGenerated Motif\tScore\tGlobal Index\tThread\tThread Index", location, writer);
		for (MotifStruct struct : list) {
			// Print to file
			CP.printToFile(String.copyValueOf(struct.getMotif()) + "\t" +
				String.copyValueOf(struct.getMatchMotif()) + "\t" +
					struct.getScore() + "\t" + struct.getGlobalIndex() + "\t\t"
					+ struct.getThreadName() + "\t" + struct.getIndex(), location, writer);
				
		}
		CP.printToFile("\n\nEnd of file. Total time: " +
				((MotifLauncher.timeSum / 1000) % 60) + " seconds.", location);
	    try {
			writer.close();
		} catch (IOException e) {
			CP.println("We tried to close the file stream and yeah.... nope");
		}
		return true;
	}
}
