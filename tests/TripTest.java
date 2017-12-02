import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class TripTest {

	@Test
	public void testTripPrint() {
		Trip trip = new Trip();
		Point p1 = new PointWorld(1., 2., 0.);
		Point p2 = new PointWorld(3., 2., 10.);
		Point p3 = new PointWorld(5., 2., 30.);
		trip.addPoint(p1);
		trip.addPoint(p2);
		trip.addPoint(p3);

		String expected = ""
				+ "[ (lat (deg): 1.000000  lon (deg): 2.000000  time (s): 0.000000), "
				+ "(lat (deg): 3.000000  lon (deg): 2.000000  time (s): 10.000000), "
				+ "(lat (deg): 5.000000  lon (deg): 2.000000  time (s): 30.000000) ]";
		Assert.assertTrue(expected.equals(trip.toString()));
	}

	@Test
	public void testTripOffset() {
		Trip trip = new Trip();
		Point p1 = new PointWorld(1., 2., 0.);
		Point p2 = new PointWorld(3., 2., 10.);
		Point p3 = new PointWorld(5., 2., 30.);
		trip.addPoint(p1);
		trip.addPoint(p2);
		trip.addPoint(p3);

		trip.offsetTime(1.);
		Point p1Test = new PointWorld(1., 2., 1.);
		Point p2Test = new PointWorld(3., 2., 11.);
		Point p3Test = new PointWorld(5., 2., 31.);
		Assert.assertTrue("offset p1", p1.equals(p1Test));
		Assert.assertTrue("offset p2", p2.equals(p2Test));
		Assert.assertTrue("offset p3", p3.equals(p3Test));

	}

	@Test
	public void testTripScale() {
		Trip trip = new Trip();
		Point p1 = new PointWorld(1., 2., 0.);
		Point p2 = new PointWorld(3., 2., 10.);
		Point p3 = new PointWorld(5., 2., 30.);
		trip.addPoint(p1);
		trip.addPoint(p2);
		trip.addPoint(p3);

		Point p1Test = new PointWorld(1., 2., 0.);
		Point p2Test = new PointWorld(3., 2., 20.);
		Point p3Test = new PointWorld(5., 2., 60.);
		trip.scaleTime(2.);
		Assert.assertTrue("scaling p1", p1.equals(p1Test));
		Assert.assertTrue("scaling p2", p2.equals(p2Test));
		Assert.assertTrue("scaling p3", p3.equals(p3Test));
	}

	@Test
	public void testGetPoints() {
		Trip trip = new Trip();
		Point p1 = new PointWorld(1., 2., 0.);
		Point p2 = new PointWorld(3., 5., 10.);
		Point p3 = new PointWorld(5., 2., 30.);
		trip.addPoint(p1);
		trip.addPoint(p2);
		trip.addPoint(p3);

		ArrayList<Point> points = trip.getPoints();
		Point[] pointsExp = { p1, p2, p3 };
		boolean allTheSame = true;
		for (int i = 0; i < trip.getPoints().size(); i++) {
			allTheSame &= (pointsExp[i].equals(points.get(i)));
		}

		Assert.assertTrue("testGetPoints", allTheSame);
	}
}
