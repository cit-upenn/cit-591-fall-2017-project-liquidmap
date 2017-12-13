import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class SettingsFileReaderTest {
	
	private SettingsFileReader sfr;
	private Settings settings;
	private ArrayList<Settings.RasterDataDesc> rdd;
	
	@Before
	public void setUp() throws Exception {
		sfr = new SettingsFileReader();
		settings = sfr.getSettings();
		rdd = settings.rasterDataDescs;
	}

	@Test
	public void test() {
		assertNotNull(rdd);
	}

}
