import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openstreetmap.osmosis.osmbinary.file.FileFormatException;
/**
 * This class will take in a fileName (.csv) 
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
 * 
 * @author sgb
 *
 */
public class VectorDataSource implements DataSource {
	private FileReader fileReader;
	private ArrayList<String> lines;
	private ArrayList<PointWorld> points;


	public VectorDataSource(String fileName) {

		fileReader = new FileReader(fileName);
		lines = fileReader.getLines();
		points = new ArrayList<PointWorld>();

		for (String line : lines) {
			Matcher matcher = Pattern.compile("[\\d]*\\.[\\d]*").matcher(line);

			while (matcher.find()) {
				ArrayList<Double> matches = new ArrayList<>();

				for (int i = 0; i < matcher.groupCount(); i++) {
					for (int j = 0; j < 2; j++) {
						double match = Double.parseDouble(matcher.group());
						matches.add(match);
					}
				}
				//constructing a Point with just a Lat/Lng pair and weights set to 1
				if (matches.size() == 2) {
					PointWorld point = new PointWorld(matches.get(0), matches.get(1));
					points.add(point);
					//if weights are provided, then use em!
				} else if (matches.size() == 3) {
					double weight = matches.get(2);
					PointWorld point = new PointWorld(matches.get(0), matches.get(1));
					point.setProbWeight(weight);
					points.add(point);
				}
			}
		}
	}
	/**
	 * @return the lines
	 */
	public ArrayList<String> getLines() {
		return lines;
	}

	/**
	 * @return the points
	 */
	public ArrayList<PointWorld> getPoints() {
		return points;
	}
	
	@Override
	public Point getRandPoint() throws NullPointerException {
		ArrayList<PointWorld> points = getPoints();
		
		Random rand = new Random();
		Point chosenPoint = null;
		int count = 0;
		
		while (count < 10000) {
			int randIndex = rand.nextInt(points.size());
			double randPointWeight = points.get(randIndex).getWeight();
			//constraint = a random decimal between 0 and 1
			double constraint = (double) (rand.nextInt(10000)/10000);

			if (randPointWeight >= constraint) {
				chosenPoint = points.get(randIndex);
				break;
			}
			count++;
		}
		return chosenPoint;
	}

}
