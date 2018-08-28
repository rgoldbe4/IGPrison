package us.ignitiongaming.enums;

import net.md_5.bungee.api.ChatColor;

public enum IGGuardArmor {

	HELMET ("§6§lGuard Helmet"), CHESTPLATE ("§6§lGuard Chestplate"), LEGGINGS ("§6§lGuard Leggings"), BOOTS ("§6§lGuard Boots");
	
	private String label;
	
	private IGGuardArmor(String label) { this.label = label; }
	
	public String getLabel() { return label; }
	
	public String getStripped() { return ChatColor.stripColor(label); }
}
