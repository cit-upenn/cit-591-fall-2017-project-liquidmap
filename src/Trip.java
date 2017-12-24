import java.util.ArrayList;

/**
 * Represents a traveled path (e.g., on a globe or map).
 * Contains an ArrayList of Points, each with a latitude, a longitude, and a time value.
 * @author Brian Edwards, Matt Surka
 */
public class Trip implements Cloneable {
	private ArrayList<Point> listPoints;

	/**
	 * Constructor.
	 * Initializes the Trip with an empty set of Points.
	 */
	public Trip() {
		listPoints = new ArrayList<>();
	}
	
	/**
	 * Returns a clone of this Trip.
	 * @return A clone of this Trip.
	 */
	@Override
	public Trip clone() {
		Trip tripClone = new Trip();
		tripClone.getPoints().addAll(this.getPoints());
		return tripClone;
	}

	/**
	 * Returns the ArrayList of Points.
	 * @return The ArrayList of Points.
	 */
	public ArrayList<Point> getPoints() {
		return listPoints;
	}

	/**
	 * Adds a Point to the end of the ArrayList of Points.
	 * @param point The Point to be added.
	 */
	public void addPoint(Point point) {
		listPoints.add(point);
	}

	/**
	 * The time at the start of the Trip in seconds.
	 * @return The time at the start of the Trip in seconds.
	 */
	public double minTime() {
		return listPoints.get(0).getTime();
	}

	/**
	 * The time at the end of the Trip in seconds.
	 * @return The time at the end of the Trip in seconds.
	 */
	public double maxTime() {
		if (listPoints.size() == 0) {
			return 0;
		} else {
			return listPoints.get(listPoints.size() - 1).getTime();
		}
	}

	/**
	 * Shifts all Point times in the Trip by a provided offset.
	 * @param timeOffset The time value by which to shift the Point times.         
	 */
	public void offsetTime(double timeOffset) {
		for (Point point : listPoints) {
			point.setTime(point.getTime() + timeOffset);
		}
	}

	/**
	 * Scales all Point times in the Trip by a provided factor.
	 * If the original time was 10(s) and the scaleFactor is 0.5, the new time will be 5(s).
	 * @param scaleFactor The factor by which to scale the Point times.      
	 */
	public void scaleTime(double scaleFactor) {
		for (Point point : listPoints) {
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
		double dblTime = listPoints.get(intPointEnd).getTime() - listPoints.get(intPointStart).getTime();
		return dblTime;
	}
	
	/**
	 * Computes the distance along a path between two Points in the Trip.
	 * @param listPoints The ArrayList of Points.
	 * @param intPointStart The start point of the path.
	 * @param intPointEnd The end point of the path.
	 * @return The distance along the path between the two Points.
	 */
	public double computeTripDistance (int intPointStart, int intPointEnd) {
		double dblDistance = 0;
		
		for (int i = intPointStart; i < intPointEnd; i++) {
			dblDistance += listPoints.get(i).distanceTo(listPoints.get(i + 1));
		}
		
		return dblDistance;
	}
	
	/**
	 * Optimizes the trip by merging points that are close together.
	 * @param dblMergeDistance The distance that defines whether two points are close enough together to be merged.
	 */
	public void optimizeTrip (double dblMergeDistance) {
		int j = 0;
		while (j + 1 < getPoints().size()) {
			if (getPoints().get(j).distanceTo(getPoints().get(j + 1)) < dblMergeDistance) {
				getPoints().remove(j);
			} else {
				j++;
			}
		}
	}

	/**
	 * Overrides toString().
	 * Returns a description of the Trip as a String.
	 * @return A description of the Trip as a String.
	 */
	@Override
	public String toString() {
		StringBuilder strb = new StringBuilder();
		strb.append("[ ");
		
		for (Point point : this.listPoints) {
			strb.append(point.toString() + ", ");
		}

		strb.delete(strb.length() - 2, strb.length());
		strb.append(" ]");
		return strb.toString();
	}

	/**
	 * Returns a description of the Trip as a String.
	 * @return A description of the Trip as a String.
	 */
	public String getDescrip() {
		StringBuilder strb = new StringBuilder();
		strb.append("trip: ");
		strb.append(this.listPoints.size());
		strb.append(" points; ");
		strb.append(Math.round(this.maxTime()));
		strb.append("(s)");
		return strb.toString();
	}
}
