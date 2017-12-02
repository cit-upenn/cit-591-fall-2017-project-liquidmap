/**
 * Represents a position in latitude-longitude-time space.
 * Latitude and longitude are measured in degrees/pixels.
 * Method parameters specify how latitude/longitude should be interpreted (i.e., as degrees or pixels).
 * Time is measured in seconds.
 * @author Brian Edwards, Matt Surka
 */
public class PointWorld implements Point {
	private double lat;
	private double lon;
	private double time;

	/**
	 * Constructor. Sets the latitude, longitude, and time of the Point.
	 * @param lat The latitude in degrees/pixels.
	 * @param lon The longitude in degrees/pixels.
	 * @param time The time in seconds.
	 */
	public PointWorld(double lat, double lon, double time) {
		this.lat = lat;
		this.lon = lon;
		this.time = time;
	}

	/**
	 * Constructor. Sets the latitude and longitude of the Point.
	 * @param lat The latitude in degrees/pixels.
	 * @param lon The longitude in degrees/pixels.
	 */
	public PointWorld(double lat, double lon) {
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
	 * @return The latitude in degrees/pixels.
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * Gets the longitude of the Point.
	 * @return The longitude in degrees/pixels.
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
	 * @return True if the Points are equal; false otherwise.
	 */
	public boolean equals(Point pointOther) {
		double closeDistance = 3;
		double closeTime = 1E-3;
		double dblDistance = this.distanceTo(pointOther);
		double dblTimeDifference = Math.abs(time - pointOther.getTime());
		boolean isCloseInSpace = dblDistance < closeDistance;
		boolean isCloseInTime = dblTimeDifference < closeTime;
		return isCloseInTime && isCloseInSpace;
	}

	/**
	 * Computes the distance between this Point and a provided Point.
	 * @param pointOther The Point to compare to.
	 * @return The distance between the Points in degrees/pixels.
	 */
	public double distanceTo(Point pointOther) {
		double dblDistance = 0;
		double dblDistanceX = 0;
		double dblDistanceY = 0;
		double EARTH_RAD = 6371000; // meters
		double meanLat = (this.getLat() + pointOther.getLat()) / 2;
		double sf = Math.sin(meanLat * Math.PI / 180.);
		double mPerDegLat = 2 * Math.PI * EARTH_RAD / 360;
		double mPerDegLon = mPerDegLat * sf;
		dblDistanceX = (this.getLat() - pointOther.getLat()) * mPerDegLat;
		dblDistanceY = (this.getLon() - pointOther.getLon()) * mPerDegLon;		
		dblDistance = Math.sqrt(Math.pow(dblDistanceX, 2) + Math.pow(dblDistanceY, 2));
		return dblDistance;
	}
}
