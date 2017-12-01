import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
/**
 * This class creates an ArrayList of type Pixel
 * in order to create a representation of the image along
 * with each pixel's Red Channel RGB value.  
 * 
 * This is done using ImageReader. A probability weight 
 * is also added to each Pixel object based on its
 * redValue so that darker pixels (redValues closer to 0)
 * are more likely to be chosen from the getRandPoint() 
 * method.
 * 
 * The weight is calculated as follows: 
 * 
 * colorWeight = 1 / ((redValue + 1) ^ 1.1)
 * 
 * When choosing a random Pixel adjusted for the probability
 * weights, the getRandPixel() method first randomly selects any Pixel
 * regardless of color, and then applies a constraint.  If the weight is 
 * a larger number than the constraint, then it is chosen.  If not, the
 * method tries again.
 * 
 * The constraint is calculated as follows:
 * 
 * constraint = 1 / randInt,
 * 
 * where the randInt is defined as any integer between the 
 * lightest color pixel and the darkest color pixel chosen with 
 * 
 * 
 * A PixelPointConv object is created in order to allow
 * this DataSource type class to convert Pixels to Points.
 * 
 * @author - sgb
 * 
 */

public class RasterDataSource implements DataSource {
	private ArrayList<Pixel> pixels;
	private ImageReader imageReader;
	private BufferedImage img;
	private int totalPixels;
	private int lightest;
	private int darkest;
	
	private Point pt1;
	private Point pt2;
	private Pixel px1;
	private Pixel px2;
	
	private PixelPointConv ppc;
	
	/**
	 * This constructor requires the name of the image file
	 * and two reference points - entered both in terms of their
	 * lat/lng and pixel x,y coordinates.
	 * 
	 * @param fileName the name of the image to be read
	 * @param pt1 the first reference Point (Lat/Lng)
	 * @param px1 the first reference Pixel (X,Y)
	 * @param pt2 the second reference Point
	 * @param px2 the second reference Pixel
	 */
	
	public RasterDataSource(String fileName, Point pt1, Pixel px1, Point pt2,
			Pixel px2) {
		imageReader = new ImageReader(fileName);
		img = imageReader.getImg();
		pixels = new ArrayList<>();
		totalPixels = img.getWidth() * img.getHeight();
		
		//reference points
		
		this.pt1 = pt1;
		this.px1 = px2;
		this.pt2 = pt2;
		this.px2 = px2;
		
		ppc = new PixelPointConv(pt1, px1, pt2, px2);
		
		//create Pixel ArrayList
		
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
			
				int redValue = new Color(img.getRGB(x, y)).getRed();
				float weight = 0.0f;
				if (redValue != 255) {
					weight =  (float) 
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
	}
	/**
	 * Same constructor but without reference points.
	 * 
	 * You won't be able to call getRandPoint() if you use
	 * this constructor
	 * 
	 * @param fileName
	 */
	public RasterDataSource(String fileName) {
		imageReader = new ImageReader(fileName);
		img = imageReader.getImg();
		pixels = new ArrayList<>();
		totalPixels = img.getWidth() * img.getHeight();
		
		//create Pixel ArrayList
		
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
			
				int redValue = new Color(img.getRGB(x, y)).getRed();
				float weight = 0.0f;
				if (redValue != 255) {
					weight =  (float) 
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
	}
	
	/**
	 * @return a random Pixel, with a higher likelihood of being
	 * chosen going to the darker pixels.
	 */
	
	public Pixel getRandPixel() throws NullPointerException {
		ArrayList<Pixel> pixels = getPixels();
		
		Random rand = new Random();
		Pixel chosenPixel = null;
		int count = 0;
		
		while (count < 10000) {
			
			int randIndex = rand.nextInt(pixels.size());
			double randPixelWeight = pixels.get(randIndex).getColorWeight();
			
			int denominator = rand.nextInt(lightest - darkest) 
					+ darkest + 1;
			float constraint = (float) (1 / denominator);

			if (randPixelWeight >= constraint) {
				chosenPixel = pixels.get(randIndex);
				break;
			}
			count++;
		}
		return chosenPixel;
	}

	@Override
	public Point getRandPoint() throws NullPointerException {
		Pixel pixel = getRandPixel();
		Point randPoint = ppc.getPtFromPix(pixel);
		return randPoint;
	}
	
	//****Instance Variable Getters****//
	
	/**
	 * @return the totalPixels
	 */
	public int getTotalPixels() {
		return totalPixels;
	}

	/**
	 * @return the lightest
	 */
	public int getLightest() {
		return lightest;
	}

	/**
	 * @return the darkest
	 */
	public int getDarkest() {
		return darkest;
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
	 * @return the pt1
	 */
	public Point getPt1() {
		return pt1;
	}
	/**
	 * @return the pt2
	 */
	public Point getPt2() {
		return pt2;
	}
	/**
	 * @return the px1
	 */
	public Pixel getPx1() {
		return px1;
	}
	/**
	 * @return the px2
	 */
	public Pixel getPx2() {
		return px2;
	}
}
