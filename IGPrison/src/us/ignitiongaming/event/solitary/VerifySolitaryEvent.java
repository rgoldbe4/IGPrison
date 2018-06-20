package us.ignitiongaming.event.solitary;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerSolitary;
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
			
		}
	}
}
