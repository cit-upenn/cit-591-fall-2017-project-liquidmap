import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PixelReader {
	private ArrayList<Pixel> pixels;
	private ImageReader imageReader;
	private BufferedImage img;
	
	
	public PixelReader(String fileName) {
		imageReader = new ImageReader(fileName);
		img = imageReader.getImg();
		pixels = new ArrayList<>();
		
		//create Pixel ArrayList
		
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int redValue = new Color(img.getRGB(x, y)).getRed();
				Pixel pixel = new Pixel(x, y, redValue) ;
				pixels.add(pixel);
			}
		}
	}
	/**
	 * @return the img
	 */
	public BufferedImage getImg() {
		return img;
	}

	public ArrayList<Pixel> getPixels() {
		return pixels;
	}
}
