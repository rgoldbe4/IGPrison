package us.ignitiongaming.enums;

import us.ignitiongaming.entity.other.IGSetting;
import us.ignitiongaming.event.other.IGSettingFactory;

public enum IGSettings {
	RANKUP_D ("rankup_d"), RANKUP_C ("rankup_c"), RANKUP_B ("rankup_b"), RANKUP_A ("rankup_a"), DEFAULT_WORLD_NAME ("default_world_name"),
	DEFAULT_DONATOR_POINTS ("default_donator_points"), DEFAULT_MOTD ("default_motd");
	
	private String label;
	
	private IGSettings (String label) { this.label = label; }
	
	public String getLabel() { return label; }
	
	public IGSetting toSetting() {
		//Get all settings.
		for (IGSetting setting : IGSettingFactory.getSettings()) {
			if (setting.getLabel().equalsIgnoreCase(label)) return setting;
		}		
		return null;
	}
	
}
