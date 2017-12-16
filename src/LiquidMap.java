import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * The main class for LiquidMap project.
 *
 * @author brian
 *
 */
public class LiquidMap {

	private Settings settings;
	private HashMap<String, DataSource> dataSources = new HashMap<>();
	ArrayList<Trip> trips = new ArrayList<>();
	ArrayList<Trip> convTrips = new ArrayList<>();
	Converter converter;

	/**
	 * The script which calls each of LiquidMap steps in sequence.
	 */
	public LiquidMap() {
		readSettings();
		importDataSources();
		getTrips();
		buildConverter();
		convertTrips();
		animateTrips();
	}

	/**
	 * Reads the JSON file and sets the settings attribute.
	 */
	private void readSettings() {
		SettingsFileReader sfr = new SettingsFileReader();
		settings = sfr.getSettings();
	}

	/**
	 * Cycles through all Data Sources Descriptors in the JSON file and attempts
	 * to uses each to build datasources and put them into a HashMap so that
	 * they can be addressed later by name.
	 */
	private void importDataSources() {
		for (int i = 0; i < settings.rasterDataDescs.size(); i++) {
			String name = settings.rasterDataDescs.get(i).name;
			String mapFileName = settings.rasterDataDescs.get(i).mapFileName;
			PointWorld pt1 = settings.rasterDataDescs.get(i).point1;
			PointWorld pt2 = settings.rasterDataDescs.get(i).point2;
			Pixel px1 = settings.rasterDataDescs.get(i).pixel1;
			Pixel px2 = settings.rasterDataDescs.get(i).pixel2;
			RasterDataSource rds = new RasterDataSource(mapFileName, pt1, px1,
					pt2, px2);
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
	 * Generates the list of trips
	 */
	private void getTrips() {
		Random rdn = new Random();
		GHInterface ghi = new GHInterface(settings.cityMapFile);
		DataSource sourceBeg = dataSources.get(settings.routingVars.routeBeg);
		DataSource sourceEnd = dataSources.get(settings.routingVars.routeEnd);
		int goodTripCount = 0;
		while (goodTripCount < settings.routingVars.routeCount) {
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
		}
	}

	/**
	 * Builds a converter object capable of translating from world coordinates
	 * to the pixel coordinates of the output file.
	 */
	private void buildConverter() {
		PointWorld pointWorldUpperLeft = settings.outputVars.pointUpperLeft;
		PointWorld pointWorldLowerRight = settings.outputVars.pointLowerRight;
		Integer outputWidth = settings.outputVars.intCanvasWidth;
		converter = new Converter(pointWorldUpperLeft, pointWorldLowerRight,
				outputWidth);
	}

	/**
	 * Converts the trips from world coordinates to output file coordinates.
	 */
	private void convertTrips() {
		convTrips = converter.getConvertedListTrips(trips);
	}

	/**
	 * Generates the animated output html file.
	 */
	private void animateTrips() {
		Animator animator = new Animator();

		animator.animateTrips(convTrips, settings.outputVars.strFileName,
				settings.outputVars.intCanvasWidth,
				settings.outputVars.strCanvasColor,
				settings.outputVars.intLineWidth,
				settings.outputVars.intLineLength,
				settings.outputVars.strLineColorA,
				settings.outputVars.strLineColorB,
				settings.outputVars.strTextColor,
				settings.outputVars.dblTimeBetweenSpawns);
	}

	/**
	 * The main method.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		LiquidMap lm = new LiquidMap();
	}
}
