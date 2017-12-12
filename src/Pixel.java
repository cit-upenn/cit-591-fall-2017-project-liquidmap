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
public class Pixel implements Comparable<Pixel> {

	private int redValue;
	private double colorWeight;
	private int pixelX;
	private int pixelY;

	public Pixel(int pixelX, int pixelY, int redValue, double colorWeight) {
		this.pixelX = pixelX;
		this.pixelY = pixelY;
		this.redValue = redValue;
		this.colorWeight = colorWeight;
	}

	public Pixel(int pixelX, int pixelY) {
		this(pixelX, pixelY, 0, 0);
	}

	/**
	 * @return the rgbValue
	 */
	public int getRedValue() {
		return redValue;
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
	 * Overriding the compareTo method of the interface Comparable:
	 * 
	 * This method will compare Pixels based on their redValue.
	 */
	@Override
	public int compareTo(Pixel o) {
		int own = redValue;
		int other = o.redValue;
		if (own < other) {
			return -1;
		} else if (own == other) {
			return 0;
		} else {
			return 1;
		}
	}
	/**
	 * This method overrides Object's toString() method
	 * in order to display Pixels more pleasantly.
	 */
	@Override
	public String toString() {
		String str = "(X: " + this.pixelX + "  Y: " + this.pixelY + "  v: "
				+ this.redValue + ")";
		return str;
	}

}
