import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class LiquidMap {

	private Settings settings;
	private HashMap<String, DataSource> dataSources = new HashMap<>();
	ArrayList<Trip> trips = new ArrayList<>();

	public LiquidMap() {
		readSettings();
		importDataSources();
		getTrips();
		// convertToPixelTrips();
		// generateOutput();
	}

	private void readSettings() {
		SettingsFileReader sfr = new SettingsFileReader();
		settings = sfr.getSettings();
	}

	private void importDataSources() {
		for (int i = 0; i < settings.rasterDataDescs.size(); i++) {
			String name = settings.rasterDataDescs.get(i).name;
			String mapFileName = settings.rasterDataDescs.get(i).mapFileName;
			Point pt1 = settings.rasterDataDescs.get(i).point1;
			Point pt2 = settings.rasterDataDescs.get(i).point2;
			Pixel px1 = settings.rasterDataDescs.get(i).pixel1;
			Pixel px2 = settings.rasterDataDescs.get(i).pixel2;
			RasterDataSource rds = new RasterDataSource(mapFileName);
			dataSources.put(name, rds);
		}

		for (int i = 0; i < settings.vectorDataDescs.size(); i++) {
			String name = settings.vectorDataDescs.get(i).name;
			String vecFileName = settings.vectorDataDescs.get(i).vecFileName;
			VectorDataSource vds = new VectorDataSource(vecFileName);
			dataSources.put(name, vds);
		}
	}

	private void getTrips() {
		Random rdn = new Random();
		GHInterface ghi = new GHInterface(settings.cityMapFile);
		DataSource sourceBeg = dataSources.get(settings.routingVars.routeBeg);
		DataSource sourceEnd = dataSources.get(settings.routingVars.routeEnd);
		int goodTripCount = 0;
		while (goodTripCount < settings.routingVars.routeCount) {
			Point ptBeg = sourceBeg.getRandPoint();
			Point ptEnd = sourceEnd.getRandPoint();
			Trip trip = ghi.getTrip(ptBeg, ptEnd);
			if (trip.maxTime() < settings.routingVars.routeMaxTime) {
				double tVar = settings.routingVars.timeStartVariance;
				double tOff = tVar * rdn.nextDouble();
				double sVar = settings.routingVars.speedVariance;
				double sOff = tVar * rdn.nextDouble();
				double sMult = sOff + 1 - sVar / 2;
				trip.scaleTime(sMult);
				trip.offsetTime(tOff);
				trips.add(trip);
			}
		}
	}

	public static void main(String[] args) {
		LiquidMap lm = new LiquidMap();
	}
}
