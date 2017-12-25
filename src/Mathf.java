/**
 * Contains static methods for performing basic mathematical operations.
 * @author Matt Surka
 */
public class Mathf {
	/**
	 * Takes a ratio (0-1) and converts it to a percentage expressed as a double (0-100);
	 * Rounds the double to a provided number of decimal places.
	 * Clamps the percentage between 0 and 100 inclusive.
	 * @param dblNum The ratio (0-1) to convert.
	 * @param intNumberOfDecimalPlaces The number of decimal places to round to.
	 * @return The ratio expressed as a percentage (0-100)
	 */
	public static double computePercentage (double dblNum, int intNumberOfDecimalPlaces) {
		dblNum *= 100;
		dblNum = roundToDouble(dblNum, intNumberOfDecimalPlaces);
		dblNum = clampDouble(dblNum, 0, 100);
		
		return dblNum;
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
	
	/**
	 * Clamps a double between two provided values.
	 * @param dblNum The double to clamp.
	 * @param dblMin The minimum value to clamp to.
	 * @param dblMax The maximum value to clamp to.
	 * @return The double clamped between the provided values.
	 */
	public static double clampDouble (double dblNum, double dblMin, double dblMax) {
		if (dblNum < dblMin) {
			dblNum = dblMin;
		} else if (dblNum > dblMax) {
			dblNum = dblMax;
		}
		
		return dblNum;
	}
	
	/**
	 * Rounds a double to a provided number of decimal places.
	 * @param dblNum The double to round.
	 * @param intNumberOfDecimalPlaces The number of decimal places to round to.
	 * @return The rounded double.
	 */
	public static double roundToDouble (double dblNum, int intNumberOfDecimalPlaces) {
		dblNum = dblNum * Math.pow(10, intNumberOfDecimalPlaces);
		dblNum = Math.round(dblNum);
		dblNum = dblNum / Math.pow(10, intNumberOfDecimalPlaces);
		return dblNum;
	}
}
