import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LiquidMapTester {
	private LiquidMap lm;
	
	@Before
	public void setUp() {
		lm = new LiquidMap();
	}

	@Test
	public void test() {
		assertNotNull(lm);
	}

}
