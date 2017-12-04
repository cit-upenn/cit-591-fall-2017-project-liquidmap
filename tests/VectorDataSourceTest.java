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

}
