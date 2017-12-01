import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RasterDataSource {
	private ArrayList<Pixel> pixels;
	private ImageReader imageReader;
	private BufferedImage img;
	private int totalPixels;
	private int lightest;
	private int darkest;
	
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
		totalPixels = img.getWidth() * img.getHeight();
		
		//create Pixel ArrayList
		//TODO Add weights to each pixel
		
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
			
				int redValue = new Color(img.getRGB(x, y)).getRed();
				float weight = 0.0f;
				if (redValue != 255) {
					weight =  (float) ((float) 1 / (Math.pow(redValue, 1.1)));
				} else {
					weight = 0;
				}
				Pixel pixel = new Pixel(x, y, redValue, weight) ;
				pixels.add(pixel);
			}
		}
		Collections.sort(pixels);
		lightest = pixels.get(pixels.size() - 1).getRedValue();
		darkest = pixels.get(0).getRedValue();
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
	 * @return a random Pixel, with a higher likelihood of being
	 * chosen going to the darker pixels.
	 */
	public Pixel getRandPixel() {
		ArrayList<Pixel> pixels = getPixels();
		
		Random rand = new Random();
		Pixel chosenPixel = null;
		int count = 0;
		
		while (count < 100000) {
			
			int randIndex = rand.nextInt(pixels.size());
			double randPixelWeight = pixels.get(randIndex).getColorWeight();
			
			float constraint = (float) ((float) 
					1 / (rand.nextInt(lightest - darkest) + darkest + 1));
			
			if (randPixelWeight >= constraint) {
				chosenPixel = pixels.get(randIndex);
				break;
			}
			count++;
		}
		return chosenPixel;
	}

}
