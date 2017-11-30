import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class RasterDataSource {
	private ArrayList<Pixel> pixels;
	private ImageReader imageReader;
	private BufferedImage img;
	
	/**
	 * The constructor creates an ArrayList of type Pixel
	 * in order to create a representation of the image along
	 * with each pixel's Red Channel RGB value.  
	 * 
	 * This is done using ImageReader. The Y location of the 
	 * pixel is also transformed into its negative value in order
	 * to set up easy conversion to a Cartesian plane with the 4 
	 * quadrants based on a selected city center XY pair.
	 * 
	 * @param fileName the name of the image to be read
	 */
	public RasterDataSource(String fileName) {
		imageReader = new ImageReader(fileName);
		img = imageReader.getImg();
		pixels = new ArrayList<>();
		
		//create Pixel ArrayList
		//TODO Add weights to each pixel
		
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
	/**
	 * @return the Pixel ArrayList
	 */
	public ArrayList<Pixel> getPixels() {
		return pixels;
	}
	
	/**
	 * @return a random Pixel
	 */
	public Pixel getRandPixel() {
		ArrayList<Pixel> pixelDeck = new ArrayList<>();
		for (Pixel dot : pixels) {
			pixelDeck.add(dot);
		}
		
		Random rand = new Random();
		Pixel chosenPixel = null;
		
		for (int i = 0; i < pixelDeck.size(); i++) {
			
			double randPixelWeight = pixelDeck.get(i).getColorWeight();
			double constraint = rand.nextFloat();
			
			if (randPixelWeight > constraint) {
				chosenPixel = pixelDeck.get(i);
				break;
			} else {
				pixelDeck.remove(i);
			}
		}
		return chosenPixel;
	}

}
