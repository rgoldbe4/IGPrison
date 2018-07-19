package us.ignitiongaming.util.convert;

import org.bukkit.entity.Player;

public class ChatConverter {

	public static String convertToColor(String message) {
		return message.replace("&", "§");
	}
	
	public static String removeSpecialCharacters(String message) {
		return message.replaceAll("[^\\w\\s]","");
	}
	
	public static void clearPlayerChat(Player player) {
		for (int i = 0; i < 200; i++)
			player.sendMessage("");
	}

}
