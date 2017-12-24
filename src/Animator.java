import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	double dblMergeDistance;
	
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
	 * @param strFileName The desired name of the output file, without the ".html" extension.
	 * @param strPageTitle The title of the output file (i.e., in the page's metadata).
	 * @param strCanvasText Text that will appear on the canvas.
	 * @param intCanvasWidth The width of the animation canvas in pixels.
	 * @param strCanvasColor The color of the animation canvas expressed as a hex triplet: (e.g., "#000000").
	 * @param intLineWidth The width of each line in pixels.
	 * @param intLineLength The length of each line in pixels.
	 * @param isKeepLinesVisible True if lines should remain on the canvas after they complete their animation.
	 * @param strLineColorA The first color boundary of the lines expressed as a hex triplet (e.g., "#000000").
	 * @param strLineColorA The second color boundary of the lines expressed as a hex triplet (e.g., "#000000").
	 * @param strTextColor The color of text on the canvas expressed as a hex triplet (e.g., "#000000").
	 * @param dblTimeBetweenSpawns The amount of time (in seconds) to wait before spawning a new line.
	 * @param dblMergeDistance The distance (in pixels) that defines whether two points are close enough together to be merged.
	 */
	public void animateTrips (ArrayList<Trip> listTrips, String strFileName, String strPageTitle, String strCanvasText,
							  int intCanvasWidth, String strCanvasColor, int intLineWidth, int intLineLength,
							  boolean isKeepLinesVisible, String strLineColorA, String strLineColorB, String strTextColor,
							  double dblTimeBetweenSpawns, double dblMergeDistance) {
		this.strFileName = strFileName;
		this.strPageTitle = strPageTitle;
		this.strCanvasText = strCanvasText;
		this.intCanvasWidth = intCanvasWidth;
		this.strCanvasColor = verifyColor(strCanvasColor, "#000000");
		this.intLineWidth = intLineWidth;
		this.intLineLength = intLineLength;
		this.isKeepLinesVisible = isKeepLinesVisible;
		this.strLineColorA = verifyColor(strLineColorA, "#FFFFFF");
		this.strLineColorB = verifyColor(strLineColorB, "#FFFFFF");
		this.strTextColor = verifyColor(strTextColor, "#FFFFFF");
		this.dblTimeBetweenSpawns = dblTimeBetweenSpawns;
		this.dblMergeDistance = dblMergeDistance;
		
		for (int i = 0; i < listTrips.size(); i++) {
			Trip trip = listTrips.get(i);
			trip.optimizeTrip(dblMergeDistance);
			trip.offsetTime(dblTimeBetweenSpawns * i);
		}
		
		fileWriter.writeString(generateMainBlock(listTrips), strFileName + ".html");
	}
	
	/**
	 * Generates the main block of code for the animation.
	 * @param listTrips The ArrayList of Trips.
	 * @return A string containing the main block of code for the animation.
	 */
	private String generateMainBlock (ArrayList<Trip> listTrips) {
		StringBuilder strbOut = new StringBuilder();
		
		// add <head> block and start <body> block
		strbOut.append(	"<!DOCTYPE html>\r\n" + 
						"<html lang=\"en\">\r\n" + 
						"\t<head>\r\n" + 
						"\t\t<meta charset=\"UTF-8\">\r\n" + 
						"\t\t<title>" + strPageTitle + "</title>\r\n" + 
						"\r\n" +
						generateStyleBlock(listTrips) +
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
			
			// if the lines will not all be one color, assign each one a random color here
			if (!strLineColorA.equals(strLineColorB)) {
				strbOut.append("stroke=\"" + generateRandomColor(strLineColorA, strLineColorB) + "\" ");
			}
			
			strbOut.append("d=\"M" + intX + "," + intY);
			
			for (int j = 1; j < listPoints.size(); j++) {
				intX = Mathf.roundToInt(listPoints.get(j).getLat());
				intY = Mathf.roundToInt(listPoints.get(j).getLon());
				strbOut.append(" L" + intX + "," + intY);
			}
			
			strbOut.append("\"></path>\r\n");
		}

		// close the <body> block
		strbOut.append(	"\t\t</svg>\r\n" + 
						"\r\n" + 
						"\t\t<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>\r\n" + 
						"\t\t<script src='https://cdnjs.cloudflare.com/ajax/libs/segment-js/1.0.8/segment.js'></script>\r\n" + 
						"\r\n" +
						generateScriptBlock(listTrips) +
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
		
		// add style info for font, canvas size, text color, text alignment, etc.
		strbOut.append(	"\t\t<style>\r\n" + 
						"\t\t\tbody {\r\n" + 
						"\t\t\t\tfont-family: \"Helvetica Neue\", Helvetica;\r\n" + 
						"\t\t\t\twidth: " + intCanvasWidth + "px;\r\n" + 
						"\t\t\t\theight: " + intCanvasWidth + "px;\r\n" + 
						"\t\t\t\tfont-weight: 200;\r\n" + 
						"\t\t\t\tletter-spacing: 1px;\r\n" + 
						"\t\t\t\tmargin: 25px auto 0 auto;\r\n" + 
						"\t\t\t\tbackground: " + strCanvasColor + ";\r\n" + 
						"\t\t\t}\r\n" + 
						"\r\n" + 
						"\t\t\tp {\r\n" + 
						"\t\t\t\tcolor: " + strTextColor + ";\r\n" + 
						"\t\t\t\tfont-size: 0.85rem;\r\n" + 
						"\t\t\t\ttext-align: center;\r\n" + 
						"\t\t\t\tmargin: 0 auto;\r\n" + 
						"\t\t\t\tmargin-bottom: 17px;\r\n" + 
						"\t\t\t}\r\n" + 
						"\r\n" + 
						"\t\t\tsvg {\r\n" + 
						"\t\t\t\tmargin: 0 auto;\r\n" + 
						"\t\t\t\tdisplay: block;\r\n" + 
						"\t\t\t\twidth: 100%;\r\n" + 
						"\t\t\t\theight: 100%;\r\n" + 
						"\t\t\t}\r\n" + 
						"\r\n" + 
						"\t\t\tpath {\r\n");
		
		// if the lines will all be one color, define that color here
		if (strLineColorA.equals(strLineColorB)) {
			strbOut.append("\t\t\t\tstroke: " + strLineColorA + ";\r\n");
		}
		
		// complete the <style> block, adding code to prevent the animation from starting before it is loaded
		strbOut.append(	"\t\t\t\tstroke-width: " + intLineWidth + ";\r\n" + 
						"\t\t\t\tfill-opacity: 0;\r\n" +
						"\t\t\t}\r\n" + 
						"\t\t\r\n" +
						"\t\t\t.js-loading *,\r\n" +
						"\t\t\t.js-loading *:before,\r\n" +
						"\t\t\t.js-loading *:after {\r\n" +
						"\t\t\t\tanimation-play-state: paused !important;\r\n" +
						"\t\t\t}\r\n" +
						"\t\t</style>\r\n\r\n");
		
		return strbOut.toString();
	}

	/**
	 * Generates the JS (script) block of code for the animation.
	 * @param listTrips The ArrayList of Trips.
	 * @return A string containing the JS (script) block of code for the animation.
	 */
	private String generateScriptBlock (ArrayList<Trip> listTrips) {
		StringBuilder strbOut = new StringBuilder();
		
		// start the <script> block with code to check for when the animation has finished loading
		strbOut.append(	"\t\t<script>\r\n" +
						"\t\t\tdocument.body.classList.add('js-loading');\r\n" +
						"\t\t\twindow.addEventListener(\"load\", showPage);\r\n" +
						"\r\n" +
						"\t\t\tfunction showPage() {\r\n" +
						"\t\t\t\tdocument.body.classList.remove('js-loading');\r\n" +
						"\t\t\t}\r\n" +
						"\r\n");
		
		//declare the LegData class
		strbOut.append(	"\t\t\tvar LegData = class LegData {\r\n" +
						"\t\t\t\tconstructor(begin, end, duration) {\r\n" +
						"\t\t\t\t\tthis.begin = begin;\r\n" +
						"\t\t\t\t\tthis.end = end;\r\n" +
						"\t\t\t\t\tthis.duration = duration;\r\n" +
						"\t\t\t\t};\r\n" +
						"\t\t\t}\r\n" +
						"\r\n");
		
		// initialize a Segment object for each <path> element
		for (int i = 0; i < listTrips.size(); i++) {
			strbOut.append("\t\t\tvar path" + i + " = document.getElementById(\"path" + i + "\"), segment" + i + " = new Segment(path" + i + ");\r\n");
		}
		
		strbOut.append("\r\n");
		
		// create an array of Segments and populate it
		strbOut.append("\t\t\tvar arrSegment = new Array();\r\n");
		for (int i = 0; i < listTrips.size(); i++) {
			strbOut.append("\t\t\tarrSegment.push(segment" + i + ");\r\n");
		}
		
		strbOut.append("\r\n");
		
		// create a LegData object for each leg of each trip
		for (int i = 0; i < listTrips.size(); i++) {
			Trip trip = listTrips.get(i);
			double dblTripDistance = trip.computeTripDistance(0, trip.getPoints().size() - 1);
				
			for (int j = 0; j < trip.getPoints().size(); j++) {
				double dblDistanceToPointFromStart = trip.computeTripDistance(0, j);
				double dblPositionFront = Mathf.computePercentage(dblDistanceToPointFromStart / dblTripDistance, 2);
				double dblPositionBack = Mathf.computePercentage((dblDistanceToPointFromStart - intLineLength) / dblTripDistance, 2);
				double dblLegTime = 0;
				
				//TODO: Remove hard-coded division
				if (j == 0) {
					dblLegTime = Mathf.roundToDouble(trip.getPoints().get(j).getTime() / 1000, 2);
				} else {
					dblLegTime = Mathf.roundToDouble(trip.computeTripTime(j - 1, j) / 1000, 2);
				}
				
				if (dblLegTime == 0) {
					dblLegTime += 0.01;
				}
				
				// if true, hide lines after they complete their animation
				if (!isKeepLinesVisible) {
					if (j == trip.getPoints().size() - 1) {
						dblPositionFront = 100;
						dblPositionBack = 100;
					}
				}
				
				strbOut.append("\t\t\tvar legData" + i + "_" + j + " = new LegData (" + dblPositionBack + ", " + dblPositionFront + ", " + dblLegTime + ");\r\n");
			}
			
			strbOut.append("\r\n");
		}
		
		// create an array of LegData for each Segment and populate it
		for (int i = 0; i < listTrips.size(); i++) {
			Trip trip = listTrips.get(i);
			
			strbOut.append("\t\t\tvar arrLegData" + i + " = new Array();\r\n");
				
			for (int j = 0; j < trip.getPoints().size(); j++) {
				strbOut.append("\t\t\tarrLegData" + i + ".push(legData" + i + "_" + j + ");\r\n");
			}
			
			strbOut.append("\r\n");
		}
		
		// create an array of the LegData arrays and populate it
		strbOut.append("\t\t\tvar arrArrLegData = new Array();\r\n");
		for (int i = 0; i < listTrips.size(); i++) {
			strbOut.append("\t\t\tarrArrLegData.push(arrLegData" + i + ");\r\n");
		}
		
		strbOut.append("\r\n");
		
		// create an array of indices to track which leg each trip is on
		strbOut.append("\t\t\tvar arrIntLeg = new Array();\r\n");
		for (int i = 0; i < listTrips.size(); i++) {
			strbOut.append("\t\t\tarrIntLeg.push(0);\r\n");
		}
		
		strbOut.append("\r\n");
		
		// declare the nextLeg() function, which will be called for each leg of each trip
		strbOut.append(	"\t\t\tfunction nextLeg (intSegment) {\r\n" +
						"\t\t\t\tif (arrIntLeg[intSegment] < arrArrLegData[intSegment].length) {\r\n" +
						"\t\t\t\t\tarrSegment[intSegment].draw(arrArrLegData[intSegment][arrIntLeg[intSegment]].begin,\r\n" +
						"\t\t\t\t\t\t\t\t\t\t\t\tarrArrLegData[intSegment][arrIntLeg[intSegment]].end,\r\n" +
						"\t\t\t\t\t\t\t\t\t\t\t\tarrArrLegData[intSegment][arrIntLeg[intSegment]].duration,\r\n" +
						"\t\t\t\t\t\t\t\t\t\t\t\t{ callback: function(){ nextLeg(intSegment); } });\r\n" +
						"\t\t\t\t}\r\n" +
						"\r\n" +
						"\t\t\t\tarrIntLeg[intSegment]++;\r\n" +
						"\t\t\t}\r\n");
		
		strbOut.append("\r\n");
		
		// call nextLeg() for the first leg of each trip
		strbOut.append(	"\t\t\tfor (var i = 0; i < arrSegment.length; i++) {\r\n" +
						"\t\t\t\tnextLeg(i);\r\n" +
						"\t\t\t}\r\n");
			
		strbOut.append("\t\t</script>\r\n");
		
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
	
	/**
	 * Verifies that a string expressing a color as a hex triplet is valid and correctly formatted.
	 * Takes in a color to verify, as well as a default color to revert to if verification fails.
	 * @param strColorToVerify A string that will be tested to ensure it is formatted as a hex triplet (e.g., "#000000").
	 * @param strColorDefault A color expressed as a hex triplet to revert to if verification fails.
	 * @return The verified color if verification succeeds; the default color otherwise.
	 */
	private String verifyColor (String strColorToVerify, String strColorDefault) {
		Pattern pattern = Pattern.compile("#[0-9A-F]{6}");
		Matcher matcher = pattern.matcher(strColorToVerify);
		
		if (matcher.matches()) {
			return strColorToVerify;
		} else {
			return strColorDefault;
		}
	}
}
