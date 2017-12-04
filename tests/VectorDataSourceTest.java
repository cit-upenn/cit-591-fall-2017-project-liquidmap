import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class VectorDataSourceTest {
	private ArrayList<PointWorld> points;
	private VectorDataSource vds;
	
	@Before
	public void setUp() throws Exception {
		this.vds = new VectorDataSource("VDSTest.csv");
		this.points = vds.getPoints();
	}

	@Test
	public void testPointLat() {
		assertEquals("The first index should have a latitude of 42.1234567",
				42.1234567, points.get(0).getLat());
	}

}
