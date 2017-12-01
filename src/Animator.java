import java.util.ArrayList;

/**
 * Converts an ArrayList of Trips into a .html file that animates a line for each Trip.
 * @author Matt Surka
 */
public class Animator {
	Writer writer;
	int intStrokeWidth;
	int intStrokeLength;
	double dblTimeBetweenSpawns;
	
	/**
	 * Constructor. Initializes the Animator with a Writer object.
	 */
	public Animator () {
		writer = new Writer();
	}
	
	
	//TO DO: ensure intStrokeLength behaves properly
	
	/**
	 * Converts an ArrayList of Trips into a .html file that animates a line for each Trip.
	 * @param listTrips The ArrayList of Trips.
	 * @param intStrokeWidth The width of each line in pixels.
	 * @param intStrokeLength The height of each line in pixels.
	 * @param dblTimeBetweenSpawns The amount of time (in seconds) to wait before spawning a new line.
	 */
	public void animateTrips (ArrayList<Trip> listTrips, int intStrokeWidth, int intStrokeLength, double dblTimeBetweenSpawns) {
		this.intStrokeWidth = intStrokeWidth;
		this.intStrokeLength = intStrokeLength;
		this.dblTimeBetweenSpawns = dblTimeBetweenSpawns;
		
		for (int i = 0; i < listTrips.size(); i++) {
			listTrips.get(i).offsetTime(dblTimeBetweenSpawns * i);
		}
		
		writer.writeString(generateMainBlock(listTrips) + generateStyleBlock(listTrips) + generateScriptBlock(listTrips), "animation.html");
	}
	
	/**
	 * Generates the main block of code for the animation.
	 * @param listTrips The ArrayList of Trips.
	 * @return A string containing the main block of code for the animation.
	 */
	private String generateMainBlock (ArrayList<Trip> listTrips) {
		StringBuilder strbOut = new StringBuilder();
		
		strbOut.append(	"<!DOCTYPE html>\r\n" + 
						"<html lang=\"en\">\r\n" + 
						"\t<head>\r\n" + 
						"\t\t<meta charset=\"UTF-8\">\r\n" + 
						"\t\t<title>LiquidMaps</title>\r\n" + 
						"\t</head>\r\n" + 
						"\r\n" + 
						"\t<body>\r\n" + 
						"\t\t<p>\r\n" + 
						"\t\t\tRefresh this page to replay.\r\n" + 
						"\t\t</p>\r\n" + 
						"\r\n" + 
						"\t\t<svg>\r\n");
		
		for (int i = 0; i < listTrips.size(); i++) {
			ArrayList<Point> listPoints = listTrips.get(i).getPoints();
			int intX = Mathf.roundToInt(listPoints.get(0).getLat());
			int intY = Mathf.roundToInt(listPoints.get(0).getLon());
			
			strbOut.append("\t\t\t<path id=\"path" + i + "\" d=\"M" + intX + "," + intY);
			
			for (int j = 1; j < listPoints.size(); j++) {
				intX = Mathf.roundToInt(listPoints.get(j).getLat());
				intY = Mathf.roundToInt(listPoints.get(j).getLon());
				strbOut.append(" L" + intX + "," + intY);
			}
			
			strbOut.append("\" />\r\n");
		}

		strbOut.append(	"\t\t</svg>\r\n" + 
						"\r\n" + 
						"\t\t<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>\r\n" + 
						"\t\t<script src='https://cdnjs.cloudflare.com/ajax/libs/velocity/1.5.0/velocity.min.js'></script>\r\n" + 
						"\t\t<script src='https://cdnjs.cloudflare.com/ajax/libs/segment-js/1.0.8/segment.js'></script>\r\n" + 
						"\t</body>\r\n" + 
						"</html>\r\n\r\n");
		
		return strbOut.toString();
	}
	
