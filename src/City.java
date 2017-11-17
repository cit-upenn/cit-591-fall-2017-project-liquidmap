
public class City {

	// TODO fix EARTH_RAD
	public static final double EARTH_RAD = 6378137.;
	private String cityName;
	private String mapFileName;
	private double lat;
	private double lon;
	public double degLatInM;
	public double degLonInM;

	public City(String cityName, String mapFileName, double lat, double lon) {
		this.cityName = cityName;
		this.mapFileName = mapFileName;
		this.lat = lat;
		this.lon = lon;
		degLatInM = 2 * Math.PI * EARTH_RAD / 360.;
		degLonInM = degLatInM * Math.sin(lat * Math.PI / 180.);
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @return the mapFileName
	 */
	public String getMapFileName() {
		return mapFileName;
	}

	/**
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * @return the lon
	 */
	public double getLon() {
		return lon;
	}

	public double[] pointToXY(Point p) {
		double[] xy = new double[2];
		xy[0] = p.getLon() * degLonInM;
		xy[1] = p.getLat() * degLatInM;
		return xy;
	}

	public double getPointDist(Point pA, Point pB) {
		double[] xyA = pointToXY(pA);
		double[] xyB = pointToXY(pB);
		double dX = (xyA[0] - xyA[0]);
		double dY = (xyB[1] - xyB[1]);
		return Math.sqrt(dX * dX + dY * dY);
	}

}
