package us.ignitiongaming.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SmeltCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				
				// [/smelt]
				if (lbl.equalsIgnoreCase("smelt")) {
					//Go through the user's inventory and smelt all in their inventory.
					for (int i = 0; i < player.getInventory().getSize(); i++) {
						if (player.getInventory().getItem(i) == null) { continue; }
						Material material = player.getInventory().getItem(i).getType();
						int amount = player.getInventory().getItem(i).getAmount();
						if (material == Material.IRON_ORE) {
							ItemStack ironIngots = new ItemStack(Material.IRON_INGOT);
							ironIngots.setAmount(amount);
							player.getInventory().setItem(i, ironIngots);
						}
						
						if (material == Material.GOLD_ORE) {
							ItemStack goldIngots = new ItemStack(Material.GOLD_INGOT);
							goldIngots.setAmount(amount);
							player.getInventory().setItem(i, goldIngots);
						}
					}
				}
			}
		} catch (Exception ex) {
		
		}
		return false;
	}

}
