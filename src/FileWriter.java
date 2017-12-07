import java.io.PrintWriter;

/**
 * Writes a string to a text file.
 * Uses the default constructor.
 * @author Matt Surka
 */
public class FileWriter {
	
	/**
	 * Writes a string to a text file.
	 * @param strToWrite The string to write.
	 * @param strFileName The desired file name.
	 */
	public void writeString (String strToWrite, String strFileName) {
		System.out.println("Writing to " + strFileName + "...");
		
		try (PrintWriter printWriter = new PrintWriter(strFileName);) {	
			printWriter.print(strToWrite);
		} catch (Exception exception) {
			System.out.println("Could not write to file: " + strFileName);
		}
		System.out.println("...done!");
	}
}
