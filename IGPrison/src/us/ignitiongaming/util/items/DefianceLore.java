package us.ignitiongaming.util.items;

import java.util.ArrayList;
import java.util.List;

import us.ignitiongaming.config.GlobalTags;

public class DefianceLore {

	public static List<String> getGeneralLore() {
		List<String> lore = new ArrayList<>();
		lore.add(GlobalTags.DEFIANCE);
		return lore;
	}
}
