package us.ignitiongaming.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import us.ignitiongaming.config.SignTags;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.enums.IGSignItems;
import us.ignitiongaming.util.convert.CurrencyConverter;

public class PlaceBuySignEvent implements Listener {
	
	@EventHandler
	public void onPlaceSellSign(SignChangeEvent event) {
		try {
			Player player = event.getPlayer();
			//Determine if the player was attempting to make a sell event...
			if (event.getLine(1).contains("[BUY]")) {
				//Only staff or wardens may create events...
				boolean isPlayerStaff = IGRankNodes.getPlayerRank(player).isStaff();
				if (isPlayerStaff) {
					IGSignItems signItem = IGSignItems.getItem(event.getLine(3));
					event.setLine(0, SignTags.BUY);
					event.setLine(1, "§a" + CurrencyConverter.convertToCurrency(event.getLine(2)));
					event.setLine(2, "§8" + signItem.getName());
					event.setLine(3, "§9" + signItem.getAmount());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
