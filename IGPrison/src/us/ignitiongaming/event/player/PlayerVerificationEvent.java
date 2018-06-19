package us.ignitiongaming.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.factory.player.IGPlayerFactory;

public class PlayerVerificationEvent implements Listener{

	/*
	 * Features in this class:
	 * - Check if player exists in database (IGPlayer)
	 * - Check if player changed their name (via Mojang)
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
}
