import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class VectorDataSourceTest {
	private ArrayList<PointWorld> points;
	private VectorDataSource vds;
	
	@Before
	public void setUp() throws Exception {
		this.vds = new VectorDataSource("VDSTest.txt");
		this.points = vds.getPoints();
		System.out.println(points.size());
	}

	@Test
	public void testPointLat() {
		assertEquals("The first index should have a latitude of 39.949741",
				39.949741, points.get(0).getLat(), 0.00001);
	}
	@Test
	public void testPointLon() {
		assertEquals("The first index should have a longitude of -75.180969",
				-75.180969, points.get(0).getLon(), 0.00001);
	}
	@Test
	public void testPointWeight() {
		assertEquals("The tenth entry should have a weight of 0.01",
				0.01, points.get(9).getWeight(), 0.00001);
	}
	@Test
	public void testGetRandPoint() {
		PointWorld randPoint = vds.getRandPoint();
		System.out.println("randPoint Lat: " + randPoint.getLat());
		System.out.println("randPoint Long: " + randPoint.getLon());
		System.out.println("randPoint Weight: " + randPoint.getWeight());
		assertNotNull(randPoint);
	}

}
