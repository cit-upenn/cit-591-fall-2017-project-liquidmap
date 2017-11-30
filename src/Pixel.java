/**
 * This class will represent the pixels in the image file read in by
 * ImageReader.
 *
 * A Pixel object will have an (x,y) value corresponding to its 'coordinates' in
 * the image, where the top left of the image corresponds to an (x,y) of (0,0).
 *
 * It will also have a red value corresponding to the image's color at that
 * location in the red channel. Can vary from 0 - 255.
 *
 * Using red was chosen arbitrarily. All maps uploaded to this project will be
 * in greyscale, so we are merely using the red channel to track the
 * white-to-black gradient.
 *
 * @author sgb
 *
 */
public class Pixel {

	private int redValue;
	private double colorWeight;
	private int pixelX;
	private int pixelY;

	public Pixel(int pixelX, int pixelY, int redValue) {
		this.pixelX = pixelX;
		this.pixelY = pixelY;
		this.redValue = redValue;
	}

	public Pixel(int pixelX, int pixelY) {
		this(pixelX, pixelY, 0);
	}

	/**
	 * @return the rgbValue
	 */
	public int getRedValue() {
		return redValue;
	}
	
	/**
	 * @param colorWeight the colorWeight to set
	 */
	public void setColorWeight(double colorWeight) {
		this.colorWeight = colorWeight;
	}

	/**
	 * @return the colorWeight
	 */
	public double getColorWeight() {
		return colorWeight;
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

	/**
	 * @param redValue
	 *            the redValue to set
	 */
	public void setRedValue(int redValue) {
		this.redValue = redValue;
	}

	/**
	 * @param pixelX
	 *            the pixelX to set
	 */
	public void setPixelX(int pixelX) {
		this.pixelX = pixelX;
	}

	/**
	 * @param pixelY
	 *            the pixelY to set
	 */
	public void setPixelY(int pixelY) {
		this.pixelY = pixelY;
	}

}
