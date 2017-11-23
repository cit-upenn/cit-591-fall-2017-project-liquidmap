import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class RasterDataSourceTest {
	private ArrayList<Pixel> pixels;
	private RasterDataSource RasterDataSource;
	private ArrayList<Pixel> pixels2;
	private RasterDataSource RasterDataSource2;
	private ArrayList<Pixel> centered;
	
	@Before
	public void setup() {
		this.RasterDataSource = new RasterDataSource("PhillyPopDensity2012.png");
		this.pixels = RasterDataSource.getPixels();
		this.RasterDataSource2 = new RasterDataSource("PhillyPopDensity2012.png");
		this.pixels2 = RasterDataSource2.getPixels();
		this.centered = RasterDataSource2.centerPixelArray(pixels2, 138, 358);
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
	public void testPixelCenteredX() {
		assertEquals("The first pixel should have an X of -138", 
				-138, centered.get(0).getPixelX());
	}
	@Test
	public void testPixelCenteredY() {
		assertEquals("The first pixel should have an X of 358", 
				358, centered.get(0).getPixelY());
	}
	@Test
	public void testPixelCenteredRedValue() {
		assertEquals("The first pixel should still have a red value of 165", 
				165, centered.get(0).getRedValue());
	}

}
