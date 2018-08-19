package us.ignitiongaming.event.player;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import us.ignitiongaming.config.ServerDefaults;
import us.ignitiongaming.config.SignTags;
import us.ignitiongaming.enums.IGSignItems;
import us.ignitiongaming.util.convert.ChatConverter;

public class InteractBuySignEvent implements Listener {
	
	@EventHandler
	public static void onPlayerInteractBuySign(PlayerInteractEvent event) {
		try {
			//Verify the user is right clicking.
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				//Verify if the user is right clicking a sign.
				if (event.getClickedBlock().getState() instanceof Sign) {
					//Example sign...
					/*
					 * 0 -> "[Sell]"
					 * 1 -> $10.00
					 * 2 -> Iron
					 */
					//Sign found... Verify it's a sell sign
					Sign sign = (Sign) event.getClickedBlock().getState();
					Player player = (Player) event.getPlayer();
					if (sign.getLine(0).contains(SignTags.BUY)) {
						String strippedPrice = ChatConverter.stripColor( ChatConverter.stripCurrency( sign.getLine(1) ) );
						IGSignItems signItem = IGSignItems.getItem( ChatConverter.stripColor( sign.getLine(2) ) );
						double balance = ServerDefaults.econ.getBalance(player);

						double price = 0.0;
						//If not free...
						if (!strippedPrice.equalsIgnoreCase("free")) {
							price = Double.parseDouble(strippedPrice);
						}
						
						if (balance >= price) {
						
							//Charge the user.
							ServerDefaults.econ.withdrawPlayer(player, price);
							
							//Give them the item.
							ItemStack item = new ItemStack(signItem.toMaterial());
							item.setAmount(signItem.getBuyAmount());
							
							player.getInventory().addItem(item);
							player.sendMessage(SignTags.BUY + " You were given §e" + signItem.getName() + "§f.");
						} else {
							player.sendMessage(SignTags.BUY + " §4You do not have enough money to buy §f" + signItem.getName() + "§4.");
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
