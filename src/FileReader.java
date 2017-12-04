import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
		lines = new ArrayList<String>();
		File file = new File(fileName);
		
		try {
			Scanner in = new Scanner(file);
			//make sure its a .csv
			if (goodFileType()) {
				while (in.hasNextLine()) {
					String line = in.nextLine();
					lines.add(line);
				}

			} else {
				System.out.println("Currently only tab delimited .txt files supported");
			}

			in.close();
			
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
	}

	private boolean goodFileType() {
		Matcher matcher = Pattern.compile("(?>.*\\.)(.+)").matcher(fileName);
		String fileType = new String();
		
		if (matcher.matches()) {
			fileType = matcher.group(1);
		}
		
		if (fileType.equalsIgnoreCase("txt")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @return the lines
	 */
	public ArrayList<String> getLines() {
		return lines;
	}
}
