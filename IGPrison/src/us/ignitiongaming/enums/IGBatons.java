package us.ignitiongaming.enums;

import org.bukkit.ChatColor;

public enum IGBatons {
	SHOCK_BATON("§6§lSHOCK BATON"), GUARD_BATON("§2GUARD BATON"), SOLITARY_STICK("§8§lSOLITARY STICK");
	
	private String tag;
	
	private IGBatons(String tag) { this.tag = tag; }
	
	public String getTag() { return tag; }
	
	public String getStripped() { return ChatColor.stripColor(tag); }
}
