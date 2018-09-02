package us.ignitiongaming.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.util.items.Drugs;

public class ConvertCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (lbl.contains("convert")) {
					int cactusAmount = 0, sugarCaneAmount = 0;
					for (int i = 0; i < player.getInventory().getSize(); i++) {
						ItemStack item = player.getInventory().getItem(i);
						if (item == null) continue;
						switch (item.getType()) {
							case CACTUS:
								cactusAmount += item.getAmount();
								break;
							case SUGAR_CANE:
								sugarCaneAmount += item.getAmount();
								break;
							default:
								break;
						}
					}
					player.getInventory().addItem(Drugs.getWarrior(cactusAmount));
					player.getInventory().addItem(Drugs.getAutoDrop(sugarCaneAmount));
					player.sendMessage(GlobalTags.DRUGS + "Your inventory has converted specific items into drugs.");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
