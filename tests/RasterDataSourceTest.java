import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class RasterDataSourceTest {
	private ArrayList<Pixel> pixels;
	private RasterDataSource rds;
	
	@Before
	public void setup() {
		this.rds = new RasterDataSource("PhillyPopDensity2012.png");
		this.pixels = rds.getPixels();
	}
	@Test
	public void testPixelX() {
		assertEquals("The first pixel should have an X of 0", 
				0, pixels.get(0).getPixelX());
	}
	@Test
	public void testPixelY() {
		assertEquals("The first pixel should have an Y of 0", 
				0, pixels.get(0).getPixelY());
	}
	@Test
	public void testPixelRedValue() {
		assertEquals("The first pixel a red value of 165", 
				165, pixels.get(0).getRedValue());
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
		System.out.println(fiftyCount + " | " + ((double) fiftyCount/10) + "%");
		System.out.println(hundCount + " | " + ((double) hundCount/10) + "%");
		System.out.println(hundFiftyCount + " | " + ((double) hundFiftyCount/10) + "%");
		System.out.println(rest + " | " + ((double) rest/10) + "%");
	}

}
