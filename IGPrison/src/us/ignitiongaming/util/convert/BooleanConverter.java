package us.ignitiongaming.util.convert;

public class BooleanConverter {

	public static boolean getBooleanFromInteger(int number) {
		return number == 1;
	}
	
	public static int getIntegerFromBoolean(boolean condition) {
		return (condition ? 1 : 0);
	}
	
	public static String getYesNoFromBoolean(boolean condition) {
		return condition ? "Yes" : "No";
	}
	
	public static String getYesNoFromBooleanWithColor(boolean condition) {
		return condition ? "§2Yes" : "§4No";
	}
	
	public static boolean toBoolean(int id) {
		return getBooleanFromInteger(id);
	}
	
	public static int toInteger(boolean condition) {
		return getIntegerFromBoolean(condition);
	}
}
