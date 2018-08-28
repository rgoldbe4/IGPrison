package us.ignitiongaming.enums;

import us.ignitiongaming.util.convert.ChatConverter;

public enum IGMenus {
	BUY_COMMANDS ("§d§lBUY IN-GAME COMMANDS"),
	DEFIANCE_POINTS ("§d§lPURCHASE DEFIANCE POINTS");
	
	private String name;
	
	private IGMenus(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getStrippedName() {
		return ChatConverter.stripColor(getName());
	}
}
