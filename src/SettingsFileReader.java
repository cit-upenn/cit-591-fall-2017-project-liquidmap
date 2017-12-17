import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

/**
 * Reads the JSON file (settings.json) and initializes the settings variables.
 *
 * @author brian
 */
public class SettingsFileReader {
	private Settings settings;

	/**
	 * Constructor. Reads the JSON file (settings.json) and initializes the
	 * settings variables.
	 */
	public SettingsFileReader() throws Exception {
		try (Reader reader = new InputStreamReader(
				SettingsFileReader.class.getResourceAsStream("/settings.json"),
				"UTF-8")) {
			Gson gson = new GsonBuilder().create();
			settings = gson.fromJson(reader, Settings.class);
			System.out.println(settings);

		} catch (UnsupportedEncodingException e) {
			throw new Exception("The Character Encoding of 'settings.json'"
					+ "is not supported.");
		} catch (IOException e) {
			throw new Exception(
					"Something has gone terribly wrong while reading "
							+ "'settings.json'.  Please try again.");
		} catch (JsonParseException e) {
			throw new Exception(
					"It appears as if 'settings.json' is not properly "
							+ "formatted.  Please consult the manual for proper "
							+ "JSON syntax.");
		}

	}

	/**
	 * Returns a Settings object containing the settings from settings.json.
	 *
	 * @return A Settings object containing the settings from settings.json.
	 */
	public Settings getSettings() {
		return settings;
	}
}
