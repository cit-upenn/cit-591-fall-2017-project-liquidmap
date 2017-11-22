import static org.junit.Assert.*;
import java.awt.image.BufferedImage;
import org.junit.Test;

public class ImageReaderTest {
	
	@Test
	public void testImageIOWidth() {
		ImageReader imageReader = new ImageReader("PhillyPopDensity2015.png");
		BufferedImage img = imageReader.getImg();
		assertEquals("Image width should be 1280", 1280, img.getWidth());
	}
	@Test
	public void testImageIOHeight() {
		ImageReader imageReader = new ImageReader("PhillyPopDensity2015.png");
		BufferedImage img = imageReader.getImg();
		assertEquals("Image height should be 720", 720, img.getHeight());
	}
}
