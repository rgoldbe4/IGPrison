package us.ignitiongaming.event.player;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import us.ignitiongaming.config.ServerDefaults;
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
					
					//Sign found... Verify it's a pickaxe sign
					Sign sign = (Sign) event.getClickedBlock().getState();
					Player player = (Player) event.getPlayer();
					if (sign.getLine(0).equalsIgnoreCase("[Sell]"))
					{
						double price = Double.parseDouble(sign.getLine(1).replace("$", "").replace(",", ""));
						SignOres item = SignOres.getItem(sign.getLine(2));
					
						int amount = removeOres(player, item);
						double totalRewarded = amount * price;
						ServerDefaults.econ.depositPlayer(player, totalRewarded);
					}
				}
			}
		}
		catch (Exception ex)
		{
			
		}				
			
	}
	
	private int removeOres(Player player, SignOres ore) {
		int amount = 0;
		switch (ore) {
			case IRON:
				for (int i = 0; i < player.getInventory().getContents().length; i++) {
					if (player.getInventory().getContents()[i].getType() == Material.IRON_INGOT) {
						amount += player.getInventory().getContents()[i].getAmount();
						player.getInventory().getContents()[i].setType(Material.AIR);
					}
				}
				break;
			case GOLD:
				for (int i = 0; i < player.getInventory().getContents().length; i++) {
					if (player.getInventory().getContents()[i].getType() == Material.GOLD_INGOT) {
						amount += player.getInventory().getContents()[i].getAmount();
						player.getInventory().getContents()[i].setType(Material.AIR);
					}
				}
				break;
			case DIAMOND:
				for (int i = 0; i < player.getInventory().getContents().length; i++) {
					if (player.getInventory().getContents()[i].getType() == Material.DIAMOND) {
						amount += player.getInventory().getContents()[i].getAmount();
						player.getInventory().getContents()[i].setType(Material.AIR);
					}
				}
				break;
			case EMERALD:
				for (int i = 0; i < player.getInventory().getContents().length; i++) {
					if (player.getInventory().getContents()[i].getType() == Material.EMERALD) {
						amount += player.getInventory().getContents()[i].getAmount();
						player.getInventory().getContents()[i].setType(Material.AIR);
					}
				}				
				break;
		}
		return amount;
	}
	
}
