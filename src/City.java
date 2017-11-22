
/**
 * This class defines the city in which the GHServer will operate. It stores
 * informations such as the city name, the map file name, and a relevant
 * Latitude and Longitude.
 *
 * It also is responsible for geographic calculations. Since it owns a Lat and
 * Long, it can precalcuate conversion factors between [lat, long] and [X, Y].
 *
 * TODO Is that a good idea? We need to put this math somewhere. - Create its
 * own class (but then everyone would need to create it with the la Or should we
 * create another class to do this math. My thinking is that if we did so, we
 * would either need to pass it around between the classes as an argument (bad
 * for
 *
 * @author brian
 *
 */
public class City {

	public static final double EARTH_RAD = 6378137.;
	private String cityName;
	private String mapFileName;
	private double lat;
	private double lon;
	private double degLatInM;
	private double degLonInM;

	/**
	 * Constructs a City object and initializes geographic computations relative
	 * to that city's latitude and longitude.
	 *
	 * @param cityName
	 * @param mapFileName
	 * @param lat
	 * @param lon
	 */
	public City(String cityName, String mapFileName, double lat, double lon) {
		this.cityName = cityName;
		this.mapFileName = mapFileName;
		this.lat = lat;
		this.lon = lon;
		degLatInM = 2 * Math.PI * EARTH_RAD / 360.;
		degLonInM = degLatInM * Math.sin(lat * Math.PI / 180.);
	}

	/**
	 * Get's the city name.
	 *
	 * @return the city name
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * Gets the map file name.
	 *
	 * @return The map file name
	 */
	public String getMapFileName() {
		return mapFileName;
	}

	/**
	 * Gets the city latitude in degrees.
	 *
	 * @return The latitude of the city
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * Gets the city longitude in degrees.
	 *
	 * @return The longitude of the city
	 */
	public double getLon() {
		return lon;
	}

	/**
	 * Takes a point consisting of Lat and Long and constructs and XY pair
	 * representing the location in meters relative to City.lat, City.lon where
	 * X is the distance along the Easterly and Y is the distance along the
	 * Northerly direction. Distance is measured in meters.
	 *
	 * @param p
	 *            A Point to be converted.
	 * @return [x, y] where X is East in meters and Y is North in meters.
	 */
	public double[] pointToXY(Point p) {
		double[] xy = new double[2];
		xy[0] = p.getLon() * degLonInM;
		xy[1] = p.getLat() * degLatInM;
		return xy;
	}

	/**
	 * Gets a distance in meters between two points in meters.
	 *
	 * @param pA
	 *            The first point
	 * @param pB
	 *            The other point
	 * @return The distance in meters
	 */
	public double getPointDist(Point pA, Point pB) {
		double[] xyA = pointToXY(pA);
		double[] xyB = pointToXY(pB);
		double dX = (xyA[0] - xyB[0]);
		double dY = (xyA[1] - xyB[1]);
		return Math.sqrt(dX * dX + dY * dY);
	}

	/**
	 * Gets the number of meters per degree latitude for the location of the
	 * city.
	 *
	 * @return The number of meters per degree for the location of the city
	 */
	public double getDegLatInM() {
		return this.degLatInM;
	}

	/**
	 * Gets the number of meters per degree longitude for the location of the
	 * city.
	 *
	 * @return The number of meters per degree for the location of the city
	 */
	public double getDegLonInM() {
		return this.degLonInM;
	}

}
