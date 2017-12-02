import java.util.ArrayList;

/**
 * Converts between world space (degrees) and screen space (pixels).
 * Can be initialized in two different ways (i.e., with two different constructors):
 * 
 * 1. With two locations, the positions of which are known in both degrees and pixels.
 * 2. With the upper-left and lower-left corners of a bounding box in degrees, plus an image width.
 * 
 * Once the converter is initialized, it can make conversions repeatedly without re-initialization.
 * 
 * @author Brian Edwards, Matt Surka
 */
public class Converter {
	private double degLonXPixConvFactor; // deg/pix
	private double degLatYPixConvFactor; // deg/pix
	private double lonAt0X; // deg
	private double latAt0Y; // deg
	
	/**
	 * Creates a Converter object using two reference Pixel-Point pairs.
	 *
	 * In other words, the conversion is based on two points on a raster image
	 * where you can identify the pixel position and the latitude/longitude. This is
	 * most useful for input images where you can identify two features on the map
	 * and locate them on Google Maps to identify their locations.
	 *
	 * Does not operate well around the poles or on the international dateline.
	 *
	 * @param pt1 First reference Point (lat, lon)
	 * @param px1 First reference Pixel (iX, iY)
	 * @param pt2 Second reference Point (lat, lon)
	 * @param px2 Second reference Pixel (iX, iY)
	 */
	public Converter(Point pt1, Pixel px1, Point pt2, Pixel px2) {
		double deltaLon = (pt2.getLon() - pt1.getLon());
		double deltaX = (px2.getPixelX() - px1.getPixelX());
		double deltaLat = (pt2.getLat() - pt1.getLat());
		double deltaY = (px2.getPixelY() - px1.getPixelY());
		degLonXPixConvFactor = deltaLon / deltaX;
		degLatYPixConvFactor = deltaLat / deltaY;
		lonAt0X = pt1.getLon() - degLonXPixConvFactor * px1.getPixelX();
		latAt0Y = pt1.getLat() - degLatYPixConvFactor * px1.getPixelY();

	}

	/**
	 * Creates a Converter object using a bounding box on a map defined by
	 * two Points and a width of the image in pixels.
	 *
	 * This will be most useful for defining output images where you can
	 * identify the region you want covered and you know the width of the image.
	 *
	 * Does not operate well around the poles or on the international dateline.
	 *
	 * @param ptUpperLeft Point in the upper-left corner.
	 * @param ptLowerRight Point in the lower-right corner.
	 * @param width The image width.
	 */
	public Converter(Point ptUpperLeft, Point ptLowerRight, int width) {

		double deltaLat = ptUpperLeft.getLat() - ptUpperLeft.getLat();
		double deltaLon = ptUpperLeft.getLon() - ptUpperLeft.getLon();
		degLonXPixConvFactor = deltaLon / width;

		Double meanLat = (ptUpperLeft.getLat() + ptUpperLeft.getLat()) / 2;
		double sizeRatio = Math.cos(Math.toRadians(meanLat));

		int height = new Double(sizeRatio * width * deltaLon / deltaLat).intValue();
		height = Math.abs(height);

		degLatYPixConvFactor = deltaLat / height;
		degLonXPixConvFactor = deltaLon / width;
		latAt0Y = ptUpperLeft.getLat();
		lonAt0X = ptUpperLeft.getLon();
	}
	
	/**
	 * Converts a position in one space (i.e., world or screen) to its corresponding position in the other space.
	 * @param dblLat The latitude in degrees/pixels.
	 * @param dblLon The longitude in degrees/pixels.
	 * @param enumTypeSpaceTo The space to convert to (i.e., world or screen).
	 * @return A pair of doubles (as a double[]) with converted latitude at index [0] and converted longitude at index [1].
	 */
	public double[] convertPositionWorldToScreen(double dblLat, double dblLon) {
		double dblLatConverted = 0;
		double dblLonConverted = 0;
		
		dblLatConverted = (dblLon - lonAt0X) / degLonXPixConvFactor;
		dblLonConverted = (dblLat - latAt0Y) / degLatYPixConvFactor;
		
		return new double[] {dblLatConverted, dblLonConverted};
	}
	
