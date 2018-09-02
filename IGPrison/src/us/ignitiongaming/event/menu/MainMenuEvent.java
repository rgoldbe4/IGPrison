package us.ignitiongaming.event.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import us.ignitiongaming.enums.IGMenuItems;
import us.ignitiongaming.enums.IGMenus;
import us.ignitiongaming.util.convert.ChatConverter;
import us.ignitiongaming.util.handy.InventoryMenu;

public class MainMenuEvent implements Listener {

	@EventHandler
	public static void onInteractMainMenu(InventoryClickEvent event) {
		try {
			if (event.getCurrentItem() == null) return;
			Player player = (Player) event.getWhoClicked();
			ItemStack item = event.getCurrentItem();
			Inventory menu = event.getInventory();
			
			//Step 1: Determine if it is a valid Buy Commands inventory menu.
			if (ChatConverter.stripColor(menu.getName()).equalsIgnoreCase(IGMenus.GENERAL_MENU.getStrippedName())) {
				IGMenuItems menuItem = IGMenuItems.getMenuItem(item);		
				//Ignore the event and whatever they did if they click a purchased item.
				if (menuItem == IGMenuItems.CLOSE) {
					player.closeInventory();
					return;
				} else {
					if (item.getItemMeta() == null) return;
					if (item.getItemMeta().getDisplayName() == null) return;
					if (ChatConverter.stripColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase(IGMenus.DEFIANCE_POINTS.getStrippedName())) {
						player.openInventory(InventoryMenu.getBuyDefianceMenu(player));
					} else {
						player.openInventory(InventoryMenu.getBuyCommandMenu(player));
					}
				}
				
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
