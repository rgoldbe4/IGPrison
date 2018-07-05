package us.ignitiongaming.enums;

import net.md_5.bungee.api.ChatColor;

public enum IGGuardArmor {

	HELMET ("Helmet"), CHESTPLATE ("Chestplate"), LEGGINGS ("Leggings"), BOOTS ("Boots");
	
	private String label;
	
	private IGGuardArmor(String label) { this.label = label; }
	
	public String getLabel() { return label; }
	
	public String getStripped() { return ChatColor.stripColor(label); }
}
