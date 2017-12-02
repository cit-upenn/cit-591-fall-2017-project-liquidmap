import org.junit.Assert;
import org.junit.Test;

public class PointScreenTest {

	@Test
	public void testPointEquals() {
		Point p1 = new PointScreen(20., 30., 40.);
		Point p2 = new PointScreen(20., 30., 40.);
		Assert.assertTrue("P1 is the same as P2", p1.equals(p2));
	}

	@Test
	public void testPointTimeEqualsFalse() {
		Point p1 = new PointScreen(0., 0., 0.); // on equator
		Point p2 = new PointScreen(0., 0., 1.);
		Assert.assertFalse("P1 has a different time than P2", p1.equals(p2));
	}

	@Test
	public void testPointTimeEqualsTrue() {
		Point p1 = new PointScreen(0., 0., 0.); // on equator
		Point p2 = new PointScreen(0., 0., 0.);
		Assert.assertTrue("P1 is close enough to P2 in time", p1.equals(p2));
	}

	@Test
	public void testPointEqualsLocFalse() {
		Point p1 = new PointScreen(0., 0., 0.); // on equator
		Point p2 = new PointScreen(1E-4, 0., 0.); // near equator (11m north)
		Assert.assertFalse("P1 has a different loc than P2", p1.equals(p2));
	}

	@Test
	public void testPointEqualsLocTrue() {
		Point p1 = new PointScreen(0., 0., 0.); // on equator
		Point p2 = new PointScreen(0., 0., 0.); // near equator (1.1m north)
		Assert.assertTrue("P1 is close enough to P2 in space", p1.equals(p2));
	}

	@Test
	public void testPointEquals2Args() {
		Point p1 = new PointScreen(1., 2.);
		Point p2 = new PointScreen(1., 2., 0.);
		Assert.assertTrue("P1 two args is at t=0", p1.equals(p2));
	}

	@Test
	public void testPointGettersSetters() {
		Point p1 = new PointScreen(1., 2., 3.);
		Assert.assertTrue(Math.abs(p1.getLat() - 1.) < 1E-6);
		Assert.assertTrue(Math.abs(p1.getLon() - 2.) < 1E-6);
		Assert.assertTrue(Math.abs(p1.getTime() - 3.) < 1E-6);
	}

	@Test
	public void testPointTimeOps() {
		Point p1 = new PointScreen(1., 2., 3.);
		p1.setTime(10.);
		Assert.assertTrue(Math.abs(p1.getTime() - 10.) < 1E-6);
	}

	@Test
	public void testPointString() {
		Point p1 = new PointScreen(1., 2., 3.);
		Assert.assertEquals(p1.toString(), "(lat (px): 1.000000  lon (px): 2.000000  time (s): 3.000000)");
	}
}
