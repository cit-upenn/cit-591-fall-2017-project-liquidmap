import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

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
		for (int i = 0; i < 100; i++) {
			randPixels.add(rds.getRandPixel());
		}
		for (int i = 0; i < randPixels.size(); i++) {
			System.out.print("X:" + randPixels.get(i).getPixelX() + ":");
			System.out.print("Y:" + randPixels.get(i).getPixelY() + ":");
			System.out.print("Red:" + randPixels.get(i).getRedValue() + ":");
			System.out.println("Weight:" + randPixels.get(i).getColorWeight());
		}
	}

}
