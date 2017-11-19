import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class reads in an image (GIF, PNG, JPEG, BMP, or WBMP)
 * from a file. It could also easily be written to accept a URL.
 * 
 * It will put the file into a "BufferedImage" object which 
 * has a ColorModel and a Raster of image data. 
 * 
 * The ColorModel provides color interpretation, and the 
 * Raster provides the access to specific pixels.
 * 
 * @author sgb
 *
 */
public class ImageReader {
	private BufferedImage img;
	private String filePath;
	
	public ImageReader(String filePath) {
		this.filePath = filePath;
		readImageFile();
	}
	
	private void readImageFile() {
		try {
			File inputFile = new File(filePath);
			img = ImageIO.read(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the img
	 */
	public BufferedImage getImg() {
		return img;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

}
