package us.ignitiongaming.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.factory.player.IGPlayerBannedFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;

public class PlayerBannedEvent implements Listener {
	@EventHandler
	public static void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
		if(igPlayer != null){
			if(IGPlayerBannedFactory.isBanned(igPlayer)){
				player.kickPlayer("You have been banned until " + IGPlayerBannedFactory.getBanDate(igPlayer));
			}
		}
	}
}
