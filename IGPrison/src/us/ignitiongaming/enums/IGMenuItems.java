package us.ignitiongaming.enums;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.entity.menu.IGMenuItem;
import us.ignitiongaming.factory.menu.IGMenuItemFactory;
import us.ignitiongaming.util.convert.ChatConverter;

public enum IGMenuItems {
	CLOSE (-1, "close", "§4Close Menu", ""),
	PURCHASED (0, "purchased", "§a§lPURCHASED!", ""),
	FIX (1, "fix", "§b§l/fix", "essentials.repair.all"),
	WORKBENCH (2, "workbench", "§e§l/workbench", "essentials.workbench"),
	MSG (3, "msg", "§a§l/msg", "essentials.msg"),
	NICKNAME (4, "nickname", "§d§l/nickname", "igprison.nickname"),
	ENDERCHEST (5, "enderchest", "§6§l/enderchest", "essentials.enderchest");
	
	private String displayName, label, node;
	private int id = 0;
	
	private IGMenuItems(int id, String label, String displayName, String node) {
		this.id = id;
		this.displayName = displayName;
		this.label = label;
		this.node = node;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public int getId() { return id; }
	
	public String getLabel() { return label; }
	
	public List<String> getLore() {
		List<String> lore = new ArrayList<>();
		lore.add(GlobalTags.DEFIANCE + "§d" + toMenuItem().getPoints() + " Points");
		lore.add("§2§l-- Click To Buy --");
		return lore;
	}
	
	public String getNode() { return node; }
	
	public static IGMenuItems getMenuItem(ItemStack item) {
		for (IGMenuItems menuItem : IGMenuItems.values()) {
			if (ChatConverter.stripColor(menuItem.getDisplayName()).equalsIgnoreCase(ChatConverter.stripColor(item.getItemMeta().getDisplayName()))) 
				return menuItem;
		}
		return null;
	}
	
	public IGMenuItem toMenuItem() {
		return IGMenuItemFactory.getItemByEnum(this);
	}
}
