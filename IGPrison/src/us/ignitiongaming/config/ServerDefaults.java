package us.ignitiongaming.config;

import net.milkbowl.vault.economy.Economy;
import us.ignitiongaming.enums.IGLocations;
import us.ignitiongaming.enums.IGRanks;

public class ServerDefaults {

	/*
	 * This is a temporary class until I implement server settings in the database.
	 */
	
	public static IGRanks DEFAULT_RANK = IGRanks.D;
	public static int DEFAULT_DONATOR_PERK_POINTS = 10;
	public static final String DEFAULT_MOTD = "§6[§8I§4G§6] §8Ignition Gaming Minecraft";
	public static final String DEFAULT_WORLD_NAME = "world";
	public static IGLocations DEFAULT_LOCATION = IGLocations.SPAWN;
	
	public static Economy econ;
	
}
