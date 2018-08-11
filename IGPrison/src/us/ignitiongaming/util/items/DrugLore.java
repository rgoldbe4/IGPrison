package us.ignitiongaming.util.items;

import java.util.ArrayList;
import java.util.List;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.enums.IGDrugType;

public class DrugLore {

	private static List<String> cactusLore = new ArrayList<>();
	private static List<String> warriorLore = new ArrayList<>();

	public static List<String> getAutoDropLore() {
		cactusLore.add(GlobalTags.DRUGS + IGDrugType.AUTO_DROP.getTitle());
		cactusLore.add("§lDuration:§r 10 seconds");
		cactusLore.add("§lAction:§r Break blocks with a punch.");
		return cactusLore;
	}
	public static List<String> getWarriorLore() {
		warriorLore.add(GlobalTags.DRUGS + IGDrugType.WARRIOR.getTitle());
		warriorLore.add("§lDuration:§r 10 seconds");
		warriorLore.add("§lAction:§r Gain fighting effects.");
		return warriorLore;
	}
}
