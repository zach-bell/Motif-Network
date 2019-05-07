package tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import main.MotifLauncher;

/** <strong>Clean Printer</strong>
 * <p>Clean printer is a class to easily access the print function of System, 
 * convert numbers from ugly long decimals to a limited amount, insert the 
 * comma (,) for larger digits, and print to files depending.</p>
 * @author Zachary Vanscoit
 */
public class CP {
	
	private static BufferedWriter writer = null;
	private static File file = null;
	
	public static DateFormat timeFormat = new SimpleDateFormat("HH-mm-ss");
	public static Date date = new Date();
	
	/**<strong>println()</strong>
	 * <p>Pretty self explanatory. Prints to console with a line after.</p>
	 * @param str
	 */
	public static void println(String str) {
		System.out.println(str);
	}
	
	/**<strong>println()</strong>
	 * <p>Will print just a new line to the console.</p>
	 */
	public static void println() {
		System.out.println();
	}
	
	/**<strong>println()</strong>
	 * <p>Will print to console and to the file given the location.</p>
	 * @param str to print.
	 * @param location to file to append the string.
	 */
	public static void println(String str, String location) {
		date = new Date();
		printToFile("[" + timeFormat.format(date) + "] " + str, location);
		System.out.println("[" + timeFormat.format(date) + "] " + str);
	}
	
	/**<strong>print()</strong>
	 * <p>Pretty self explanatory. Prints to console WITHOUT a line after (cause some people do that).</p>
	 * @param str
	 */
	public static void print(String str) {
		System.out.print(str);
	}
	
	/**<strong>printToFile()</strong>
	 * <p>This method is used to append directly to a file.</p>
	 * @param str to print to file.
	 * @param location or destination to print to.
	 * @return if it was successful in printing to file.
	 */
	public static boolean printToFile(String str, String location) {
		try {
			file = new File(location);
			if (!file.exists())
				if (file.createNewFile()) {
					println("Created file that wasn't there.", MotifLauncher.logFileLocation);
				}
			writer = new BufferedWriter(new FileWriter(location, true));
		    writer.append(str);
		    writer.newLine();
		    writer.close();
		} catch (FileNotFoundException n) {
			println("Ok, somehow the file was not created. Talk to your system administrator even though "+
					"it's not their fault.", MotifLauncher.logFileLocation);
		}
		catch (Exception e) {
			println("Listen man, something happened, and it goes something like this:\n" +
					e.getMessage(), MotifLauncher.logFileLocation);
			return false;
		}
		return true;
	}
	
	public static boolean printToFile(String str, String location, BufferedWriter writer) {
		try {
			file = new File(location);
			if (!file.exists())
				if (file.createNewFile()) {
					println("Created file that wasn't there.", MotifLauncher.logFileLocation);
				}
		    writer.append(str);
		    writer.newLine();
		} catch (FileNotFoundException n) {
			println("Ok, somehow the file was not created. Talk to your system administrator even though "+
					"it's not their fault.", MotifLauncher.logFileLocation);
		}
		catch (Exception e) {
			println("Listen man, something happened, and it goes something like this:\n" +
					e.getMessage(), MotifLauncher.logFileLocation);
			return false;
		}
		return true;
	}
	
	/**<strong>printToFile()</strong>
	 * <p>This method is used to append directly to a file.</p>
	 * @param str to print to file.
	 * @param location or destination to print to.
	 * @param trackTime true to add [Time] infront of the string.
	 * @return if it was successful in printing to file.
	 */
	public static boolean printToFile(String str, String location, boolean trackTime) {
		try {
			date = new Date();
			file = new File(location);
			if (!file.exists())
				if (file.createNewFile()) {
					println("Created file that wasn't there.");
				}
			
			if (trackTime)
				str = "[" + timeFormat.format(date) + "] " + str;
			writer = new BufferedWriter(new FileWriter(location, true));
		    writer.append(str);
		    writer.newLine();
		    writer.close();
		} catch (FileNotFoundException n) {
			println("Ok, somehow the file was not created. Talk to your system administrator even though "+
					"it's not their fault.", MotifLauncher.logFileLocation);
		}
		catch (Exception e) {
			println("Listen man, something happened, and it goes something like this:\n" +
					e.getMessage(), MotifLauncher.logFileLocation);
			return false;
		}
		return true;
	}
}
