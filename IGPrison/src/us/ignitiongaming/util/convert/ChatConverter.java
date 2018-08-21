package us.ignitiongaming.util.convert;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.google.common.base.CaseFormat;

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
	
	public static String stripColor(String message) {
		return ChatColor.stripColor(message);
	}
	
	public static String stripCurrency(String message) {
		return message.replace("$", "").replace(",", "");
	}
	
	public static String toCamelCase(String message) {
		return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, message);
	}
	
	

}
