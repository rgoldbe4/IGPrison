package us.ignitiongaming.util.items;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

public class GuardArmorLore {

	public static List<String> getHelmetLore() {
		List<String> helmetLore = new ArrayList<>();
		return helmetLore;
	}
	
	public static List<String> getStrippedLore(List<String> lore) {
		List<String> strippedLore = new ArrayList<>();
		for (String line : lore) {
			strippedLore.add(ChatColor.stripColor(line));
		}
		return strippedLore;
	}
	
	public static List<String> getChestplateLore() {
		List<String> chestplateLore = new ArrayList<>();
		return chestplateLore;
	}
	
	public static List<String> getLeggingLore() {
		List<String> leggingLore = new ArrayList<>();
		return leggingLore;
	}
	
	public static List<String> getBootsLore() {
		List<String> bootLore = new ArrayList<>();
		return bootLore;
	}
}
