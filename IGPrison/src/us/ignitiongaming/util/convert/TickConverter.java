package us.ignitiongaming.util.convert;

public class TickConverter {

	public static int getTicksInSeconds(int seconds) {
		return seconds * 20;
	}
	
	public static int getTicksInMinutes(int minutes) {
		return (minutes * 60) * 20;
	}
}
