package us.ignitiongaming.util.convert;

public class BooleanConverter {

	public static boolean getBooleanFromInteger(int number) {
		return number == 1;
	}
	
	public static int getIntegerFromBoolean(boolean condition) {
		return (condition ? 1 : 0);
	}
}
