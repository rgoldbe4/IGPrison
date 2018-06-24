package us.ignitiongaming.event.solitary;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerSolitary;
import us.ignitiongaming.enums.IGLocations;
import us.ignitiongaming.factory.other.IGLocationFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerSolitaryFactory;

public class VerifySolitaryEvent implements Listener {

	@EventHandler
	public void checkIfPlayerHasServed(AsyncPlayerChatEvent event) {
		try {
			Player player = event.getPlayer();
			IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
			
			//Only care if the player is in solitary.
			if (IGPlayerSolitaryFactory.isIGPlayerInSolitary(igPlayer)) {
				IGPlayerSolitary igPlayerSolitary = IGPlayerSolitaryFactory.getIGPlayerInSolitary(igPlayer);
				if (igPlayerSolitary.hasServed()) {
					IGPlayerSolitaryFactory.remove(igPlayer);
					player.sendMessage(GlobalTags.SOLITARY + "§eYou have served your time in solitary.");
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@EventHandler
	public void checkIfPlayerIsInSolitaryOnLogin(PlayerJoinEvent event) {
		try {
			Player player = event.getPlayer();
			IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
			//Ensure that we have a valid igPlayer object.
			if (igPlayer != null) {
				//Check if the player is in solitary.
				if (IGPlayerSolitaryFactory.isIGPlayerInSolitary(igPlayer)) {
					IGPlayerSolitary igPlayerSolitary = IGPlayerSolitaryFactory.getIGPlayerInSolitary(igPlayer);
					if (!igPlayerSolitary.hasServed()) {
						//Make sure they are teleported back to solitary on log in.
						player.teleport(IGLocationFactory.getLocationByIGLocations(IGLocations.SOLITARY).toLocation());
					} else {
						//Teleport them to spawn.
						
					}
					
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
