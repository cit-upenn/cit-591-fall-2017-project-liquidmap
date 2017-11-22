/**
 * This class will represent the pixels in 
 * the image file read in by ImageReader.
 * 
 * A Pixel object will have an (x,y) value corresponding
 * to its 'coordinates' in the image, where the top left 
 * of the image corresponds to an (x,y) of (0,0).  
 * 
 * It will also have an RGB value corresponding to 
 * the image's color at that location.
 * 
 * @author sgb
 *
 */
public class Pixel {
	private int redValue;
	private int pixelX;
	private int pixelY;
	
	public Pixel(int pixelX, int pixelY, int redValue) {
		this.pixelX = pixelX;
		this.pixelY = pixelY;
		this.redValue = redValue;
	}

	/**
	 * @return the rgbValue
	 */
	public int getRedValue() {
		return redValue;
	}
	/**
	 * @return the pixelX
	 */
	public int getPixelX() {
		return pixelX;
	}

	/**
	 * @return the pixelY
	 */
	public int getPixelY() {
		return pixelY;
	}

}
