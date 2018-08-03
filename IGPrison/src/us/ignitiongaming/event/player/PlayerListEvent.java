package us.ignitiongaming.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.factory.player.IGPlayerFactory;

public class PlayerListEvent implements Listener {

	@EventHandler
	public static void onPlayerJoinUpdatePlayerList(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
		if (igPlayer.isValid()) {
			IGRankNodes rank = IGRankNodes.getPlayerRank(player);
			player.setPlayerListName(rank.getFormatting() + igPlayer.getDisplayName());
		}
	}
}
