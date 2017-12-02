/**
 * Represents a position in latitude-longitude-time space.
 * Latitude and longitude are measured in degrees/pixels.
 * Method parameters specify how latitude/longitude should be interpreted (i.e., as degrees or pixels).
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
