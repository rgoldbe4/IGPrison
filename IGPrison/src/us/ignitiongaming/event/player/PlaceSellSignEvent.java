package us.ignitiongaming.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import us.ignitiongaming.config.ServerDefaults;
import us.ignitiongaming.config.SignTags;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.enums.IGSettings;
import us.ignitiongaming.enums.IGSignItems;
import us.ignitiongaming.util.convert.CurrencyConverter;

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
					IGSignItems signItem = IGSignItems.getItem(event.getLine(3));
					event.setLine(0, SignTags.SELL);
					event.setLine(1, "§a" + event.getLine(2));
					event.setLine(2, "§8" + event.getLine(3));
					event.setLine(3, "§9§l" + signItem.getAmount());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@EventHandler
	public void onPlaceSellHeadSign(SignChangeEvent event) {
		try {
			Player player = event.getPlayer();
			//Determine if the player was attempting to make a sell event...
			if (event.getLine(1).contains("[SellHeads]")) {
				//Only staff or wardens may create events...
				boolean isPlayerStaff = IGRankNodes.getPlayerRank(player).isStaff();
				if (isPlayerStaff) {
					String amt = CurrencyConverter.convertToCurrency(Double.parseDouble(ServerDefaults.getSetting(IGSettings.DEFAULT_HEAD_AMOUNT).getValue().toString()));
					event.setLine(0, SignTags.SELL_HEAD);
					event.setLine(1, "§a" + amt);
					event.setLine(2, "");
					event.setLine(3, "§8§l1");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
