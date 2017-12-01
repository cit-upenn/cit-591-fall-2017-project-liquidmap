/**
 * Represents a position in latitude-longitude-time space.
 * Latitude and longitude are measured in degrees.
 * Time is measured in seconds.
 * @author brian
 */
public class Point {
	private double lat;
	private double lon;
	private double time;

	/**
	 * Constructor. Sets the latitude, longitude, and time of the Point.
	 * @param lat The latitude in degrees.
	 * @param lon The longitude in degrees.
	 * @param time The time in seconds.
	 */
	public Point(double lat, double lon, double time) {
		this.lat = lat;
		this.lon = lon;
		this.time = time;
	}

	/**
	 * Constructor. Sets the latitude and longitude of the Point.
	 * @param lat The latitude in degrees.
	 * @param lon The longitude in degrees.
	 */
	public Point(double lat, double lon) {
		this(lat, lon, 0.);
	}

	/**
	 * Sets the time of the Point.
	 * @param time The time in seconds.
	 */
	public void setTime(double time) {
		this.time = time;
	}

	/**
	 * Gets the latitude of the Point.
	 * @return The latitude of the Point in degrees.
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * Gets the longitude of the Point.
	 * @return The longitude of the Point in degrees.
	 */
	public double getLon() {
		return lon;
	}

	/**
	 * Gets the time of the Point.
	 * @return The time of the Point in seconds.
	 */
	public double getTime() {
		return time;
	}

	/**
	 * Overrides toString().
	 * Returns a description of the Point as a String.
	 * @return A description of the Point as a String.
	 */
	@Override
	public String toString() {
		return String.format("(lat: %f  lon: %f  time: %f)", lat, lon, time);
	}

	/**
	 * Determines if this Point is equal to a provided Point.
	 * Equality is defined as having all attributes equal or very close.
	 * Having equality defined on Points makes jUnit testing much easier.
	 * @param pointOther The Point to compare to.     
	 * @return True if the Points are equal, false otherwise.
	 */
	public boolean equals(Point pointOther) {
		double closeDistance = 3; // close in distance in meters
		double closeTime = 1E-3; // close in time in seconds
		double dist = this.distanceTo(pointOther);
		double dt = Math.abs(time - pointOther.getTime());
		boolean isCloseInSpace = dist < closeDistance;
		boolean isCloseInTime = dt < closeTime;
		return (isCloseInTime && isCloseInSpace);
	}

	//TO DO: Is the distance returned in meters, degrees, or some other unit?
	/**
	 * Computes the distance between this Point and a provided Point.
	 * @param pointOther The Point to compare to.
	 * @return The distance between the Points.
	 */
	public double distanceTo(Point pointOther) {
		double EARTH_RAD = 6371000; // meters
		double meanLat = (this.getLat() + pointOther.getLat()) / 2;
		double sf = Math.sin(meanLat * Math.PI / 180.);
		double mPerDegLat = 2 * Math.PI * EARTH_RAD / 360;
		double mPerDegLon = mPerDegLat * sf;
		double dy = (lat - pointOther.getLat()) * mPerDegLat;
		double dx = (lon - pointOther.getLon()) * mPerDegLon;
		double dist = Math.sqrt(dy * dy + dx * dx);
		return dist;
	}
}
