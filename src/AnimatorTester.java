import java.util.ArrayList;
import java.util.Random;

/**
 * Tests the methods of the Animator class by generating a random list of Trips.
 * This class will not be in the final build.
 * @author Matt Surka
 */
public class AnimatorTester {
	/**
	 * Tests the methods of the Animator class by generating a random list of Trips.
	 * This method will not be in the final build.
	 * @param args
	 */
	public static void main(String[] args) {
		Random random = new Random();
		Animator animator = new Animator();
		ArrayList<Trip> listTrips = new ArrayList<Trip>();
		
		int intNumberOfTrips = 400;
		int intMinPointsPerTrip = 3;
		int intMaxPointsPerTrip = 6;
		double dblMinDurationOfTripLeg = 1;
		double dblMaxDurationOfTripLeg = 10;
		int intCanvasSize = 600;
		int intSpawnPadding = 10;
		double dblMinLat = intSpawnPadding;
		double dblMaxLat = intCanvasSize - intSpawnPadding;
		double dblMinLon = intSpawnPadding;
		double dblMaxLon = intCanvasSize - intSpawnPadding;
		
		for (int i = 0; i < intNumberOfTrips; i++) {
			Trip trip = new Trip(Trip.typeSpace.SCREEN);
			int intNumberOfPoints = intMinPointsPerTrip + random.nextInt(intMaxPointsPerTrip - intMinPointsPerTrip) + 1;
			double dblTime = 0;
			
			for (int j = 0; j < intNumberOfPoints; j++) {
				double dblLat = Math.round(random.nextDouble() * (dblMaxLat - dblMinLat) + dblMinLat);
				double dblLon = Math.round(random.nextDouble() * (dblMaxLon - dblMinLon) + dblMinLon);
				
				trip.addPoint(new Point(dblLat, dblLon, dblTime));
				
				dblTime += Math.round(random.nextDouble() * (dblMaxDurationOfTripLeg - dblMinDurationOfTripLeg) + (dblMinDurationOfTripLeg));
			}
			
			listTrips.add(trip);
		}
		
		animator.animateTrips(listTrips, intCanvasSize, "#000000", 1, 200, "#FFFFFF", "#FFFFFF", 0.05);
	}

}
