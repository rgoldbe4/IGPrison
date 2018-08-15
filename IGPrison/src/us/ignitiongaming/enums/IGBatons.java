package us.ignitiongaming.enums;

import org.bukkit.ChatColor;

public enum IGBatons {
	SHOCK_BATON("§6§lSHOCK BATON"), GUARD_BATON("§2§lGUARD BATON");
	
	private String tag;
	
	private IGBatons(String tag) { this.tag = tag; }
	
	public String getTag() { return tag; }
	
	public String getStripped() { return ChatColor.stripColor(tag); }
}
