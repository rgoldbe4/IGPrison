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

import us.ignitiongaming.config.ServerDefaults;
import us.ignitiongaming.config.SignTags;
import us.ignitiongaming.enums.SignOres;

public class InteractSellSignEvent implements Listener {

	@EventHandler
	public void onRightClickSellOresSign(PlayerInteractEvent event)
	{
		try
		{
			//Verify the user is right clicking.
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
			{
				//Verify if the user is right clicking a sign.
				if (event.getClickedBlock().getState() instanceof Sign)
				{
					//Example sign...
					/*
					 * 0 -> "[Sell]"
					 * 1 -> $10.00
					 * 2 -> Iron
					 */
					//Sign found... Verify it's a sell sign
					Sign sign = (Sign) event.getClickedBlock().getState();
					Player player = (Player) event.getPlayer();
					if (sign.getLine(0).contains(SignTags.SELL)) {
						double price = Double.parseDouble(ChatColor.stripColor(sign.getLine(1).replace("$", "").replace(",", "")));
						
						SignOres item = SignOres.getItem(ChatColor.stripColor(sign.getLine(2).toUpperCase()));
						int amount = removeOres(player, item);
						if (amount == 0) {
							player.sendMessage(SignTags.SELL + " §4You do not have any " + item.getName().toLowerCase());
						} else {
							double totalRewarded = amount * price;
							ServerDefaults.econ.depositPlayer(player, totalRewarded);
							player.sendMessage(SignTags.SELL + " §a$" + totalRewarded + "§f was given to you.");
						}
					}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
			
	}
	
	private int removeOres(Player player, SignOres ore) {
		int amount = 0;
		Material material = ore.toMaterial();
		
		for (int i = 0; i < player.getInventory().getSize(); i++) {
			if (player.getInventory().getItem(i) == null) { continue; }
			if (player.getInventory().getItem(i).getType() == material) {
				amount += player.getInventory().getItem(i).getAmount();
				player.getInventory().setItem(i, new ItemStack(Material.AIR));
			}
		}
		return amount;
	}
	
}
