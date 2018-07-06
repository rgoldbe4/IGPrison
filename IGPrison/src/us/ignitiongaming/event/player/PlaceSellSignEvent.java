package us.ignitiongaming.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import us.ignitiongaming.config.SignTags;
import us.ignitiongaming.enums.IGRankNodes;

public class PlaceSellSignEvent implements Listener {

	@EventHandler
	public void onPlaceSellSign(SignChangeEvent event) {
		try {
			Player player = event.getPlayer();
			//Determine if the player was attempting to make a sell event...
			if (event.getLine(1).contains("[Sell]")) {
				//Only staff or wardens may create events...
				boolean isPlayerStaff = IGRankNodes.getPlayerRank(player).isStaff();
				if (isPlayerStaff) {
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
