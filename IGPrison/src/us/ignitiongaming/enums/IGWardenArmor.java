package us.ignitiongaming.enums;

import net.md_5.bungee.api.ChatColor;

public enum IGWardenArmor {
	HELMET ("Helmet"), CHESTPLATE ("Chestplate"), LEGGINGS ("Leggings"), BOOTS ("Boots");
	
	private String label;
	
	private IGWardenArmor(String label) { this.label = label; }
	
	public String getLabel() { return label; }
	
	public String getStripped() { return ChatColor.stripColor(label); }
}
