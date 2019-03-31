package us.ignitiongaming.enums;

import us.ignitiongaming.entity.other.IGSetting;
import us.ignitiongaming.factory.other.IGSettingFactory;

public enum IGSettings {
	RANKUP_D ("rankup_d"), RANKUP_C ("rankup_c"), RANKUP_B ("rankup_b"), RANKUP_A ("rankup_a"), DEFAULT_WORLD_NAME ("default_world_name"),
	DEFAULT_DONATOR_POINTS ("default_donator_points"), DEFAULT_MOTD ("default_motd"), CREATE_GANG_PRICE ("create_gang_price"),
	DEFAULT_LOCATION ("default_location"), DEFAULT_CELL ("default_cell"), DEFAULT_RANK ("default_rank"), ADD_GANG_MEMBER_PRICE ("add_gang_member_price"),
	DEFAULT_DRUG_AMOUNT ("default_drug_amount"), DEFAULT_DRUG_COST ("default_drug_cost"), DEFAULT_HEAD_AMOUNT ("default_head_amount"), DEFAULT_BOUNTY_AMOUNT("default_bounty_amount");
	
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
