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
		System.out.println(trip);
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
