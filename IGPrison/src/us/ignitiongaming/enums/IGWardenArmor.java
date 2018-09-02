package us.ignitiongaming.enums;

import net.md_5.bungee.api.ChatColor;

public enum IGWardenArmor {
	HELMET ("§4§lWarden Helmet"), CHESTPLATE ("§4§lWarden Chestplate"), LEGGINGS ("§4§lWarden Leggings"), BOOTS ("§4§lWarden Boots");
	
	private String label;
	
	private IGWardenArmor(String label) { this.label = label; }
	
	public String getLabel() { return label; }
	
	public String getStripped() { return ChatColor.stripColor(label); }
}
