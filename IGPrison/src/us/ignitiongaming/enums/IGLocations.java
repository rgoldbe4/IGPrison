package us.ignitiongaming.enums;

import org.bukkit.Location;

import us.ignitiongaming.entity.other.IGLocation;
import us.ignitiongaming.factory.other.IGLocationFactory;


public enum IGLocations {
	SPAWN ("spawn"), SOLITARY ("solitary"), D ("d"), C ("c"), B ("b"), A ("a"),
	A_TOWER ("a_block_tower"), B_TOWER ("b_block_tower"), C_TOWER ("c_block_tower"), D_TOWER ("d_block_tower"),
	A_VISITATION ("a_visitation"), B_VISITATION ("b_visitation"), C_VISITATION ("c_visitation"), D_VISITATION ("d_visitation"),
	TUTORIAL ("tutorial"), A_ENDER_PORTAL("a_ender_portal");
	
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
	
	public Location toLocation() {
		IGLocation igLocation = IGLocationFactory.getLocationByIGLocations(this);
		return igLocation.toLocation();
	}
}
