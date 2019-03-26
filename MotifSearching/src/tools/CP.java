package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/** <strong>Clean Printer</strong>
 * <p>Clean printer is a class to easily access the print function of System, 
 * convert numbers from ugly long decimals to a limited amount, insert the 
 * comma (,) for larger digits, and print to files depending.</p>
 * @author Zachary Vanscoit
 */
public class CP {
	
	private static PrintWriter out = null;
	private static File file = null;
	
	/**<strong>println()</strong>
	 * <p>Pretty self explanatory. Prints to console with a line after.</p>
	 * @param str
	 */
	public static void println(String str) {
		System.out.println(str);
	}
	
	/**<strong>print()</strong>
	 * <p>Pretty self explanatory. Prints to console WITHOUT a line after (cause some people do that).</p>
	 * @param str
	 */
	public static void print(String str) {
		System.out.print(str);
	}
	
	/**<strong>printToFile()</strong>
	 * <p>This method is used to print a nice log file to a file :D</p>
	 * @param str
	 * @param location
	 * @return
	 */
	public static boolean printToFile(String str, String location) {
		try {
			file = new File(location);
			if (file.createNewFile()) {
				println("Created file that wasn't there.");
			}
			out = new PrintWriter(location);
			out.println(str);
		} catch (FileNotFoundException n) {
			println("Ok, somehow the file was not created. Talk to your system administrator even though "+
					"it's not their fault.");
		}
		catch (Exception e) {
			println("Listen man, something happened, and it goes something like this:\n" + e.getMessage());
			return false;
		}
		return true;
	}
}
