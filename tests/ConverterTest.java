import org.junit.Assert;
import org.junit.Test;

public class ConverterTest {

	@Test
	public void test() {
		PointWorld pt1 = new PointWorld(40.1, -75.3); // NW PHL (lat, lon)
		PointWorld pt2 = new PointWorld(39.9, -74.9); // SE PHL
		Pixel px1 = new Pixel(20, 10); // NW PHL (x, y) image pix
		Pixel px2 = new Pixel(80, 90); // SE PHL

		Converter conv = new Converter(pt1, px1, pt2, px2);

		Pixel pxOut1 = conv.getPixelFromPointWorld(pt1);
		Pixel pxOut2 = conv.getPixelFromPointWorld(pt2);
		PointWorld ptOut1 = conv.getPointFromPixel(px1);
		PointWorld ptOut2 = conv.getPointFromPixel(px2);

		// System.out.println(pxOut1);
		// System.out.println(pxOut2);
		// System.out.println(ptOut1);
		// System.out.println(ptOut2);

		Assert.assertTrue(px1.toString().equals(pxOut1.toString()));
		Assert.assertTrue(pt1.toString().equals(ptOut1.toString()));
		Assert.assertTrue(px2.toString().equals(pxOut2.toString()));
		Assert.assertTrue(pt2.toString().equals(ptOut2.toString()));
	}

	@Test
	public void test2() {
		PointWorld pt1 = new PointWorld(40.1, -75.3); // NW PHL (lat, lon)
		PointWorld pt2 = new PointWorld(39.9, -74.9); // SE PHL
		// 0.2 degrees lat, 0.4 degrees lon
		// at 40deg ratio is 0.766
		// 1000/(0.4) * 0.2 * 0.766 = 652
		Pixel px1 = new Pixel(0, 0); // NW PHL (x, y) image pix
		Pixel px2 = new Pixel(1000, 652); // SE PHL

		Converter conv = new Converter(pt1, pt2, 1000);

		Pixel pxOut1 = conv.getPixelFromPointWorld(pt1);
		Pixel pxOut2 = conv.getPixelFromPointWorld(pt2);
		PointWorld ptOut1 = conv.getPointFromPixel(px1);
		PointWorld ptOut2 = conv.getPointFromPixel(px2);

		System.out.println(pxOut1);
		System.out.println(pxOut2);
		System.out.println(ptOut1);
		System.out.println(ptOut2);

		Assert.assertTrue(px1.toString().equals(pxOut1.toString()));
		Assert.assertTrue(pt1.toString().equals(ptOut1.toString()));
		Assert.assertTrue(px2.toString().equals(pxOut2.toString()));
		Assert.assertTrue(pt2.toString().equals(ptOut2.toString()));
	}

}
