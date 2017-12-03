/**
 * Represents a position in latitude-longitude-time space.
 * Implementations of this interface can measure latitude and longitude in different units (e.g., degrees on a globe, pixels on a map).
 * Time is measured in seconds.
 * @author Brian Edwards, Matt Surka
 */
public interface Point {
	public void setTime(double time);

	public double getLat();
	
	public double getLon();
	
	public double getTime();

	@Override
	public String toString();

	public boolean equals(Point pointOther);

	public double distanceTo(Point pointOther);
}
