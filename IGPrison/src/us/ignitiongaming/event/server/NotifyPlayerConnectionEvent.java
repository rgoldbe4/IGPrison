package us.ignitiongaming.event.server;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
		event.setQuitMessage("§4§l< §f" + player.getName() + " §4§l>");
	}
}
