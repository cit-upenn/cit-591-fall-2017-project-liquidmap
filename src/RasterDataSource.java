import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RasterDataSource {
	private ArrayList<Pixel> pixels;
	private ArrayList<Pixel> centered;
	private ImageReader imageReader;
	private BufferedImage img;
	private double latConversionFactor;
	private double lngConversionFactor;
	
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
			int pixelXCenter, int pixelYCenter) {
		
		ArrayList<Pixel> centeredPixelArray = arrayToCenter;
		
		for (int i = 0; i < arrayToCenter.size(); i++) {
			centeredPixelArray.get(i).setPixelX(arrayToCenter.get(i).getPixelX() 
					- Math.abs(pixelXCenter));
			centeredPixelArray.get(i).setPixelY(arrayToCenter.get(i).getPixelY() 
					+ Math.abs(pixelYCenter));
		}
		
		return centeredPixelArray;
	}
	/**
	 * This method calculates two conversion factors: one for latitude
	 * and one for longitude, based on two points on the map.
	 * 
	 * It requires as arguments the city center pixelXY, another
	 * location's pixelXY (must be different in both the X and Y direction)
	 * as well as the city center lat/long and another location's lat/long.
	 * 
	 * @param pixelXCenter pixel X-coord at city center
	 * @param pixelYCenter pixel Y-coord at city center
	 * @param latCenter latitude at city center
	 * @param lngCenter longitude at city center
	 * @param pixelXArb pixel X-coord at another location
	 * @param pixelYArb pixel Y-coord at another location
	 * @param latArb latitude at another location
	 * @param lngArb longitude at another location
	 */
	public void calculateMapScale(int pixelXCenter, int pixelYCenter,
			double latCenter, double lngCenter, int pixelXArb, int pixelYArb, 
			double latArb, double lngArb) {
		
		double latDiff = Math.abs(latArb - latCenter);
		double lngDiff = Math.abs(lngArb - lngCenter);
		
		int pixelXDiff = Math.abs(pixelXArb - pixelXCenter);
		int pixelYDiff = Math.abs(pixelYArb - pixelYCenter);
		
		latConversionFactor = latDiff / pixelXDiff;
		lngConversionFactor = lngDiff / pixelYDiff;
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
	 * @return the centered Pixel ArrayList
	 */
	public ArrayList<Pixel> getCenteredPixels() {
		return centered;
	}
	/**
	 * @return the latConversionFactor
	 */
	public double getLatConversionFactor() {
		return latConversionFactor;
	}
	/**
	 * @return the lngConversionFactor
	 */
	public double getLngConversionFactor() {
		return lngConversionFactor;
	}
}
