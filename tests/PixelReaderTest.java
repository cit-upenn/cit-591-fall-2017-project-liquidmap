import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Test;

public class PixelReaderTest {

	@Test
	public void testPixelX() {
		PixelReader pixelReader = new PixelReader("PhillyPopDensity2015.png");
		ArrayList<Pixel> pixels = pixelReader.getPixels();
		assertEquals("The first pixel should have an X of 0", 0, pixels.get(0).getPixelX());
	}
	@Test
	public void testPixelY() {
		PixelReader pixelReader = new PixelReader("PhillyPopDensity2015.png");
		ArrayList<Pixel> pixels = pixelReader.getPixels();
		assertEquals("The first pixel should have an Y of 0", 0, pixels.get(0).getPixelY());
	}
	@Test
	public void testPixelRedValue() {
		PixelReader pixelReader = new PixelReader("PhillyPopDensity2015.png");
		ArrayList<Pixel> pixels = pixelReader.getPixels();
		assertEquals("The first pixel a red value of 131", 131, pixels.get(0).getRedValue());
	}

}
