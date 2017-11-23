import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
		
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int redValue = new Color(img.getRGB(x, y)).getRed();
				Pixel pixel = new Pixel(x, y, redValue) ;
				pixels.add(pixel);
			}
		}
	}
	/**
	 * This method modifies the Xs and Ys of the Pixel ArrayList
	 * such that the "center" of the city is now  at (0,0),
	 * and essentially converts the location of each Pixel to a location
	 * in a Cartesian plane. 
	 * 
	 * The input must be using the X and Y of the 
	 * pixels in the image, not lat and long.
	 * 
	 * @param arrayToCenter the ArrayList of type Pixel to center
	 * @param xAtCenter the arbitrary X coordinate at the city center
	 * @param yAtCenter the arbitrary Y coordinate at the city center
	 * 
	 * @return a centered ArrayList of type Pixel
	 */
	public ArrayList<Pixel> centerPixelArray(ArrayList<Pixel> arrayToCenter, 
			int pixelXAtCenter, int pixelYAtCenter) {
		
		ArrayList<Pixel> centeredPixelArray = arrayToCenter;
		
		for (int i = 0; i < arrayToCenter.size(); i++) {
			centeredPixelArray.get(i).setPixelX(arrayToCenter.get(i).getPixelX() 
					- Math.abs(pixelXAtCenter));
			centeredPixelArray.get(i).setPixelY(arrayToCenter.get(i).getPixelY() 
					+ Math.abs(pixelYAtCenter));
		}
		
		return centeredPixelArray;
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
}
