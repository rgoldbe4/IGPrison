package us.ignitiongaming.enums;

public enum IGSettings {
	RANKUP_D ("rankup_d"), RANKUP_C ("rankup_c"), RANKUP_B ("rankup_b"), RANKUP_A ("rankup_a"), DEFAULT_WORLD_NAME ("default_world_name"),
	DEFAULT_DONATOR_POINTS ("default_donator_points"), DEFAULT_MOTD ("default_motd");
	
	private String label;
	
	private IGSettings (String label) { this.label = label; }
	
	public String getLabel() { return label; }
	
}
