import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * A versatile timer that can track the elapsed time of multiple operations concurrently.
 * Each element of time data is mapped to a key.
 * Starting and stopping the timer on a new key will create a new list of time data.
 * Starting and stopping the timer on an existing key will add more information to that list of time data.
 * @author Matt Surka
 *
 */
public class Timer {
	private HashMap<String, Long> mapLngTimeStart;
	private HashMap<String, ArrayList<Long>> mapListLngTimeElapsed;

	/**
	 * Constructor. Initializes the HashMaps used to track time data.
	 */
	public Timer () {
		mapLngTimeStart = new HashMap<>();
		mapListLngTimeElapsed = new HashMap<>();
	}

	/**
	 * Starts the timer mapped to the provided key.
	 * @param strKey The key to which this timer should be mapped (e.g., a description of what is being timed).
	 */
	public void startTimer (String strKey) {
		mapLngTimeStart.put(strKey, System.nanoTime());
	}

	/**
	 * Stores the time elapsed in the ArrayList mapped to the provided key.
	 * If the mapping doesn't exist, creates a new one.
	 * @param strKey The key to which this timer should be mapped (e.g., a description of what is being timed).
	 */
	public void stopTimer (String strKey) {
		if (mapLngTimeStart.containsKey(strKey)) {
			if (!mapListLngTimeElapsed.containsKey(strKey)) {
				mapListLngTimeElapsed.put(strKey, new ArrayList<Long>());
			}

			mapListLngTimeElapsed.get(strKey).add(System.nanoTime() - mapLngTimeStart.get(strKey));
		}
	}

	/**
	 * Returns a summary of all timers.
	 * Shows the best-case time, average time, and worst-case time for each timer.
	 * @return A summary of all timers.
	 */
	public String getSummaryAll () {
		String strSummary = "";

		for (String strKey : mapListLngTimeElapsed.keySet()) {
			strSummary += getSummaryOne(strKey) + System.getProperty("line.separator");
		}

		return strSummary;
	}

	/**
	 * Returns a summary of the ArrayList mapped to the provided key.
	 * Shows the best-case time, average time, and worst-case time.
	 * @param strKey The key mapped to the ArrayList that should be summarized.
	 * @return A summary of the ArrayList mapped to the provided key.
	 */
	public String getSummaryOne (String strKey) {
		if (mapListLngTimeElapsed.containsKey(strKey)) {
			String strSummary = strKey + ":" + System.getProperty("line.separator");
			
			strSummary += "Best case: " + formatTime(getMinTime(strKey)) + System.getProperty("line.separator");
			strSummary += "Average case: " + formatTime(getAverageTime(strKey)) + System.getProperty("line.separator");
			strSummary += "Worst case: " + formatTime(getMaxTime(strKey)) + System.getProperty("line.separator");
			
			return strSummary;
		} else {
			return "No data for key: " + strKey;
		}
	}

	/**
	 * Returns the average of the values of the ArrayList mapped to the provided key.
	 * @param strKey The key mapped to the ArrayList that should be summarized.
	 * @return The average of the values of the ArrayList mapped to the provided key.
	 */
	private long getAverageTime (String strKey) {
		if (mapListLngTimeElapsed.containsKey(strKey)) {
			long lngTimeTotal = 0L;
			
			for (long lngTime : mapListLngTimeElapsed.get(strKey)) {
				lngTimeTotal += lngTime;
			}

			return Math.round ((double) lngTimeTotal / mapListLngTimeElapsed.get(strKey).size());
		} else {
			return -1L;
		}
	}

	/**
	 * Returns the maximum of the values of the ArrayList mapped to the provided key.
	 * @param strKey The key mapped to the ArrayList that should be summarized.
	 * @return The maximum of the values of the ArrayList mapped to the provided key.
	 */
	private long getMinTime (String strKey) {
		if (mapListLngTimeElapsed.containsKey(strKey)) {
			return Collections.min(mapListLngTimeElapsed.get(strKey));
		} else {
			return -1L;
		}
	}

	/**
	 * Returns the minimum of the values of the ArrayList mapped to the provided key.
	 * @param strKey The key mapped to the ArrayList that should be summarized.
	 * @return The minimum of the values of the ArrayList mapped to the provided key.
	 */
	private long getMaxTime (String strKey) {
		if (mapListLngTimeElapsed.containsKey(strKey)) {
			return Collections.max(mapListLngTimeElapsed.get(strKey));
		} else {
			return -1L;
		}
	}

	/**
	 * Returns a time value in nanoseconds formatted as HH:MM:SS.
	 * @param lngTimeNano The nanoseconds value to format.
	 * @return The time value formatted as HH:MM:SS.
	 */
	private LocalTime formatTime (long lngTimeNano) {
		return LocalTime.ofNanoOfDay(lngTimeNano);
	}
}
