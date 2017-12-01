/**
 * Contains static methods for performing basic mathematical operations.
 * @author Matt Surka
 */
public class Mathf {
	/**
	 * Takes a ratio (0-1) and converts it to a percentage expressed as an integer (0-100);
	 * Clamps the percentage between 0 and 100 inclusive.
	 * @param dblNum The ratio (0-1) to convert.
	 * @return The ratio expressed as an integer percentage (0-100)
	 */
	public static int computePercentage (double dblNum) {
		dblNum *= 100;
		int intNum = roundToInt(dblNum);
		clampInt(intNum, 0, 100);
		
		return intNum;
	}
	
	/**
	 * Rounds a double to the nearest integer.
	 * @param dblNum The double to convert.
	 * @return The double rounded to the nearest integer.
	 */
	public static int roundToInt (double dblNum) {
		dblNum = Math.round(dblNum);
		return (int)dblNum;
	}
	
	/**
	 * Clamps an integer between two provided values.
	 * @param intNum The integer to clamp.
	 * @param intMin The minimum value to clamp to.
	 * @param intMax The maximum value to clamp to.
	 * @return The integer clamped between the provided values.
	 */
	public static int clampInt (int intNum, int intMin, int intMax) {
		if (intNum < intMin) {
			intNum = intMin;
		} else if (intNum > intMax) {
			intNum = intMax;
		}
		
		return intNum;
	}
}
