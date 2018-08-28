package us.ignitiongaming.enums;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.util.convert.ChatConverter;

public enum IGMenus {
	BUY_COMMANDS ("§b§lBUY IN-GAME COMMANDS"),
	DEFIANCE_POINTS ("§d§lBUY DEFIANCE POINTS"),
	GENERAL_MENU (GlobalTags.LOGO + "§8§lMAIN MENU");
	
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
