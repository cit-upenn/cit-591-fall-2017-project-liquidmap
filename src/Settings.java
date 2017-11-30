import java.util.ArrayList;

/**
 * Settings and the inner classes it contains are data structures to be filled
 * by the GSON library.
 *
 * @author brian
 *
 */
public class Settings {

	public final String cityMapFile = null;
	public final ArrayList<RasterDataDesc> rasterDataDescs = new ArrayList<RasterDataDesc>();
	public final ArrayList<VectorDataDesc> vectorDataDescs = new ArrayList<VectorDataDesc>();
	public final RoutingVars routingVars = new RoutingVars();

	Settings() {
	}

	public class RasterDataDesc {

		public final String name = null;
		public final String mapFileName = null;
		public final Point point1 = null;
		public final Pixel pixel1 = null;
		public final Point point2 = null;
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

	public class RoutingVars {

		public final String routeBeg = null;
		public final String routeEnd = null;
		public final Integer routeCount = null;
		public final Double routeMaxTime = null;
		public final Double timeStartVariance = null;
		public final Double speedVariance = null;

		public RoutingVars() {
		};

		@Override
		public String toString() {
			String str = "Data will consist of " + routeCount + " trips from '"
					+ routeBeg + "' to '" + routeEnd
					+ "' that take no more that " + routeMaxTime
					+ " seconds with start times distrubited over "
					+ timeStartVariance + " seconds with speeds varying over "
					+ (speedVariance * 100) + "%";
			return str;
		}

	}

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
		str += routingVars;
		return str;
	}
}
