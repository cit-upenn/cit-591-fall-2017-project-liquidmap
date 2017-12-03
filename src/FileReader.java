import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class reads in a file and delivers an ArrayList of 
 * lines for that file to be parsed by another class.
 * 
 * @author sgb
 *
 */
public class FileReader {
	private String fileName;
	private ArrayList<String> lines;
	
	public FileReader(String fileName) {
		this.fileName = fileName;

		try {
			File file = new File(fileName);
			Scanner in = new Scanner(file);
			
			while (in.hasNextLine()) {
				String line = in.nextLine();
				lines.add(line);
			}
			
			in.close();
			
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
	}

	/**
	 * @return the lines
	 */
	public ArrayList<String> getLines() {
		return lines;
	}
}