	/**
	 * Generates the CSS (style) block of code for the animation.
	 * @param listTrips The ArrayList of Trips.
	 * @return A string containing the CSS (style) block of code for the animation.
	 */
	private String generateStyleBlock (ArrayList<Trip> listTrips) {
		StringBuilder strbOut = new StringBuilder();
		
		strbOut.append(	"<style>\r\n" + 
						"\tbody {\r\n" + 
						"\t\tfont-family: \"Helvetica Neue\", Helvetica;\r\n" + 
						"\t\twidth: 600px;\r\n" + 
						"\t\theight: 600px;\r\n" + 
						"\t\tfont-weight: 200;\r\n" + 
						"\t\tletter-spacing: 1px;\r\n" + 
						"\t\tmargin: 25px auto 0 auto;\r\n" + 
						"\t\tbackground: #111111;\r\n" + 
						"\t\tcolor: rgb(25, 25, 25);\r\n" + 
						"\t}\r\n" + 
						"\r\n" + 
						"\tp {\r\n" + 
						"\t\tcolor: #FFFFFF;\r\n" + 
						"\t\tfont-size: 0.85rem;\r\n" + 
						"\t\ttext-align: center;\r\n" + 
						"\t\tmargin: 0 auto;\r\n" + 
						"\t\tmargin-bottom: 17px;\r\n" + 
						"\t}\r\n" + 
						"\r\n" + 
						"\tsvg {\r\n" + 
						"\t\tmargin: 0 auto;\r\n" + 
						"\t\tdisplay: block;\r\n" + 
						"\t\twidth: 100%;\r\n" + 
						"\t\theight: 100%;\r\n" + 
						"\t}\r\n" + 
						"\r\n" + 
						"\tpath {\r\n" + 
						"\t\tstroke: #FFFFFF;\r\n" + 
						"\t\tstroke-width: " + intStrokeWidth + ";\r\n" + 
						"\t\tfill-opacity: 0;" +
						"\t}\r\n" + 
						"</style>\r\n\r\n");
		
		return strbOut.toString();
	}

	/**
	 * Generates the JS (script) block of code for the animation.
	 * @param listTrips The ArrayList of Trips.
	 * @return A string containing the JS (script) block of code for the animation.
	 */
	private String generateScriptBlock (ArrayList<Trip> listTrips) {
		StringBuilder strbOut = new StringBuilder();
		
		strbOut.append("<script>\r\n");
		
		for (int i = 0; i < listTrips.size(); i++) {
			strbOut.append("\tvar path" + i + " = document.getElementById(\"path" + i + "\"), segment" + i + " = new Segment(path" + i + ");\r\n");
		}
		
		strbOut.append("\r\n");
		
		for (int i = 0; i < listTrips.size(); i++) {
			strbOut.append("\tsegment" + i + ".draw(\"0%\", \"0%\", 0);\r\n");
		}
		
		strbOut.append("\r\n");
		
		for (int i = 0; i < listTrips.size(); i++) {
			Trip trip = listTrips.get(i);
			double dblTripDistance = trip.computeTripDistance(0, trip.getPoints().size() - 1);
				
			for (int j = 1; j < trip.getPoints().size(); j++) {
				double dblDistanceToPointFromStart = trip.computeTripDistance(0, j);
				int intPositionFront = Mathf.computePercentage(dblDistanceToPointFromStart / dblTripDistance);
				int intPositionBack = Mathf.clampInt(Mathf.computePercentage((dblDistanceToPointFromStart - intStrokeLength) / dblTripDistance), 0, Integer.MAX_VALUE);
				int intLegTime = Mathf.roundToInt(trip.computeTripTime(j - 1, j));
				double dblDelayTime = trip.getPoints().get(j - 1).getTime() * 1000;
				
				if (j == trip.getPoints().size() - 1) {
					intPositionFront = 100;
					intPositionBack = 100;
				}
				
				strbOut.append("\tsetTimeout(function(){ segment" + i + ".draw(\"" + intPositionBack + "%\", \"" + intPositionFront + "%\", " + intLegTime + "); }, " + dblDelayTime + ");\r\n");
			}
			
			strbOut.append("\r\n");
		}
		
		strbOut.append("</script>\n\n");
		
		return strbOut.toString();
	}
}
