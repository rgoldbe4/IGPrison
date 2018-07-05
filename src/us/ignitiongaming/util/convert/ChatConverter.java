package us.ignitiongaming.util.convert;

public class ChatConverter {

	public static String convertToColor(String message) {
		return message.replace("&", "§");
	}

}
