	import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class GHInterfaceTest {

	@Test
	public void testLongTrip() {
		City phl = new City("Philadelphia", "philadelphia.osm.pbf", 39.9521104,
				-75.1641682);
		GHInterface gh = new GHInterface(phl);
		Point p1 = new Point(39.9539856, -75.1868489);
		Point p2 = new Point(39.9839484, -75.1299003);
		Trip trip = gh.getTrip(p1, p2);

		// Check that there is a large number of points
		ArrayList<Point> points = trip.getPoints();
		int actualSize = points.size();
		Assert.assertTrue("The trip should have greater than 100pts",
				actualSize > 100);

		// Check that the first time is 0.0
		ArrayList<Boolean> increasingTime = new ArrayList<>();
		double firstTime = points.get(0).getTime();
		Assert.assertTrue("Should start with time near zero",
				Math.abs(firstTime - 0.0) < 10E-6);

		// Check that the time is always increasing
		boolean allGrowing = true;
		for (int i = 1; i < points.size(); i++) {
			boolean timeIncQ = points.get(i).getTime() > points.get(i - 1)
					.getTime();
			allGrowing &= timeIncQ;
		}
		Assert.assertTrue("time should be increasing", allGrowing);
	}

	@Test
	public void testShortTrip() {
		City phl = new City("Philadelphia", "philadelphia.osm.pbf", 39.9521104,
				-75.1641682);
		GHInterface gh = new GHInterface(phl);
		Point p1 = new Point(40.0583080, -75.1856526);
		Point p2 = new Point(40.0571932, -75.1838301);
		Trip trip = gh.getTrip(p1, p2);
		System.out.println(trip);
	}

	@Test
	public void testNonTrip() {
		City phl = new City("Philadelphia", "philadelphia.osm.pbf", 39.9521104,
				-75.1641682);
		GHInterface gh = new GHInterface(phl);
		Point p1 = new Point(40.0571932, -75.1838301);
		Point p2 = new Point(40.0571932, -75.1838301);
		Trip trip = gh.getTrip(p1, p2);
		System.out.println(trip);
	}

	@Test
	public void testFailedTrip() {
		City phl = new City("Philadelphia", "philadelphia.osm.pbf", 39.9521104,
				-75.1641682);
		GHInterface gh = new GHInterface(phl);
		Point p1 = new Point(39.9766282, -75.0804953); // middle of Deleware
														// river
		Point p2 = new Point(40.0571932, -75.1838301);
		Trip trip = gh.getTrip(p1, p2);
		System.out.println(trip);
	}

	public static void main(String[] args) {
		GHInterfaceTest ghit = new GHInterfaceTest();
		ghit.testShortTrip();
		ghit.testLongTrip();
		ghit.testNonTrip();
		ghit.testFailedTrip();
	}

}
