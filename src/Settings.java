import java.util.ArrayList;

/**
 * Stores the input and output settings for the LiquidMaps program.
 * The variables within inner classes are data structures to be filled by the GSON library using SettingsFileReader.
 * @author brian
 */
public class Settings {
	public final String cityMapFile = null;
	public final ArrayList<RasterDataDesc> rasterDataDescs = new ArrayList<RasterDataDesc>();
	public final ArrayList<VectorDataDesc> vectorDataDescs = new ArrayList<VectorDataDesc>();
	public final RoutingVars routingVars = new RoutingVars();
	public final OutputVars outputVars = new OutputVars();

	Settings() {
	}

	/**
	 * Inner class that stores raster data settings.
	 * @author brian
	 */
	public class RasterDataDesc {
		public final String name = null;
		public final String mapFileName = null;
		public final PointWorld point1 = null;
		public final Pixel pixel1 = null;
		public final PointWorld point2 = null;
		public final Pixel pixel2 = null;

		public RasterDataDesc() {
		};

		@Override
		public String toString() {
			String str = name + "  " + mapFileName + "     " + point1 + " = "
					+ pixel1 + "   " + point2 + " = " + pixel2;
			return str;
		}
	}

	/**
	 * Inner class that stores vector data settings.
	 * @author brian
	 */
	public class VectorDataDesc {
		public final String name = null;
		public final String vecFileName = null;

		public VectorDataDesc() {
		};

		@Override
		public String toString() {
			String str = name + "  " + vecFileName;
			return str;
		}

	}

	/**
	 * Inner class that stores routing settings.
	 * @author brian
	 */
	public class RoutingVars {
		public final String routeBeg = null;
		public final String routeEnd = null;
		public final Integer routeCount = null;
		public final Double routeMinTime = null;
		public final Double routeMaxTime = null;
		public final Double timeStartVariance = null;
		public final Double speedVariance = null;

		public RoutingVars() {
		};

		@Override
		public String toString() {
			String str = "Data will consist of " + routeCount + " trips from '"
					+ routeBeg + "' to '" + routeEnd + "' that take between "
					+ routeMinTime + " and " + routeMaxTime
					+ " seconds with start times distrubited over "
					+ timeStartVariance + " seconds with speeds varying over "
					+ (speedVariance * 100) + "%";
			return str;
		}

	}

	/**
	 * Inner class that stores output settings.
	 * @author brian
	 */
	public class OutputVars {
		public final PointWorld pointUpperLeft = null;
		public final PointWorld pointLowerRight = null;
		public final String strFileName = null;
		public final String strPageTitle = null;
		public final String strCanvasText = null;
		public final Integer intCanvasWidth = null;
		public final String strCanvasColor = null;
		public final Integer intLineWidth = null;
		public final Integer intLineLength = null;
		public final Boolean isKeepLinesVisible = null;
		public final String strLineColorA = null;
		public final String strLineColorB = null;
		public final String strTextColor = null;
		public final Double dblTimeBetweenSpawns = null;

		@Override
		public String toString() {
			String str = "  imageWidth: " + intCanvasWidth + "  pointUpperLeft:  "
					+ pointUpperLeft + "  pointLowerRight:  " + pointLowerRight;
			return str;
		}
	}

	/**
	 * Returns a description of the current LiquidMaps settings.
	 * @return A description of the current LiquidMaps settings.
	 */
	@Override
	public String toString() {
		String str = "cityMapFile: " + cityMapFile + "\n";
		str += "rasterDataDescs:\n";
		str += " [\n";
		for (RasterDataDesc desc : rasterDataDescs) {
			str += "   " + desc.toString() + "\n";
		}
		str += " ]\n";
		str += "vectorDataDescs:\n";
		str += " [\n";
		for (VectorDataDesc desc : vectorDataDescs) {
			str += "   " + desc.toString() + "\n";
		}
		str += " ]\n";
		str += "routingVars:\n" + routingVars + "\n";
		str += "outputVars:\n" + outputVars;

		return str;
	}
}
