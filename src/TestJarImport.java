import java.util.Locale;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.PathWrapper;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.Instruction;
import com.graphhopper.util.InstructionList;
import com.graphhopper.util.PointList;

public class TestJarImport {

	public static void main(String[] args) {
		// create one GraphHopper instance
		GraphHopper hopper = new GraphHopper().forDesktop();
		hopper.setOSMFile("philadelphia.osm.pbf");
		// where to store graphhopper files?
		hopper.setGraphHopperLocation("graphhopperWD\\");
		hopper.setEncodingManager(new EncodingManager("car"));

		// now this can take minutes if it imports or a few seconds for loading
		// of course this is dependent on the area you import
		hopper.importOrLoad();

		// simple configuration of the request object, see the
		// GraphHopperServlet classs for more possibilities.
		// 39.9505464,-75.1913818
		//
		GHRequest req = new GHRequest(39.9505464, -75.1913818, 39.94673,
				-75.1457843).setWeighting("fastest").setVehicle("car")
						.setLocale(Locale.US);
		GHResponse rsp = hopper.route(req);

		// first check for errors
		if (rsp.hasErrors()) {
			// handle them!
			// rsp.getErrors()
			return;
		}

		// use the best path, see the GHResponse class for more possibilities.
		PathWrapper path = rsp.getBest();

		// points, distance in meters and time in millis of the full path
		PointList pointList = path.getPoints();
		System.out.println("getPoints:");
		System.out.println(pointList);
		double distance = path.getDistance();
		System.out.println("getDistance:");
		System.out.println(distance);
		long timeInMs = path.getTime();
		System.out.println("getTime:");
		System.out.println(distance);
		System.out.println(timeInMs);

		System.out.println("\n");
		System.out.println("INSTRUCTION LIST");
		InstructionList il = path.getInstructions();
		// iterate over every turn instruction
		for (Instruction instruction : il) {
			System.out.println(instruction.getName());
			System.out.println("time = " + instruction.getTime());
			System.out.println("points = " + instruction.getPoints());
		}
	}

}
