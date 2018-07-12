package us.ignitiongaming.config;

import java.util.List;

import net.milkbowl.vault.economy.Economy;
import us.ignitiongaming.entity.other.IGSetting;
import us.ignitiongaming.enums.IGCells;
import us.ignitiongaming.enums.IGLocations;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.enums.IGSettings;
import us.ignitiongaming.event.other.IGSettingFactory;

public class ServerDefaults {

	public static List<IGSetting> settings = IGSettingFactory.getSettings();
	
	/**
	 * Checks the database for the correct key, then returns the object.
	 * @param label
	 * @return Object -> REMEMBER TO CONVERT THIS INTO WHAT YOU EXPECT (IGRankNodes, String, etc)
	 */
	public static IGSetting getSetting(IGSettings label) {
		for (IGSetting setting : settings) {
			if (setting.getLabel().toLowerCase().equalsIgnoreCase(label.getLabel().toLowerCase())) return setting;
		}
		return settings.get(0);
	}
	
	@Deprecated
	public static IGRankNodes DEFAULT_RANK = IGRankNodes.D;
	@Deprecated
	public static int DEFAULT_DONATOR_PERK_POINTS = 10;
	@Deprecated
	public static final String DEFAULT_MOTD = "§6[§8I§4G§6] §8Ignition Gaming Minecraft";
	@Deprecated
	public static final String DEFAULT_WORLD_NAME = "world";
	@Deprecated
	public static IGLocations DEFAULT_LOCATION = IGLocations.SPAWN;
	@Deprecated
	public static IGCells DEFAULT_CELL = IGCells.D;
	
	public static Economy econ;
	
}
