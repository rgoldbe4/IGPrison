package us.ignitiongaming.event.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import us.ignitiongaming.config.PlayerTags;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.rank.IGRank;
import us.ignitiongaming.factory.player.IGPlayerDonatorFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerRankFactory;

public class PlayerChatEvent implements Listener {
	
	@EventHandler
	public static void onPlayerTalk(AsyncPlayerChatEvent event) {
		IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(event.getPlayer());
		IGRank igRank = IGPlayerRankFactory.getIGPlayerRank(igPlayer);
		event.setFormat((IGPlayerDonatorFactory.isIGPlayerDonator(igPlayer) && !igRank.isStaff()? PlayerTags.DONATOR:"") + igRank.getTag() + igRank.getNameColor() + igPlayer.getName() + " §r> " + event.getMessage());
	}
}
