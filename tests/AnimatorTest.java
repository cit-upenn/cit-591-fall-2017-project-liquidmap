import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * Tests the methods of the Animator class by generating a random list of Trips.
 * The assertion is somewhat trivial, because all it does is check to ensure the output files exist.
 * However, the output files can then be checked manually to verify that the animation behaves as expected.
 */
public class AnimatorTest {

	@Test
	public void testAnimateTrips() {
		Random random = new Random();
		Animator animator = new Animator();
		ArrayList<Trip> listTrips = new ArrayList<>();
		
		int intNumberOfTrips = 2;
		int intMinPointsPerTrip = 2;
		int intMaxPointsPerTrip = 3;
		double dblMinDurationOfTripLeg = 100;
		double dblMaxDurationOfTripLeg = 1000;
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
		
		animator.animateTrips(listTrips, "animation_test_1", "LiquidMaps: Test 1", "", intCanvasSize, "#000000", 2, 300, false, "#224488", "#88FF00", "#FFFFFF", 0, 2);
		animator.animateTrips(listTrips, "animation_test_2", "LiquidMaps: Test 2", "Refresh page to replay.", intCanvasSize, "#000000", 4, 500, true, "#FFFFFF", "#FFFFFF", "#FFFFFF", 0.05, 2);
		animator.animateTrips(listTrips, "animation_test_3", "LiquidMaps: Test 3", "Color verification failed. Reverted to default colors.", intCanvasSize, "#00000", 4, 500, true, "#FFGSFF", "FFFFFF", "red", 0, 2);
		
		File file1 = new File("animation_test_1.html");
		File file2 = new File("animation_test_2.html");
		File file3 = new File("animation_test_3.html");
		Assert.assertTrue(file1.exists());
		Assert.assertTrue(file2.exists());
		Assert.assertTrue(file3.exists());
	}
}
