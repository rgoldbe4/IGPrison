package us.ignitiongaming.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import us.ignitiongaming.config.GlobalMessages;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerStats;
import us.ignitiongaming.factory.player.IGPlayerDonatorFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerStatsFactory;
import us.ignitiongaming.util.convert.DateConverter;

public class PlayerVerificationEvent implements Listener {

	/*
	 * Features in this class:
	 * - Check if player exists in database (IGPlayer)
	 * - Check if player changed their name (via Mojang)
	 * - Check if the player has donated.
	 * - Update the player's recent login.
	 */
	
	@EventHandler
	public void addPlayerToDatabase(PlayerJoinEvent event) {
		try {
			Player player = event.getPlayer();
			IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
			//Either returns an entity (which means it exists) or returns null (add it!).
			if (igPlayer == null) {
				IGPlayerFactory.add(player);
			}
		} catch (Exception ex) {
			
		}
	}
	
	@EventHandler
	public void verifyNameChange(PlayerJoinEvent event) {
		try {
			Player player = event.getPlayer();
			IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
			
			if (!igPlayer.getName().equalsIgnoreCase(player.getName())) {
				igPlayer.setName(player.getName());
				igPlayer.save();
			}
		} catch (Exception ex) {
			
		}
	}
	
	@EventHandler
	public void verifyIfPlayerIsDonator(PlayerJoinEvent event) {
		try {
			Player player = event.getPlayer();
			IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
			boolean isDonator = IGPlayerDonatorFactory.isIGPlayerDonator(igPlayer);
			if (isDonator) {
				player.sendMessage(GlobalMessages.THANK_YOU_DONATE);
			}
			
		} catch (Exception ex) {
			
		}
	}
	
	@EventHandler
	public void updateLatestLogin(PlayerJoinEvent event) {
		try {
			Player player = event.getPlayer();
			IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
			IGPlayerStats igStats = IGPlayerStatsFactory.getIGPlayerStatsByIGPlayer(igPlayer);
			igStats.setLastLogin(DateConverter.getCurrentTimeString());
			igStats.save();
		} catch (Exception ex) {
			
		}
	}
}
