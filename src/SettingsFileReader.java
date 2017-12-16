import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Reads the JSON file (settings.json) and initializes the settings variables.
 * @author brian
 */
public class SettingsFileReader {
	private Settings settings;

	/**
	 * Constructor. Reads the JSON file (settings.json) and initializes the settings variables.
	 */
	public SettingsFileReader() {
		try (Reader reader = new InputStreamReader(
				SettingsFileReader.class.getResourceAsStream("/settings.json"),
				"UTF-8")) {
			Gson gson = new GsonBuilder().create();
			settings = gson.fromJson(reader, Settings.class);
			System.out.println(settings);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns a Settings object containing the settings from settings.json.
	 * @return A Settings object containing the settings from settings.json.
	 */
	public Settings getSettings() {
		return settings;
	}
}
