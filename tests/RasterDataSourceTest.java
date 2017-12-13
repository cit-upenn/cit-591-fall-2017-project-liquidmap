import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RasterDataSourceTest {
	private ArrayList<Pixel> pixels;
	private RasterDataSource rds;
	private Pixel firstPixel;
	private PointWorld pt1 = new PointWorld(39,42);
	private PointWorld pt2 = new PointWorld(40,41);
	private Pixel px1 = new Pixel(0,0);
	private Pixel px2 = new Pixel(10,10);
	
	@Before
	public void setup() {
		this.rds = new RasterDataSource("PhillyPopDensity2012.png", pt1, px1, pt2, px2);
		this.pixels = rds.getPixels();
		this.firstPixel = pixels.get(0);
	}
	@Test
	public void testPixelX() {
		assertTrue("The first pixel should have an X between 0 and 550", 
				(firstPixel.getPixelX() <= 550 && firstPixel.getPixelX() >= 0));
	}
	@Test
	public void testPixelY() {
		assertTrue("The first pixel should have an X between 0 and 550", 
				(firstPixel.getPixelY() <= 550 && firstPixel.getPixelY() >= 0));
	}
	@Test
	public void testPixelRedValue() {
		Pixel randPixel = rds.getRandPixel();
		int redValue = randPixel.getRedValue();
		assertTrue("Random pixel should have a redValue between 0 and 255, ", 
				(redValue <= 255 && redValue >= 0));
	}
	@Test
	public void testGetRandPixel() {
		ArrayList<Pixel> randPixels = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			randPixels.add(rds.getRandPixel());
		}
		int fiftyCount = 0;
		int hundCount = 0;
		int hundFiftyCount = 0;
		int rest = 0;
		for (Pixel dot : randPixels) {
			if (dot.getRedValue() <= 50) {
				fiftyCount++;
			} else if (dot.getRedValue() <= 100 && dot.getRedValue() > 50) {
				hundCount++;
			} else if (dot.getRedValue() <= 150 && dot.getRedValue() > 100) {
				hundFiftyCount++;
			} else {
				rest++;
			}
		}
		System.out.println("<=50: " + fiftyCount + " | " + ((double) fiftyCount/10) + "%");
		System.out.println(">50<=100: " + hundCount + " | " + ((double) hundCount/10) + "%");
		System.out.println(">100<=150: " + hundFiftyCount + " | " + ((double) hundFiftyCount/10) + "%");
		System.out.println(">150: " + rest + " | " + ((double) rest/10) + "%");
	}
	@Test
	public void testGetRandPoint() {
		Point randPoint = rds.getRandPoint();
		Assert.assertNotNull("A random point should not be null", randPoint);
	}

	//Test
	//Test again
	//Test third time

}
