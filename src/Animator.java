import java.util.ArrayList;

/**
 * Converts an ArrayList of Trips into a .html file that animates a dot for each Trip.
 * @author Matt Surka
 *
 */
public class Animator {
	Writer writer;
	int intDotWidth;
	int intDotHeight;
	int intStartDelay;
	
	/**
	 * Constructor. Initializes the Animator with a Writer object and with default values.
	 */
	public Animator () {
		writer = new Writer();
		intDotWidth = 1;
		intDotHeight = 1;
		intStartDelay = 1000;
	}
	
	/**
	 * Converts an ArrayList of Trips into a .html file that animates a dot for each Trip.
	 * @param listTrips The ArrayList of Trips.
	 * @param intDotWidth The width of each dot in pixels.
	 * @param intDotHeight The height of each dot in pixels.
	 * @param intStartDelay The amount of time (in milliseconds) to wait before starting the animation.
	 */
	public void animateTrips (ArrayList<Trip> listTrips, int intDotWidth, int intDotHeight, int intStartDelay) {
		this.intDotWidth = intDotWidth;
		this.intDotHeight = intDotHeight;
		this.intStartDelay = intStartDelay;
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
			Point pointStart = listTrips.get(i).getPoints().get(0);
			strbOut.append("\t\t\t<rect id=\"dot" + i + "\" width=\"" + intDotWidth + "\" height=\"" +	intDotHeight + "\" x=\"" + pointStart.getLat() + "\" y=\"" + pointStart.getLon() + "\" />\r\n");
		}

		strbOut.append(	"\t\t</svg>\r\n" + 
						"\r\n" + 
						"\t\t<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>\r\n" + 
						"\t\t<script src='https://cdnjs.cloudflare.com/ajax/libs/velocity/1.5.0/velocity.min.js'></script>\r\n" + 
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
						"\t\tbackground: #000000;\r\n" + 
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
						"\trect {\r\n" + 
						"\t\ttransform-origin: 0% 0%;\r\n" + 
						"\t\tfill: #FFFFFF;\r\n" + 
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
			ArrayList<Point> listPoints = listTrips.get(i).getPoints();
			strbOut.append(	"\t$(\"#dot" + i + "\").delay(" + intStartDelay + ")");
			
			for (int j = 1; j < listPoints.size(); j++) {
				double dblLat = listPoints.get(j).getLat();
				double dblLon = listPoints.get(j).getLon();
				double dblDuration = listPoints.get(j).getTime() - listPoints.get(j - 1).getTime();
				strbOut.append(".velocity({ x: \"" + dblLat + "\", y: \"" + dblLon + "\" }, {duration: " + dblDuration + ", easing: \"ease\"})");
			}
			
			strbOut.append(	"\r\n");
		}
		
		strbOut.append("\r\n</script>\n\n");
		
		return strbOut.toString();
	}
}
