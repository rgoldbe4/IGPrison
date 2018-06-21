package us.ignitiongaming.database;

import java.sql.ResultSet;
import java.util.HashMap;


public class ConvertUtils {
	/**
	 * Convert an unfriendly database datetime format into a friendly format!
	 * @param dateTime (EXAMPLE -> 2017-01-21 23:03:18)
	 * @return FRIENDLY -> 01-21-2017 at <time>
	 */
	public static String toFriendlyDate(String dateTime) {
		//EXAMPLE -> 2017-01-21 23:03:18
		String date = dateTime.split(" ")[0];
		String time = dateTime.split(" ")[1];
		//FRIENDLY -> 01-21-2017 at <time>
		date = date.split("-")[1] + "/" + date.split("-")[2] + "/" + date.split("-")[0];
		int hour = Integer.parseInt(time.split(":")[0]);
		boolean isAfternoon = false;
		
		if (hour > 12) {
			hour = hour - 12;
			isAfternoon = true;
		}
		time =  hour + ":" + time.split(":")[1] + ":" + time.split(":")[2];
		return date + " at " + time + " " + (isAfternoon ? "PM" : "AM");
	}
	/**
	 * Convert a boolean to integer for database reasons.
	 * @param value
	 * @return
	 */
	public static int boolToInt(boolean value) {
		return (value ? 1 : 0);
	}
	/**
	 * Convert an integer value to boolean to retrieve from database.
	 * @param value
	 * @return
	 */
	public static boolean intToBool(int value) {
		return (value == 0 ? false : true);
	}
	
	public static String getStringFromCommand(int start, String[] args) {
		String text = "";
		for (int i = start; i < args.length; i++) {
			if (isLastIndex(i, args.length)) {
				text += args[i];
			} else
			text += args[i] + " ";
		}
		return text;
	}
	
	public static boolean isLastIndex(int index, int last) {
		return ((index + 1) == last);
	}
	
	public static String toString(Object object) {
		return object + "";
	}
	
	public static String[] toStringArray(String ... strings) {
		String[] array = new String[strings.length];
		for (int i = 0; i < strings.length; i++) {
			array[i] = strings[i];
		}
		return array;
	}
	/**
	 * <h3>ResultSet to HashMap</h3>
	 * Note: This only returns the first row in the set.
	 * @param columns
	 * @param set
	 * @return
	 */
	public static HashMap<String,Object> resultSetToHashMap(String[] columns, ResultSet set) {
		try {
			HashMap<String, Object> results = new HashMap<>();
			for (int i = 0; i < columns.length; i++) {
				results.put(columns[i], set.getObject(columns[i]));
			}
			return results;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
