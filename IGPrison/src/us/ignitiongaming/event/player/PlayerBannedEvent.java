package us.ignitiongaming.event.player;

import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import us.ignitiongaming.factory.player.IGPlayerBannedFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;

public class PlayerBannedEvent implements Listener {
	@EventHandler
	public static void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		Date banDate = IGPlayerBannedFactory.isBanned(IGPlayerFactory.getIGPlayerByPlayer(player));
		if(banDate != null){
			player.kickPlayer("You have been banned until " + banDate.toString());
		}
	}
}