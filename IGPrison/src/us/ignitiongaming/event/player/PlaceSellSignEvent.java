package us.ignitiongaming.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import us.ignitiongaming.config.SignTags;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.rank.IGRank;
import us.ignitiongaming.enums.IGRanks;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerRankFactory;
import us.ignitiongaming.factory.rank.IGRankFactory;

public class PlaceSellSignEvent implements Listener {

	@EventHandler
	public void onPlaceSellSign(SignChangeEvent event) {
		try {
			Player player = event.getPlayer();
			//Determine if the player was attempting to make a sell event...
			if (event.getLine(1).contains("[Sell]")) {
				IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
				//Only staff or wardens may create events...
				IGRank playerRank = IGPlayerRankFactory.getIGPlayerRank(igPlayer);
				boolean hasWarden = (playerRank.getId() == IGRankFactory.getIGRankByRank(IGRanks.WARDEN).getId());
				boolean hasStaff = (playerRank.getId() == IGRankFactory.getIGRankByRank(IGRanks.STAFF).getId());
				if (hasWarden || hasStaff) {
					event.setLine(0, SignTags.SELL);
					event.setLine(1, "§a" + event.getLine(2));
					event.setLine(2, "§8" + event.getLine(3));
					event.setLine(3, "");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
