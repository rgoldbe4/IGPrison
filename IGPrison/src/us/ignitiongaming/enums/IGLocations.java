package us.ignitiongaming.enums;


public enum IGLocations {
	SPAWN ("spawn"), SOLITARY ("solitary"), D ("d"), C ("c"), B ("b"), A ("a");
	
	private String label;
	
	private IGLocations(String label) { this.label = label; }
	
	public String getLabel() { return label; }
	
	public static IGLocations getLocationByLabel(String label) {
		IGLocations loc = IGLocations.SPAWN;
		for (IGLocations location : IGLocations.values()) {
			if (location.getLabel().equalsIgnoreCase(label)) loc = location;
		}
		return loc;
	}
}
