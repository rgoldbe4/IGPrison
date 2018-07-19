package us.ignitiongaming.util.items;

import java.util.ArrayList;
import java.util.List;

import us.ignitiongaming.config.GlobalTags;

public class DrugLore {

	private static List<String> cactusLore = new ArrayList<>();

	public static List<String> getAutoDropLore() {
		cactusLore.add(GlobalTags.DRUGS + "One Punch Man");
		cactusLore.add("For 5 seconds, you can break blocks with one punch!");
		return cactusLore;
	}
}
