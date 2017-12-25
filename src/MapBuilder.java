import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * The main class for the LiquidMaps program.
 * Calls methods in sequence to read settings, import data, generate trips, and produce an animation.
 * @author brian
 */
public class MapBuilder {

	private Settings settings;
	private HashMap<String, DataSource> dataSources = new HashMap<>();
	ArrayList<Trip> trips = new ArrayList<>();
	ArrayList<Trip> convTrips = new ArrayList<>();
	Converter converter;
	private TripAnalysis tripAnalysis;

	/**
	 * Constructor. Calls each of the LiquidMaps steps in sequence.
	 */
	public MapBuilder() {
		try {
			readSettings();
			importDataSources();
			routeTrips();
			performTripAnalysis();
			buildConverter();
			convertTrips();
			animateTrips();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Terminating program");
		}
	}

	/**
	 * Reads the JSON file (settings.json) and initializes the settings variables.
	 */
	private void readSettings() throws Exception {
		SettingsFileReader sfr = new SettingsFileReader();
		settings = sfr.getSettings();
	}

	/**
	 * Cycles through all DataSources Descriptors in the JSON file and attempts
	 * to use each to build data sources and put them into a HashMap so that
	 * they can be addressed later by name.
	 */
	private void importDataSources() throws Exception {
		for (int i = 0; i < settings.rasterDataDescs.size(); i++) {
			String name = settings.rasterDataDescs.get(i).name;
			String mapFileName = settings.rasterDataDescs.get(i).mapFileName;
			PointWorld pt1 = settings.rasterDataDescs.get(i).point1;
			PointWorld pt2 = settings.rasterDataDescs.get(i).point2;
			Pixel px1 = settings.rasterDataDescs.get(i).pixel1;
			Pixel px2 = settings.rasterDataDescs.get(i).pixel2;
			RasterDataSource rds = new RasterDataSource(mapFileName, pt1, px1, pt2, px2);
			dataSources.put(name, rds);
		}

		for (int i = 0; i < settings.vectorDataDescs.size(); i++) {
			String name = settings.vectorDataDescs.get(i).name;
			String vecFileName = settings.vectorDataDescs.get(i).vecFileName;
			VectorDataSource vds = new VectorDataSource(vecFileName);
			dataSources.put(name, vds);
		}
	}

	/**
	 * Generates a list of trips using GraphHopper.
	 */
	private void routeTrips() throws Exception {
		Random rdn = new Random();
		GHInterface ghi = new GHInterface(settings.cityMapFile);
		DataSource sourceBeg = dataSources.get(settings.routingVars.routeBeg);
		DataSource sourceEnd = dataSources.get(settings.routingVars.routeEnd);
		int goodTripCount = 0;
		int tripRouteTrials = 0;
		while (goodTripCount < settings.routingVars.routeCount) {
			tripRouteTrials++;
			PointWorld ptBeg = sourceBeg.getRandPoint();
			PointWorld ptEnd = sourceEnd.getRandPoint();
			Trip trip = ghi.getTrip(ptBeg, ptEnd);
			if (trip.maxTime() > settings.routingVars.routeMinTime
					&& trip.maxTime() < settings.routingVars.routeMaxTime) {
				double tVar = settings.routingVars.timeStartVariance;
				double tOff = tVar * rdn.nextDouble();
				double sVar = settings.routingVars.speedVariance;
				double sMult = sVar * rdn.nextDouble() - sVar / 2 + 1;
				trip.scaleTime(sMult);
				trip.offsetTime(tOff);
				trips.add(trip);
				goodTripCount++;
			}
			if (tripRouteTrials > 100 * settings.routingVars.routeCount) {
				throw new Exception("It seems to be excessivly difficult to "
						+ "match routing criteria");
			}
		}
	}

	/**
	 * Generates statistics on the routed trips.
	 * @throws Exception
	 */
	private void performTripAnalysis() throws Exception {
		tripAnalysis = new TripAnalysis(trips);
		if (trips.size() == 0) {
			throw new Exception("Cannot analyze trips or input has no trips");
		}
		double[] timeStats = tripAnalysis.getTimeStats();
		double[] distanceStats = tripAnalysis.getDistanceStats();

		System.out.println();
		System.out.println("=========================================");
		System.out.println("Descriptive Statistics for Trip Times (minutes):");
		System.out.printf("Min: %.2f | Mean: %.2f | SD: %.2f | Max: %.2f \n",
				timeStats[0], timeStats[1], timeStats[2], timeStats[3]);
		System.out.println("=========================================");
		System.out
				.println("Descriptive Statistics for Trip Distances (miles):");
		System.out.printf("Min: %.2f | Mean: %.2f | SD: %.2f | Max: %.2f \n",
				distanceStats[0], distanceStats[1], distanceStats[2],
				distanceStats[3]);
		System.out.println("=========================================");
		System.out.println();
	}

	/**
	 * Builds a converter object capable of translating from world coordinates
	 * to pixel coordinates (for the output file).
	 */
	private void buildConverter() throws Exception {
		PointWorld pointWorldUpperLeft = settings.outputVars.pointUpperLeft;
		PointWorld pointWorldLowerRight = settings.outputVars.pointLowerRight;
		Integer outputWidth = settings.outputVars.intCanvasWidth;
		converter = new Converter(pointWorldUpperLeft, pointWorldLowerRight,
				outputWidth);
	}

	/**
	 * Converts the trips from world coordinates to pixel coordinates.
	 */
	private void convertTrips() {
		convTrips = converter.getConvertedListTrips(trips);
	}

	/**
	 * Generates the animated output .html file.
	 */
	private void animateTrips() {
		Animator animator = new Animator();
		
		animator.animateTrips(convTrips,
				settings.outputVars.strFileName,
				settings.outputVars.strPageTitle,
				settings.outputVars.strCanvasText,
				settings.outputVars.intCanvasWidth,
				settings.outputVars.strCanvasColor,
				settings.outputVars.intLineWidth,
				settings.outputVars.intLineLength,
				settings.outputVars.isKeepLinesVisible,
				settings.outputVars.strLineColorA,
				settings.outputVars.strLineColorB,
				settings.outputVars.strTextColor,
				settings.outputVars.dblTimeBetweenSpawns,
				settings.outputVars.dblMergeDistance);
	}
	
	/**
	 * Returns the ArrayList of Trips that the buildTrips() method creates.
	 * @return an ArrayList of Trips
	 */
	public ArrayList<Trip> getTrips() {
		return trips;
	}

	/**
	 * The main method. Calls the LiquidMap constructor, which performs all the steps of the LiquidMaps program.
	 * @param args
	 */
	public static void main(String[] args) {
		new MapBuilder();
	}
}
