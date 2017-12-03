import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openstreetmap.osmosis.osmbinary.file.FileFormatException;
/**
 * This class will take in a fileName (.json, .csv, or .txt) 
 * and allow the user to call getRandPoint(). 
 * 
 * VectorDataSource will find and allow access to the Latitude and Longitude 
 * pairs from the file provided and a probability weight if one is provided. 
 * 
 * Assumptions:
 * 
 * 1).The provided file only contains Latitudes, Longitudes and Weights.
 * 2).Latitude comes before Longitude in the provided file.
 * 3).Latitude and Longitude come before Weight
 * 4).Latitude, Longitude, and Weight are separated by something
 * other than a period 
 * 5).Weights are all decimal values between 0 and 1, inclusive.
 * 6).A file is consistent - it just has lat/long pairs, or it just
 * has lat/long pairs with weights.
 * 
 * @author sgb
 *
 */
public class VectorDataSource implements DataSource {
	private FileReader fileReader;
	private ArrayList<String> lines;
	private String fileType;
	private ArrayList<Point> points;

	
	public VectorDataSource(String fileName) throws FileFormatException {
		
		fileReader = new FileReader(fileName);
		lines = fileReader.getLines();
		
		for (String line : lines) {
			Matcher matcher = Pattern.compile("[\\d]*\\.[\\d]*").matcher(line);
			while (matcher.find()) {
				ArrayList<Double> matches = new ArrayList<>();
				for (int i = 0; i < matcher.groupCount(); i++) {
					double match = Double.parseDouble(matcher.group());
					matches.add(match);
				}
				if (matches.size() == 2) {
					PointWorld point = new PointWorld(matches.get(0), matches.get(1));
					points.add(point);
				} else if (matches.size() == 3) {
					PointWorld point = new PointWorld(matches.get(0), matches.get(1), matches.get(2));
					points.add(point);
				}
			}
		}
	}
	/**
	 * @return the fileReader
	 */
	public FileReader getFileReader() {
		return fileReader;
	}

	/**
	 * @return the lines
	 */
	public ArrayList<String> getLines() {
		return lines;
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @return the points
	 */
	public ArrayList<Point> getPoints() {
		return points;
	}

	@Override
	public Point getRandPoint() {
		Random rand = new Random();
		int randIndex = rand.nextInt(points.size());
		Point point = points.get(randIndex);
		return point;
	}

}
