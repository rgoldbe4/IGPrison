package us.ignitiongaming.event.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import us.ignitiongaming.enums.IGMenuItems;
import us.ignitiongaming.enums.IGMenus;
import us.ignitiongaming.util.convert.ChatConverter;

public class BuyDefiancePointsMenu {

	@EventHandler
	public static void onInteractBuyMenu(InventoryClickEvent event) {
		try {
			Player player = (Player) event.getWhoClicked();
			ItemStack item = event.getCurrentItem();
			Inventory menu = event.getInventory();
			
			//Step 1: Determine if it is a valid Buy Commands inventory menu.
			if (ChatConverter.stripColor(menu.getName()).equalsIgnoreCase(IGMenus.BUY_COMMANDS.getStrippedName())) {
				IGMenuItems menuItem = IGMenuItems.getMenuItem(item);
				//Ignore the event and whatever they did if they click a purchased item.
				if (menuItem == IGMenuItems.PURCHASED) {
					event.setResult(Result.DENY);
					event.setCancelled(true);
					player.sendMessage("");
					return;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
