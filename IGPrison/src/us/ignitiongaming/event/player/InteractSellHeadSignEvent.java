package us.ignitiongaming.event.player;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.config.ServerDefaults;
import us.ignitiongaming.config.SignTags;
import us.ignitiongaming.util.convert.CurrencyConverter;

public class InteractSellHeadSignEvent implements Listener {
	
	@EventHandler
	public void onRightClickSellOresSign(PlayerInteractEvent event) {
		try {
			//Verify the user is right clicking.
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK){
				
				if (event.getClickedBlock().getState() instanceof Sign) {
					Player player = event.getPlayer();
					Sign sign = (Sign) event.getClickedBlock().getState();
					
					if (sign.getLine(0).contains(SignTags.SELL_HEAD)) {
						double price = Double.parseDouble(ChatColor.stripColor(sign.getLine(1).replace("$", "").replace(",", "")));
						
						int numSkulls = getNumberOfSkulls(player);
						
						if (numSkulls == 0) {
							player.sendMessage(GlobalTags.DEFIANCE + "You do not have any SKULLS.");
						} else {
							double amount = price * numSkulls;
							player.sendMessage(GlobalTags.DEFIANCE + "You made " + CurrencyConverter.convertToCurrency(amount));
							ServerDefaults.econ.depositPlayer(player, amount);
						}
						
					}
				}
					
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private int getNumberOfSkulls(Player player) {
		int amount = 0;
		for (int i = 0; i < player.getInventory().getSize(); i++) {
			if (player.getInventory().getItem(i) == null) continue;
			if (player.getInventory().getItem(i).getType() == Material.SKULL_ITEM) {
				amount += player.getInventory().getItem(i).getAmount();
				player.getInventory().setItem(i, new ItemStack(Material.AIR));
			}
		}
		return amount;
	}
}
