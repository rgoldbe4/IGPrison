package us.ignitiongaming.event.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import us.ignitiongaming.config.GlobalMessages;
import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.entity.menu.IGMenuItem;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerStats;
import us.ignitiongaming.enums.IGMenuItems;
import us.ignitiongaming.enums.IGMenus;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerMenuItemFactory;
import us.ignitiongaming.factory.player.IGPlayerStatsFactory;
import us.ignitiongaming.util.convert.ChatConverter;

public class BuyCommandsMenu implements Listener {
	
	@EventHandler
	public static void onInteractBuyMenu(InventoryClickEvent event) {
		try {
			if (event.getCurrentItem() == null) return;
			Player player = (Player) event.getWhoClicked();
			ItemStack item = event.getCurrentItem();
			Inventory menu = event.getInventory();
			
			//Step 1: Determine if it is a valid Buy Commands inventory menu.			
			if (ChatConverter.stripColor(menu.getName()).equalsIgnoreCase(IGMenus.BUY_COMMANDS.getStrippedName())) {
				IGMenuItems menuItem = IGMenuItems.getMenuItem(item);
				
				//Ignore the event and whatever they did if they click a purchased item.
				if (menuItem == IGMenuItems.PURCHASED) {
					event.setResult(Result.DENY);
					return;
				}
				
				if (menuItem == IGMenuItems.CLOSE) {
					player.closeInventory();
					return;
				}
				
				IGMenuItem dbItem = menuItem.toMenuItem();
				IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
				IGPlayerStats igPlayerStats = IGPlayerStatsFactory.getIGPlayerStatsByIGPlayer(igPlayer);
				
				//Determine if the player can afford with their current points.
				if (igPlayerStats.getPoints() >= dbItem.getPoints()) {
					player.sendMessage(GlobalTags.DEFIANCE + "§aYou have successfully purchased: " + menuItem.getDisplayName());
					igPlayerStats.removePoints(dbItem.getPoints());
					igPlayerStats.save();
					
					//Update the server to let us know they bought something.
					IGPlayerMenuItemFactory.add(igPlayer, dbItem);
					
					//Add the permission node to the player.
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " add " + menuItem.getNode());
					
					//Close out of the inventory.
					event.setResult(Result.DENY);
					player.closeInventory();
				} else {
					player.sendMessage(GlobalMessages.INSUFFICIENT_FUNDS_SHOP);
					
					//Close out of the inventory so they can see the error message.
					event.setResult(Result.DENY);
					player.closeInventory();
				}
				
				event.setCancelled(true);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
