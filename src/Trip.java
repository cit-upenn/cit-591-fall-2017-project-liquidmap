import java.util.ArrayList;

/**
 * Represents a traveled path on the globe (i.e., on Earth). 
 * Contains an ArrayList of Points, each with a latitude, a longitude, and a time value.
 * Points are interpreted according to the Trip's typeSpace (i.e., degrees for world space, pixels for screen space).
 * @author Brian Edwards, Matt Surka
 */
public class Trip {
	public enum typeSpace { WORLD, SCREEN }
	private typeSpace enumTypeSpace;
	private ArrayList<Point> points;

	/**
	 * Constructor.
	 * Sets the Trip's typeSpace.
	 * Initializes the Trip with an empty set of Points.
	 */
	public Trip(typeSpace enumTypeSpace) {
		this.enumTypeSpace = enumTypeSpace;
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
	 * Computes the time to travel along a path between two Points in the Trip.
	 * @param intPointStart The index of the start point.
	 * @param intPointEnd The index of the end point.
	 * @return The time to travel along the path between the two Points.
	 */
	public double computeTripTime (int intPointStart, int intPointEnd) {
		double dblTime = points.get(intPointEnd).getTime() - points.get(intPointStart).getTime();
		return dblTime;
	}
	
	/**
	 * Computes the distance along a path between two Points in the Trip.
	 * Distance is calculated based on the Trip's typeSpace (i.e., degrees for world space, pixels for screen space).
	 * @param listPoints The ArrayList of Points.
	 * @param intPointStart The start point of the path.
	 * @param intPointEnd The end point of the path.
	 * @return The distance along the path between the two Points.
	 */
	public double computeTripDistance (int intPointStart, int intPointEnd) {
		double dblDistance = 0;
		
		for (int i = intPointStart; i < intPointEnd; i++) {
			dblDistance += points.get(i).distanceTo(points.get(i + 1), enumTypeSpace);
		}
		
		return dblDistance;
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
