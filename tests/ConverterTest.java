import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConverterTest {
	

	private PointWorld pt1;
	private PointWorld pt2;
	private Pixel px1;
	private Pixel px2;
	private Converter conv;
	private Converter conv2;
	
	@Before 
	public void setup() {
		pt1 = new PointWorld(40.1, -75.3); // NW PHL (lat, lon)
		pt2 = new PointWorld(39.9, -74.9); // SE PHL
		// 0.2 degrees lat, 0.4 degrees lon
		// at 40deg ratio is 0.766
		// 1000/(0.4) * 0.2 * 0.766 = 652
		px1 = new Pixel(0, 0); // NW PHL (x, y) image pix
		px2 = new Pixel(1000, 652); // SE PHL
		conv = new Converter(pt1, px1, pt2, px2);
		conv2 = new Converter(pt1, pt2, 550);
	}
	@Test
	public void testPixeltoPointANDPointToPixel() {

		Pixel pxOut1 = conv.getPixelFromPointWorld(pt1);
		Pixel pxOut2 = conv.getPixelFromPointWorld(pt2);
		PointWorld ptOut1 = conv.getPointFromPixel(px1);
		PointWorld ptOut2 = conv.getPointFromPixel(px2);

		Assert.assertTrue(px1.toString().equals(pxOut1.toString()));
		Assert.assertTrue(pt1.toString().equals(ptOut1.toString()));
		Assert.assertTrue(px2.toString().equals(pxOut2.toString()));
		Assert.assertTrue(pt2.toString().equals(ptOut2.toString()));
	}

	@Test
	public void testPixeltoPointANDPointToPixel2ndConstructor() {
		
		Pixel pxOut1 = conv.getPixelFromPointWorld(pt1);
		Pixel pxOut2 = conv.getPixelFromPointWorld(pt2);
		PointWorld ptOut1 = conv.getPointFromPixel(px1);
		PointWorld ptOut2 = conv.getPointFromPixel(px2);

		Assert.assertTrue(px1.toString().equals(pxOut1.toString()));
		Assert.assertTrue(pt1.toString().equals(ptOut1.toString()));
		Assert.assertTrue(px2.toString().equals(pxOut2.toString()));
		Assert.assertTrue(pt2.toString().equals(ptOut2.toString()));
	}
	@Test 
	public void testGetConvertedPoint() {
		PointScreen ps = (PointScreen) conv.getConvertedPoint(pt1);
		Assert.assertEquals("Expecting values of 0.00", ps.getLat(), 
				(double) px1.getPixelX(), 0.0001);
		
		PointWorld pw = (PointWorld) conv.getConvertedPoint(ps);
		Assert.assertTrue(pw.toString().equals(pt1.toString()));

	}
	@Test
	public void testGetConvertedListTrips() {
		GHInterface ghi = new GHInterface("PhillyPopDensity.png");
		ArrayList<Trip> trips = new ArrayList<>();
		
		trips.add(ghi.getTrip(pt1, pt2));
		
		ArrayList<Trip> convTrips = conv2.getConvertedListTrips(trips);
		//these should now be PointScreen type Points
		ArrayList<Point> firstTrip = convTrips.get(0).getPoints();
		double firstLat = firstTrip.get(0).getLat();
		//The pt1 lat should translate to a PixelX of 0
		System.out.println(firstLat);
		System.out.println(px1.getPixelX());
		Assert.assertEquals("Expecting values close to 0.0", firstLat, 
				(double) px1.getPixelX(), 1);
	}
}
