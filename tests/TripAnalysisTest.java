import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class TripAnalysisTest {
	private TripAnalysis tripAnalysis;
	private LiquidMap lm;
	private ArrayList<Trip> trips;
	private double[] timeStats;
	private double[] distStats;
	
	@Before
	public void setUp() {
		lm = new LiquidMap();
		trips = lm.getTrips();
		tripAnalysis = new TripAnalysis(trips);
		timeStats = tripAnalysis.getTimeStats();
		distStats = tripAnalysis.getDistanceStats();
	}

	@Test
	public void testGetTripStats() {
		//make sure min isn't 0
		assertNotEquals(0, timeStats[0], 0.000001);
		//make sure mean isn't 0
		assertNotEquals(0, timeStats[1], 0.000001);
		//make sure SD isn't 0
		assertNotEquals(0, timeStats[2], 0.000001);
		//make sure max isn't 0
		assertNotEquals(0, timeStats[3], 0.000001);
	}

}
