import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a traveled path on the globe (i.e., on Earth).
 * Contains an ArrayList of Points, each with a latitude, a longitude, and a time value.
 * @author brian
 */
public class Trip {
	private ArrayList<Point> points;

	/**
	 * Constructor. Initializes the Trip with an empty set of Points.
	 */
	public Trip() {
		points = new ArrayList<>();
	}

	/**
	 * Returns the ArrayList of Points.
	 * @return The ArrayList of Points.
	 */
	public ArrayList<Point> getPoints() {
		return points;
	}

	/**
	 * Adds a Point to the end of the ArrayList of Points.
	 * @param point The Point to be added.
	 */
	public void addPoint(Point point) {
		points.add(point);
	}

	/**
	 * The time at the start of the Trip in seconds.
	 * @return The time at the start of the Trip in seconds.
	 */
	public double minTime() {
		return points.get(0).getTime();
	}

	/**
	 * The time at the end of the Trip in seconds.
	 * @return The time at the end of the Trip in seconds.
	 */
	public double maxTime() {
		return points.get(points.size() - 1).getTime();
	}

	/**
	 * Shifts all Point times in the Trip by a provided offset.
	 * @param timeOffset The time value by which to shift the Point times.         
	 */
	public void offsetTime(double timeOffset) {
		for (Point point : points) {
			point.setTime(point.getTime() + timeOffset);
		}
	}

	/**
	 * Scales all Point times in the Trip by a provided factor.
	 * If the original time was 10(s) and the scaleFactor is 0.5, the new time will be 5(s).
	 * @param scaleFactor The factor by which to scale the Point times.      
	 */
	public void scaleTime(double scaleFactor) {
		for (Point point : points) {
			point.setTime(point.getTime() * scaleFactor);
		}
	}

	/**
	 * Overrides toString().
	 * Returns a description of the Trip as a String.
	 * @return A description of the Trip as a String.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		
		for (Point p : this.points) {
			sb.append(p.toString() + ", ");
		}

		sb.delete(sb.length() - 2, sb.length());
		sb.append(" ]");
		return sb.toString();
	}
}
