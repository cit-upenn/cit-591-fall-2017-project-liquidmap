import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MapBuilderTester {
	private MapBuilder mb;
	
	@Before
	public void setUp() {
		mb = new MapBuilder();
	}

	@Test
	public void test() {
		assertNotNull(mb);
	}

}
