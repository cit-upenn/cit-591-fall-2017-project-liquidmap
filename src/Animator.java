import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 * Converts an ArrayList of Trips into a .html file that animates a line for each Trip.
 * Stores the animation settings, including canvas size, canvas color, line width, etc.
 * @author Matt Surka
 */
public class Animator {
	Random random;
	FileWriter fileWriter;
	String strFileName;
	String strPageTitle;
	String strCanvasText;
	int intCanvasWidth;
	String strCanvasColor;
	int intLineWidth;
	int intLineLength;
	boolean isKeepLinesVisible;
	String strLineColorA;
	String strLineColorB;
	String strTextColor;
	double dblTimeBetweenSpawns;
	
	/**
	 * Constructor. Initializes the Animator with a Random object and a Writer object.
	 */
	public Animator () {
		random = new Random();
		fileWriter = new FileWriter();
	}
	
	/**
	 * Converts an ArrayList of Trips into an .html file that animates a line for each Trip.
	 * @param listTrips The ArrayList of Trips.
	 * @param strFileName The desired name of the output file, without the "html" extension.
	 * @param strPageTitle The title of the output file (i.e., in the page's metadata).
	 * @param strCanvasText Text that will appear on the canvas.
	 * @param intCanvasWidth The width the animation canvas in pixels.
	 * @param strCanvasColor The color of the animation canvas expressed as a hex triplet: (e.g., "#000000").
	 * @param intLineWidth The width of each line in pixels.
	 * @param intLineLength The length of each line in pixels.
	 * @param isKeepLinesVisible True if lines should remain on the canvas after they complete their animation.
	 * @param strLineColorA The first color boundary of the lines expressed as a hex triplet: (e.g., "#000000").
	 * @param strLineColorA The second color boundary of the lines expressed as a hex triplet: (e.g., "#000000").
	 * @param strTextColor The color of text on the canvas expressed as a hex triplet: (e.g., "#000000").
	 * @param dblTimeBetweenSpawns The amount of time (in seconds) to wait before spawning a new line.
	 */
	public void animateTrips (ArrayList<Trip> listTrips, String strFileName, String strPageTitle, String strCanvasText,
							  int intCanvasWidth, String strCanvasColor, int intLineWidth, int intLineLength,
							  boolean isKeepLinesVisible, String strLineColorA, String strLineColorB, String strTextColor,
							  double dblTimeBetweenSpawns) {
		this.strFileName = strFileName;
		this.strPageTitle = strPageTitle;
		this.strCanvasText = strCanvasText;
		this.intCanvasWidth = intCanvasWidth;
		this.strCanvasColor = strCanvasColor;
		this.intLineWidth = intLineWidth;
		this.intLineLength = intLineLength;
		this.isKeepLinesVisible = isKeepLinesVisible;
		this.strLineColorA = strLineColorA;
		this.strLineColorB = strLineColorB;
		this.strTextColor = strTextColor;
		this.dblTimeBetweenSpawns = dblTimeBetweenSpawns;
		
		for (int i = 0; i < listTrips.size(); i++) {
			listTrips.get(i).offsetTime(dblTimeBetweenSpawns * i);
		}
		
		fileWriter.writeString(generateMainBlock(listTrips) + generateStyleBlock(listTrips) 
		+ generateScriptBlock(listTrips), strFileName + ".html");
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
						"\t\t<title>" + strPageTitle + "</title>\r\n" + 
						"\t</head>\r\n" + 
						"\r\n" + 
						"\t<body>\r\n" + 
						"\t\t<p>\r\n" + 
						"\t\t\t" + strCanvasText + "\r\n" + 
						"\t\t</p>\r\n" + 
						"\r\n" + 
						"\t\t<svg>\r\n");
		
		// initialize a <path> element for each Trip
		for (int i = 0; i < listTrips.size(); i++) {
			ArrayList<Point> listPoints = listTrips.get(i).getPoints();
			int intX = Mathf.roundToInt(listPoints.get(0).getLat());
			int intY = Mathf.roundToInt(listPoints.get(0).getLon());
			
			strbOut.append("\t\t\t<path id=\"path" + i + "\" ");
			
			if (!strLineColorA.equals(strLineColorB)) {
				strbOut.append("stroke=\"" + generateRandomColor(strLineColorA, strLineColorB) + "\" ");
			}
			
			strbOut.append("d=\"M" + intX + "," + intY);
			
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
						"\t\twidth: " + intCanvasWidth + "px;\r\n" + 
						"\t\theight: " + intCanvasWidth + "px;\r\n" + 
						"\t\tfont-weight: 200;\r\n" + 
						"\t\tletter-spacing: 1px;\r\n" + 
						"\t\tmargin: 25px auto 0 auto;\r\n" + 
						"\t\tbackground: " + strCanvasColor + ";\r\n" + 
						"\t}\r\n" + 
						"\r\n" + 
						"\tp {\r\n" + 
						"\t\tcolor: " + strTextColor + ";\r\n" + 
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
						"\tpath {\r\n");
		
