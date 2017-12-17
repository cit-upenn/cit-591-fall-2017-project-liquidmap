import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
/**
 * This class creates an ArrayList of type Pixel
 * in order to represent the image that is fed to it. 
 * 
 * This process relies on ImageReader and Pixel. 
 * 
 * A probability weight is added to each Pixel object based on its
 * red-channel RGB value (0-255). This is so that darker pixels, 
 * (redValues closer to 0), will be more likely to be chosen 
 * from the getRandPoint() method.
 * 
 * The weight is calculated as follows, using an exponential
 * probability density function: 
 * 
 * colorWeight = 1 / ((redValue + 1) ^ 1.1)
 * 
 * When choosing a random Pixel, the getRandPixel() method first 
 * randomly selects any Pixel regardless of color and then calculates 
 * a constraint. If the Pixel's weight is a larger number than the 
 * constraint, then it is returned. If not, the method tries again.
 * 
 * The constraint pulls a random number from this formula:
 * 
 * constraint = 1 / (lightest - darkest)
 * 
 * A Converter object is created in order to allow
 * this class to convert Pixels to Points.
 * 
 * @author - sgb
 * 
 */

public class RasterDataSource implements DataSource {
	private ArrayList<Pixel> pixels;
	private ImageReader imageReader;
	private BufferedImage img;
	private int lightest;
	private int darkest;
	
	private PointWorld pt1;
	private PointWorld pt2;
	private Pixel px1;
	private Pixel px2;
	
	private Converter converter;
	
	/**
	 * This constructor requires the name of the image file
	 * and two reference points - entered both in terms of their
	 * lat/lng and pixel x,y coordinates.
	 * 
	 * @param fileName the name of the image to be read
	 * @param pt1 the first reference PointWorld (lat/lon)
	 * @param px1 the first reference Pixel (x, y)
	 * @param pt2 the second reference PointWorld
	 * @param px2 the second reference Pixel
	 */

	public RasterDataSource(String fileName, PointWorld pt1, Pixel px1,
			PointWorld pt2, Pixel px2) throws Exception {
		imageReader = new ImageReader(fileName);
		img = imageReader.getImg();
		pixels = new ArrayList<>();
		
		//reference points
		
		this.pt1 = pt1;
		this.px1 = px2;
		this.pt2 = pt2;
		this.px2 = px2;
		
		converter = new Converter(pt1, px1, pt2, px2);
		
		//create Pixel ArrayList
		
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
			
				int redValue = new Color(img.getRGB(x, y)).getRed();
				double weight = 0.0d;
				//Make pure white (255), often used in boundaries between
				//census tracts or counties and bodies of water, have a 0% chance 
				if (redValue != 255) {
					weight =  (double) 
							(1 / (Math.pow(redValue + 1, 1.1)));
				} else {
					weight = 0;
				}
				Pixel pixel = new Pixel(x, y, redValue, weight) ;
				pixels.add(pixel);
			}
		}
		
		//sort Pixel AL to extract lightest and darkest pixels
		
		Collections.sort(pixels);
		lightest = pixels.get(pixels.size() - 1).getRedValue();
		darkest = pixels.get(0).getRedValue();
		
		//make all Pixels have a weight of 1 if img. is all white or all red (all 255-valued).
		if (darkest == 255 && lightest == 255) {
			for (int i = 0; i < pixels.size(); i++) {
				pixels.get(i).setColorWeight(1);
			}
		}
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
		
		while (count < 10000) {
			
			int randIndex = rand.nextInt(pixels.size());
			double randPixelWeight = pixels.get(randIndex).getColorWeight();
			
			int denominator = rand.nextInt(lightest - darkest) + darkest + 1;
			double constraint = (double) 1 / denominator;

			if (randPixelWeight >= constraint) {
				chosenPixel = pixels.get(randIndex);
				break;
			}
			count++;
		}
		return chosenPixel;
	}
	/**
	 * generates a random PointWorld object based on the
	 * random Pixel chosen by getRandPixel
	 * 
	 * @return a PointWorld object at random
	 */
	@Override
	public PointWorld getRandPoint() {
		Pixel pixel = getRandPixel();
		PointWorld randPoint = converter.getPointFromPixel(pixel);
		return randPoint;
	}

	/**
	 * @return the Pixel ArrayList
	 */
	public ArrayList<Pixel> getPixels() {
		return pixels;
	}
	
}
