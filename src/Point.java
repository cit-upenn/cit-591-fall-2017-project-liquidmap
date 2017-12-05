/**
 * Represents a position in latitude-longitude-time space.
 * @author Brian Edwards, Matt Surka
 */
public class Point {
	private double lat;
	private double lon;
	private double time;

	/**
	 * Constructor. Sets the latitude, longitude, and time of the Point.
	 * @param lat The latitude.
	 * @param lon The longitude.
	 * @param time The time.
	 */
	public Point(double lat, double lon, double time) {
		this.lat = lat;
		this.lon = lon;
		this.time = time;
	}

	/**
	 * Constructor. Sets the latitude and longitude of the Point.
	 * Sets the time to zero.
	 * @param lat The latitude.
	 * @param lon The longitude.
	 */
	public Point(double lat, double lon) {
		this(lat, lon, 0.);
	}

	/**
	 * Sets the time of the Point.
	 * @param time The time.
	 */
	public void setTime(double time) {
		this.time = time;
	}

	/**
	 * Gets the latitude of the Point.
	 * @return The latitude.
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * Gets the longitude of the Point.
	 * @return The longitude.
	 */
	public double getLon() {
		return lon;
	}

	/**
	 * Gets the time of the Point.
	 * @return The time.
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
		return String.format("(lat (px): %f  lon (px): %f  time (s): %f)", getLat(), getLon(), getTime());
	}

	/**
	 * Determines if this Point is equal to a provided Point.
	 * Equality is defined as having all attributes equal or very close.
	 * Having equality defined on Points makes jUnit testing much easier.
	 * @param pointOther The Point to compare to.
	 * @return True if the Points are equal; false otherwise.
	 */
	public boolean equals(Point pointOther) {
		double closeDistance = 0;
		double closeTime = 0;
		
		double dblDistance = this.distanceTo(pointOther);
		double dblTimeDifference = Math.abs(getTime() - pointOther.getTime());
		
		boolean isCloseInSpace = dblDistance <= closeDistance;
		boolean isCloseInTime = dblTimeDifference <= closeTime;
		return isCloseInTime && isCloseInSpace;
	}

	/**
	 * Computes the distance between this Point and a provided Point.
	 * @param pointOther The Point to compare to.
	 * @return The distance between the Points.
	 */
	public double distanceTo(Point pointOther) {
		double dblDistance = 0;
		double dblDistanceX = 0;
		double dblDistanceY = 0;
		
		dblDistanceX = this.getLat() - pointOther.getLat();
		dblDistanceY = this.getLon() - pointOther.getLon();		
		dblDistance = Math.sqrt(Math.pow(dblDistanceX, 2) + Math.pow(dblDistanceY, 2));
		
		return dblDistance;
	}
}
