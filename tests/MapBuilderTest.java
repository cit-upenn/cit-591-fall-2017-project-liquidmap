import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MapBuilderTest {
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
