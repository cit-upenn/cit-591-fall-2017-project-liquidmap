import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SettingsFileReader {

	private Settings settings;

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
	 * This will return the settings to the user
	 * @return settings from settings.json file
	 */
	public Settings getSettings() {
		return settings;
	}
}