		if (strLineColorA.equals(strLineColorB)) {
			strbOut.append("\t\tstroke: " + strLineColorA + ";\r\n");
		}
		
		strbOut.append(	"\t\tstroke-width: " + intLineWidth + ";\r\n" + 
						"\t\tfill-opacity: 0;\r\n" +
						"\t}\r\n" + 
						"\r\n" +
						"\t.js-loading *,\r\n" +
						"\t.js-loading *:before,\r\n" +
						"\t.js-loading *:after {\r\n" +
						"\t\tanimation-play-state: paused !important;\r\n" +
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
		
		strbOut.append(	"<script>\r\n" +
						"\tdocument.body.classList.add('js-loading');\r\n" +
						"\twindow.addEventListener(\"load\", showPage);\r\n" +
						"\r\n" +
						"\tfunction showPage() {\r\n" +
						"\t\tdocument.body.classList.remove('js-loading');\r\n" +
						"\t}\r\n" +
						"\r\n");
		
		// initialize a Segment object for each <path> element
		for (int i = 0; i < listTrips.size(); i++) {
			strbOut.append("\tvar path" + i + " = document.getElementById(\"path" + i + "\"), segment" + i + " = new Segment(path" + i + ");\r\n");
		}
		
		strbOut.append("\r\n");
		
		// start each Segment at 0%, meaning that no part of it is drawn
		for (int i = 0; i < listTrips.size(); i++) {
			strbOut.append("\tsegment" + i + ".draw(\"0%\", \"0%\", 0);\r\n");
		}
		
		strbOut.append("\r\n");
		
		// animate the Segment for each leg of the Trip, using a time delay to move it across one leg at a time
		for (int i = 0; i < listTrips.size(); i++) {
			Trip trip = listTrips.get(i);
			double dblTripDistance = trip.computeTripDistance(0, trip.getPoints().size() - 1);
				
			for (int j = 1; j < trip.getPoints().size(); j++) {
				double dblDistanceToPointFromStart = trip.computeTripDistance(0, j);
				int intPositionFront = Mathf.computePercentage(dblDistanceToPointFromStart / dblTripDistance);
				int intPositionBack = Mathf.clampInt(Mathf.computePercentage((dblDistanceToPointFromStart - intLineLength) / dblTripDistance), 0, Integer.MAX_VALUE);
				int intLegTime = Mathf.clampInt(Mathf.roundToInt(trip.computeTripTime(j - 1, j)), 1, Integer.MAX_VALUE);
				int intDelayTime = Mathf.roundToInt(trip.getPoints().get(j - 1).getTime());
				
				// if true, hide lines after they complete their animation
				if (!isKeepLinesVisible) {
					if (j == trip.getPoints().size() - 1) {
						intPositionFront = 100;
						intPositionBack = 100;
					}
				}
				
				strbOut.append("\tsetTimeout(function(){ segment" + i + ".draw(\"" + intPositionBack + "%\", \"" + intPositionFront + "%\", " + intLegTime + "); }, " + intDelayTime + ");\r\n");
			}
			
			strbOut.append("\r\n");
		}
		
		strbOut.append("</script>\n\n");
		
		return strbOut.toString();
	}
	
	/**
	 * Generates a random color between two colors, which act as boundaries.
	 * Interpolates between each of the RGB values of the color boundaries individually.
	 * @param strColorA The first color boundary.
	 * @param strColorB The second color boundary.
	 * @return A random color with RGB values between those of the color boundaries.
	 */
	private String generateRandomColor (String strColorA, String strColorB) {
		float[] fltComponentsClrA = new float[4];
		float[] fltComponentsClrB = new float[4];	
		float[] fltComponentsClrRandom = new float[4];
		
		Color.decode(strColorA).getColorComponents(fltComponentsClrA);
		Color.decode(strColorB).getColorComponents(fltComponentsClrB);
		
		for (int i = 0; i < fltComponentsClrRandom.length; i++) {
			float fltMin = Float.min(fltComponentsClrA[i], fltComponentsClrB[i]);
			float fltMax = Float.max(fltComponentsClrA[i], fltComponentsClrB[i]);
			float fltRandom = random.nextFloat() * (fltMax - fltMin) + fltMin;
			fltComponentsClrRandom[i] = fltRandom;
		}
		
		Color clrRandom = new Color(fltComponentsClrRandom[0], fltComponentsClrRandom[1], fltComponentsClrRandom[2], fltComponentsClrRandom[3]);
		String strColorRandom = String.format("#%02X%02X%02X", clrRandom.getRed(), clrRandom.getGreen(), clrRandom.getBlue());
		return strColorRandom;
	}
}
