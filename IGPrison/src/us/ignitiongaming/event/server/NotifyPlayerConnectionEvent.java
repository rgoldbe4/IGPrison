package us.ignitiongaming.event.server;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.factory.player.IGPlayerBannedFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;

public class NotifyPlayerConnectionEvent implements Listener {

	@EventHandler
	public static void onPlayerJoinMessage(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		if (!player.hasPlayedBefore()) {
			event.setJoinMessage("§D§l< §f" + player.getName() + " §D§l>");
		} else {
			event.setJoinMessage("§2§l< §f" + player.getName() + " §2§l>");
		}
	}
	
	@EventHandler
	public static void onPlayerLeaveMessage(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
		
		if (igPlayer.isValid()) {
			if (!IGPlayerBannedFactory.isBanned(igPlayer)) {
				event.setQuitMessage("§4§l< §f" + player.getName() + " §4§l>");
			} 
		} else {
			event.setQuitMessage("§4§l< §f" + player.getName() + " §4§l>");
		}
	}
}
