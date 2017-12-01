import java.util.ArrayList;
import java.util.Locale;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.PathWrapper;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.Instruction;
import com.graphhopper.util.InstructionList;
import com.graphhopper.util.PointList;

public class GHInterface {

	GraphHopper hopper;

	/**
	 * Initializes the GraphHopper server using the map specified in the City
	 * object.<br>
	 * <br>
	 *
	 * The first time this is done on the local computer, it will initialize the
	 * map into the GHHopperWD working directory.
	 *
	 * @param city
	 */
	public GHInterface(String cityMapFile) {
		hopper = new GraphHopper().forDesktop();
		hopper.setOSMFile(cityMapFile);
		hopper.setGraphHopperLocation("graphhopperWD\\");
		hopper.setEncodingManager(new EncodingManager("car"));
		hopper.importOrLoad();
	}

	/**
	 * Uses the GHInterface object to compute a trip between Point p1 and Point
	 * p2.<br>
	 * <br>
	 *
	 * While the Point objects support time, this is not used in the
	 * calculation. The returned trip will start at 0(s) and progress so that
	 * the last point (ie the destination) will be at the concluding time of the
	 * trip. All points in between are sequential, monotonic in time, and
	 * reflect the local velocities.
	 *
	 * @param p1
	 *            The starting point
	 * @param p2
	 *            The destination point
	 * @return A trip.
	 */
	public Trip getTrip(Point p1, Point p2) {

		GHRequest req = new GHRequest(p1.getLat(), p1.getLon(), p2.getLat(),
				p2.getLon()).setWeighting("fastest").setVehicle("car")
						.setLocale(Locale.US);
		GHResponse rsp = hopper.route(req);
		if (rsp.hasErrors()) {
			return new Trip(Trip.typeSpace.WORLD); // returns null trip.
		}
		PathWrapper path = rsp.getBest();
		InstructionList il = path.getInstructions();

		return buildTripFromInst(il);
	}

	/**
	 * Converts an InstructionList into an ArrayList of Points.<br>
	 * <br>
	 *
	 * The GraphHopper PathWrapper contains a lot of information, including all
	 * lat-long pairs on the route. However the time data is not reported at
	 * this granularity.<br>
	 * <br>
	 *
	 * In order to find the time of each spatial point, we must mine the
	 * InstructionList for the information. The InstructionList provides a total
	 * time for each step of the journey and some number of spatial points that
	 * describe that step. The last step is actually the first point of the next
	 * step.<br>
	 * <br>
	 *
	 * We can then imagine each step in the InstructionList consisting of a
	 * series of microsteps as reported by that Instructions points. We make two
	 * assumptions: (1) the velocity is constant across all microsteps in a step
	 * and (2) the linear distances between the points inform how much time
	 * should occur on each microstep. We can then compute the time for each
	 * microstep. The time for each point will be the accumulation of these
	 * times. <br>
	 * <br>
	 *
	 * @param instList
	 *            InstructionList object to be processed.
	 * @return A Trip containing points with lat, long, and time.
	 */

	// TODO this method is massive. It internally shares a lot of data though so
	// I don't think it can be easily broken into steps without creating lots of
	// awkward objects that are used just once. One solution is to
	// create a library of mathematical list operations that encapsulate the
	// microroutines found here.
	private Trip buildTripFromInst(InstructionList instList) {
		boolean DEBUG = false;
		if (DEBUG) {
			System.out.println("   ==== building trip ====");
		}
		Trip trip = new Trip(Trip.typeSpace.WORLD);
		ArrayList<Double> legTimes = new ArrayList<>();
		ArrayList<Integer> pointCounts = new ArrayList<>();

		int nLegs = instList.getSize() - 1; // last leg doesn't count as it is
											// only the termination point.

		if (nLegs == 0) {
			// occurs if start and end points are effectively the same.
			return trip;
		}

		// iterate over all instructions and collect leg times, a running
		// list of points in all legs, and the number of points in that leg.
		for (int i = 0; i < nLegs; i++) {
			Instruction inst = instList.get(i);
			PointList pList = inst.getPoints();
			double legTime = inst.getTime() / 1000.;
			int nPoints = pList.getSize();
			legTimes.add(legTime);
			pointCounts.add(nPoints);
			for (int j = 0; j < nPoints; j++) {
				double lat = pList.getLat(j);
				double lon = pList.getLon(j);
				Point p = new Point(lat, lon);
				trip.addPoint(p);
			}
		}

		{// Add final point from terminating instruction to the trip
			Instruction inst = instList.get(nLegs);
			PointList pList = inst.getPoints();
			double lat = pList.getLat(0);
			double lon = pList.getLon(0);
			Point p = new Point(lat, lon);
			trip.addPoint(p);
		}
		if (DEBUG) {
			System.out
					.println("legTimes[" + legTimes.size() + "]: " + legTimes);
			System.out.println(
					"pointCounts[" + pointCounts.size() + "]: " + pointCounts);
			System.out
					.println("trip[" + trip.getPoints().size() + "]: " + trip);
		}
		int nPoints = trip.getPoints().size();

		// Compute the distance between every pair of points.
		ArrayList<Double> dists = new ArrayList<>();
		for (int i = 1; i < trip.getPoints().size(); i++) {
			Point pA = trip.getPoints().get(i - 1);
			Point pB = trip.getPoints().get(i);
			double dist = pA.distanceTo(pB, Trip.typeSpace.WORLD);
			dists.add(dist);
		}
		if (DEBUG) {
			System.out.println("dists[" + dists.size() + "]: " + dists);
		}
		// Compute the indices that correspond to each leg in the point list.
		ArrayList<Integer> breakPoints = new ArrayList<>();
		int posIndex = 0;
		breakPoints.add(posIndex);
		for (int i = 0; i < nLegs; i++) {
			posIndex += pointCounts.get(i);
			breakPoints.add(posIndex);
		}

		// include termination point into the last leg.
		// breakPoints.set(nLegs, breakPoints.get(nLegs) + 1);
		if (DEBUG) {
			System.out.println(
					"breakPoints[" + breakPoints.size() + "]: " + breakPoints);
		}
		// Find the total distance for each leg.
		ArrayList<Double> legDists = new ArrayList<>();
		for (int i = 0; i < nLegs; i++) {
			// System.out.print(i + " ");
			int start = breakPoints.get(i);
			int end = breakPoints.get(i + 1);
			double legDist = 0.;
			for (int j = start; j < end; j++) {
				legDist += dists.get(j);
			}
			legDists.add(legDist);
			// System.out.println(start + "-" + end + ": " + legDist);
		}
		if (DEBUG) {
			System.out
					.println("legDists[" + legDists.size() + "]: " + legDists);
		}
		// compute the time for each point based on the current time and
		ArrayList<Double> times = new ArrayList<>();
		double time = 0.;
		times.add(time);
		for (int i = 0; i < nLegs; i++) {
			int start = breakPoints.get(i);
			int end = breakPoints.get(i + 1);
			for (int j = start; j < end; j++) {
				double vel = legTimes.get(i) / legDists.get(i);
				double dt = dists.get(j) * vel;
				time += dt;
				times.add(time);
			}
		}
		if (DEBUG) {
			System.out.println("times[" + times.size() + "]: " + times);
		}
		for (int i = 0; i < times.size(); i++) {
			trip.getPoints().get(i).setTime(times.get(i));
		}

		return trip;

	}
}
