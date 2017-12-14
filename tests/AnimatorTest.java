import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * Tests the methods of the Animator class by generating a random list of Trips.
 * The assertion is somewhat trivial, because all it does is check to ensure the output file exists.
 * However, the output file can then be checked manually to verify that the animation behaves as expected.
 */
public class AnimatorTest {

	@Test
	public void testAnimateTrips() {
		Random random = new Random();
		Animator animator = new Animator();
		ArrayList<Trip> listTrips = new ArrayList<>();
		
		int intNumberOfTrips = 1000;
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
			Trip trip = new Trip();
			int intNumberOfPoints = intMinPointsPerTrip + random.nextInt(intMaxPointsPerTrip - intMinPointsPerTrip) + 1;
			double dblTime = 0;
			
			for (int j = 0; j < intNumberOfPoints; j++) {
				double dblLat = Math.round(random.nextDouble() * (dblMaxLat - dblMinLat) + dblMinLat);
				double dblLon = Math.round(random.nextDouble() * (dblMaxLon - dblMinLon) + dblMinLon);
				
				trip.addPoint(new PointScreen(dblLat, dblLon, dblTime));
				
				dblTime += Math.round(random.nextDouble() * (dblMaxDurationOfTripLeg - dblMinDurationOfTripLeg) + (dblMinDurationOfTripLeg));
			}
			
			listTrips.add(trip);
		}
		
		animator.animateTrips(listTrips, "animation_test_1", intCanvasSize, "#000000", 1, 200, "#AAFF88", "#FFFFFF", "#FFFFFF", 0.05);
		animator.animateTrips(listTrips, "animation_test_2", intCanvasSize, "#000000", 1, 200, "#FFFFFF", "#FFFFFF", "#FFFFFF", 0.05);
		
		File file1 = new File("animation_test_1.html");
		File file2 = new File("animation_test_2.html");
		Assert.assertTrue(file1.exists());
		Assert.assertTrue(file2.exists());
	}
}
