
public class PixelPointConv {

	private double degLonXPixConvFactor; // deg/pix
	private double degLatYPixConvFactor; // deg/pix
	private double lonAt0X; // deg
	private double latAt0Y; // deg

	/**
	 * Creates a PixelPointConv object using two reference Pixel-Point pairs.
	 *
	 * In other words, the conversion is based two points on a raster image
	 * where you can identify the pixel value and the lat-lon. This is most
	 * useful for input images where you can identify two features on the map
	 * and locate them on Google Maps to identify their locaions.
	 *
	 * Does not operate well around the poles or on the international dateline.
	 *
	 * @param pt1
	 *            First Reference Pixel (iX, iY)
	 * @param px1
	 *            First Reference Point (Lat Lon)
	 * @param pt2
	 *            Second Reference Pixel (iX, iY)
	 * @param px2
	 *            Second Reference Point (Lat Lon)
	 */
	public PixelPointConv(Point pt1, Pixel px1, Point pt2, Pixel px2) {
		double deltaLon = (pt2.getLon() - pt1.getLon());
		double deltaX = (px2.getPixelX() - px1.getPixelX());
		double deltaLat = (pt2.getLat() - pt1.getLat());
		double deltaY = (px2.getPixelY() - px1.getPixelY());
		degLonXPixConvFactor = deltaLon / deltaX;
		degLatYPixConvFactor = deltaLat / deltaY;
		lonAt0X = pt1.getLon() - degLonXPixConvFactor * px1.getPixelX();
		latAt0Y = pt1.getLat() - degLatYPixConvFactor * px1.getPixelY();

	}

	/**
	 * Creates a PixelPointConv object using a bounding box on a map defined by
	 * two Points and a width of the image in pixels.
	 *
	 * This will be most useful for defining output images where you can
	 * identify the region you want covered and you know the width of the image.
	 *
	 * Does not operate well around the poles or on the international dateline.
	 *
	 * @param ptUpperLeft
	 *            Point in the upper-left corner.
	 * @param ptLowerRight
	 *            Point in the lower-right corner.
	 * @param width
	 *            The image width.
	 */
	public PixelPointConv(Point ptUpperLeft, Point ptLowerRight, int width) {

		double deltaLat = ptUpperLeft.getLat() - ptUpperLeft.getLat();
		double deltaLon = ptUpperLeft.getLon() - ptUpperLeft.getLon();
		degLonXPixConvFactor = deltaLon / width;

		Double meanLat = (ptUpperLeft.getLat() + ptUpperLeft.getLat()) / 2;
		double sizeRatio = Math.cos(Math.toRadians(meanLat));

		int height = new Double(sizeRatio * width * deltaLon / deltaLat)
				.intValue();
		height = Math.abs(height);

		degLatYPixConvFactor = deltaLat / height;
		degLonXPixConvFactor = deltaLon / width;
		latAt0Y = ptUpperLeft.getLat();
		lonAt0X = ptUpperLeft.getLon();
	}

	public Pixel getPixFromPt(Point pt) {
		double lat = pt.getLat();
		double lon = pt.getLon();
		Double x = (lon - lonAt0X) / degLonXPixConvFactor;
		Double y = (lat - latAt0Y) / degLatYPixConvFactor;
		return new Pixel((int) Math.round(x), (int) Math.round(y));
	}

	public Point getPtFromPix(Pixel px) {
		double x = px.getPixelX();
		double y = px.getPixelY();
		double lon = degLonXPixConvFactor * x + lonAt0X;
		double lat = degLatYPixConvFactor * y + latAt0Y;
		return new Point(lat, lon);
	}

}
