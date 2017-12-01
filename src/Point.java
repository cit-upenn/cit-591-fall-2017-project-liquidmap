/**
 * Represents a position in latitude-longitude-time space.
 * Latitude and longitude are measured in degrees/pixels.
 * Method parameters specify how latitude/longitude should be interpreted (i.e., as degrees or pixels).
 * Time is measured in seconds.
 * @author Brian Edwards, Matt Surka
 */
public class Point {
	private double lat;
	private double lon;
	private double time;

	/**
	 * Constructor. Sets the latitude, longitude, and time of the Point.
	 * @param lat The latitude in degrees/pixels.
	 * @param lon The longitude in degrees/pixels.
	 * @param time The time in seconds.
	 */
	public Point(double lat, double lon, double time) {
		this.lat = lat;
		this.lon = lon;
		this.time = time;
	}

	/**
	 * Constructor. Sets the latitude and longitude of the Point.
	 * @param lat The latitude in degrees/pixels.
	 * @param lon The longitude in degrees/pixels.
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
	 * @return The latitude of the Point in degrees/pixels.
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * Gets the longitude of the Point.
	 * @return The longitude of the Point in degrees/pixels.
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
	 * @param enumTypeSpace The type of space traversed by the Trip (i.e., world space or screen space).    
	 * @return True if the Points are equal, false otherwise.
	 */
	public boolean equals(Point pointOther, Trip.typeSpace enumTypeSpace) {
		double closeDistance = 0;
		double closeTime = 0;
		
		switch (enumTypeSpace) {
		case WORLD:
			closeDistance = 3;
			closeTime = 1E-3;
			break;
		case SCREEN:
			closeDistance = 0;
			closeTime = 0;
			break;
		}

		double dblDistance = this.distanceTo(pointOther, enumTypeSpace);
		double dblTimeDifference = Math.abs(time - pointOther.getTime());
		boolean isCloseInSpace = dblDistance < closeDistance;
		boolean isCloseInTime = dblTimeDifference < closeTime;
		return isCloseInTime && isCloseInSpace;
	}

	/**
	 * Computes the distance between this Point and a provided Point.
	 * @param pointOther The Point to compare to.
	 * @param enumTypeSpace The type of space traversed by the Trip (i.e., world space or screen space).
	 * @return The distance between the Points.
	 */
	public double distanceTo(Point pointOther, Trip.typeSpace enumTypeSpace) {
		double dblDistance = -1;
		
		switch (enumTypeSpace) {
		case WORLD:
			double EARTH_RAD = 6371000; // meters
			double meanLat = (this.getLat() + pointOther.getLat()) / 2;
			double sf = Math.sin(meanLat * Math.PI / 180.);
			double mPerDegLat = 2 * Math.PI * EARTH_RAD / 360;
			double mPerDegLon = mPerDegLat * sf;
			double dy = (lat - pointOther.getLat()) * mPerDegLat;
			double dx = (lon - pointOther.getLon()) * mPerDegLon;
			dblDistance = Math.sqrt(dy * dy + dx * dx);
			break;
		case SCREEN:
			double dblDistanceX = Math.abs(this.getLat() - pointOther.getLat());
			double dblDistanceY = Math.abs(this.getLon() - pointOther.getLon());
			dblDistance = Math.sqrt(Math.pow(dblDistanceX, 2) + Math.pow(dblDistanceY, 2));
			break;
		}
		
		return dblDistance;
	}
}
