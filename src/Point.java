/**
 * A position in Lat-Lon-Time space.<br>
 * <br>
 *
 * Where latitude and longitude are measured in degrees and time is measured in
 * seconds.
 *
 * @author brian
 *
 */
public class Point implements Comparable<Point> {
	// This implements Comparable (ie defines Point.compareTo()) so that Trip
	// can utilize Collections.binarySearch() in finding interpolated points in
	// time.

	private double lat;
	private double lon;
	private double time;

	/**
	 * Constructor utilizing three arguments, namely latitude, longitude, and
	 * time.
	 *
	 * @param lat
	 *            The latitude in degrees
	 * @param lon
	 *            The longitude in degrees
	 * @param time
	 *            The time in seconds
	 */
	public Point(double lat, double lon, double time) {
		this.lat = lat;
		this.lon = lon;
		this.time = time;
	}

	/**
	 * Constructor utilizing two arguments, namely latitude and longitude.
	 *
	 * The point's time is set to 0(s) as a default.
	 *
	 * @param lat
	 *            The latitude in degrees
	 * @param lon
	 *            The longitude in degrees
	 */
	public Point(double lat, double lon) {
		this(lat, lon, 0.);
	}

	// Returns an Array of doubles.
	//
	// public double[] getLLT() {
	// double[] triple = new double[3];
	// triple[0] = lat;
	// triple[1] = lon;
	// triple[2] = time;
	// return triple;
	// }

	// Getters and Setters
	public void setTime(double time) {
		this.time = time;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public double getTime() {
		return time;
	}

	/**
	 * Shifts the point in time.
	 *
	 * @param timeOffset
	 */
	public void offsetTime(double timeOffset) {
		this.time += timeOffset;
	}

	/**
	 * Scales the point in time relative to t=0(s).<br>
	 * <br>
	 *
	 * If the original time was 10(s) and the scaleFactor is 0.5, the new time
	 * will be 5(s).
	 *
	 * @param scaleFactor
	 *            The scaling factor
	 */

	// TODO is this method necessary or can we just roll its functionality into
	// the Trip Class
	public void scaleTime(double scaleFactor) {
		this.time *= scaleFactor;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	// Because why not make it print pretty?
	public String toString() {
		return String.format("(lat: %f  lon: %f  time: %f)", lat, lon, time);
	}

	@Override
	// Needed for binary search
	public int compareTo(Point otherPoint) {
		return Double.compare(this.getTime(), otherPoint.getTime());
	}

	/**
	 * Determines if two points are equal. Equality is defined by having all
	 * attributes equal.<br>
	 * <br>
	 *
	 * Have equality defined on Points makes jUnit testing much easier.
	 *
	 * TODO Equality is poorly defined on doubles.
	 *
	 * @param p2
	 *            The Point to compare to.
	 * @return True if they are equal, false otherwise.
	 */
	public boolean equals(Point p2) {
		boolean latEqual = lat == p2.getLat();
		boolean lonEqual = lon == p2.getLon();
		boolean timeEqual = time == p2.getTime();
		return (latEqual && lonEqual && timeEqual);
	}

}