	/**
	 * Converts a position in one space (i.e., world or screen) to its corresponding position in the other space.
	 * @param dblLat The latitude in degrees/pixels.
	 * @param dblLon The longitude in degrees/pixels.
	 * @param enumTypeSpaceTo The space to convert to (i.e., world or screen).
	 * @return A pair of doubles (as a double[]) with converted latitude at index [0] and converted longitude at index [1].
	 */
	public double[] convertPositionScreenToWorld(double dblLat, double dblLon) {
		double dblLatConverted = 0;
		double dblLonConverted = 0;
		
		dblLonConverted = degLonXPixConvFactor * dblLat + lonAt0X;
		dblLatConverted = degLatYPixConvFactor * dblLon + latAt0Y;
		
		return new double[] {dblLatConverted, dblLonConverted};
	}

	/**
	 * Converts a Point to a Pixel (i.e., from world space to screen space).
	 * @param point The Point to convert.
	 * @return The Pixel that corresponds to the location of the Point.
	 */
	public Pixel getPixelFromPointWorld(PointWorld pointWorld) {
		double[] arrDblLatLon = convertPositionWorldToScreen(pointWorld.getLat(), pointWorld.getLon());
		return new Pixel(Mathf.roundToInt(arrDblLatLon[0]), Mathf.roundToInt(arrDblLatLon[1]));
	}

	/**
	 * Converts a Pixel to a Point (i.e., from screen space to world space).
	 * @param pixel The Pixel to convert.
	 * @return The Point that corresponds to the location of the Pixel.
	 */
	public PointWorld getPointFromPixel(Pixel pixel) {
		double[] arrDblLatLon = convertPositionScreenToWorld(pixel.getPixelX(), pixel.getPixelY());
		return new PointWorld(arrDblLatLon[0], arrDblLatLon[1]);
	}
	
	/**
	 * Converts a Point in one space (i.e., world or screen) to a corresponding Point in the other space.
	 * @param point The Point to convert.
	 * @return The converted Point.
	 */
	public Point getConvertedPoint(Point point) {		
		if (point.getClass() == PointWorld.class) {				// convert from world space to screen space
			double[] arrDblLatLon = convertPositionWorldToScreen(point.getLat(), point.getLon());
			return new PointScreen(arrDblLatLon[0], arrDblLatLon[1]);
		} else if (point.getClass() == PointScreen.class) {		// convert from screen space to world space
			double[] arrDblLatLon = convertPositionScreenToWorld(point.getLat(), point.getLon());
			return new PointWorld(arrDblLatLon[0], arrDblLatLon[1]);
		}
		
		return null;
	}
	
	/**
	 * Converts a Trip in one space (i.e., world or screen) to a corresponding Trip in the other space.
	 * @param trip The Trip to convert.
	 * @param enumTypeSpaceTo The space to convert to (i.e., world or screen).
	 * @return The converted Trip.
	 */
	public Trip getConvertedTrip(Trip trip) {
		Trip tripConverted = trip.clone();
		ArrayList<Point> listPoints = tripConverted.getPoints();
		
		for (int i = 0; i < listPoints.size(); i++) {
			listPoints.set(i, getConvertedPoint(listPoints.get(i)));
		}
		
		return tripConverted;
	}
	
	/**
	 * Converts an ArrayList of Trips in one space (i.e., world or screen) to a corresponding ArrayList of Trips in the other space.
	 * @param listTrips The ArrayList of Trips to convert.
	 * @return The converted ArrayList of Trips.
	 */
	public ArrayList<Trip> getConvertedListTrips(ArrayList<Trip> listTrips) {
		ArrayList<Trip> listTripsConverted = new ArrayList<>();
		
		for (Trip trip : listTrips) {
			listTripsConverted.add(getConvertedTrip(trip));
		}
		
		return listTripsConverted;
	}
}
