/**
 * Represents a position in latitude-longitude-time space.
 * Latitude and longitude are measured in pixels (e.g., on a map).
 * Time is measured in seconds.
 * @author Brian Edwards, Matt Surka
 */
public class PointScreen extends Point {
	/**
	 * Constructor. Sets the latitude, longitude, and time of the Point.
	 * @param lat The latitude in pixels.
	 * @param lon The longitude in pixels.
	 * @param time The time in seconds.
	 */
	public PointScreen(double lat, double lon, double time) {
		super(lat, lon, time);
	}
	
	/**
	 * Constructor. Sets the latitude and longitude of the Point.
	 * Sets the time to zero.
	 * @param lat The latitude in pixels.
	 * @param lon The longitude in pixels.
	 */
	public PointScreen(double lat, double lon) {
		super(lat, lon);
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
	@Override
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
	 * @return The distance between the Points in pixels.
	 */
	@Override
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
