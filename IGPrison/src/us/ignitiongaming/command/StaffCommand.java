package us.ignitiongaming.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalMessages;
import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.singleton.IGList;
import us.ignitiongaming.util.items.GuardArmor;
import us.ignitiongaming.util.items.StaffBaton;
import us.ignitiongaming.util.items.WardenArmor;

public class StaffCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				
				if (lbl.equalsIgnoreCase("guard")) {
					if (player.hasPermission(IGRankNodes.GUARD.getNode())) {
						if (IGList.clockedIn.contains(player)) {
							IGList.clockedIn.remove(player);
							player.sendMessage(GlobalTags.LOGO + "You have been clocked out.");
							player.getInventory().clear();
						} else {
							IGList.clockedIn.add(player);
							player.sendMessage(GlobalTags.LOGO + "You have been clocked in.");
							//Remove all items from inventory.
							player.getInventory().clear();
							//Add guard armor.
							player.getInventory().setHelmet(GuardArmor.getHelmet());
							player.getInventory().setChestplate(GuardArmor.getChestplate());
							player.getInventory().setLeggings(GuardArmor.getLeggings());
							player.getInventory().setBoots(GuardArmor.getBoots());
							//Add guard equipment
							player.getInventory().addItem(StaffBaton.getBaton());
							player.getInventory().addItem(StaffBaton.getSolitaryBaton());
						}
					} else {
						player.sendMessage(GlobalMessages.NO_PERMISSIONS);
					}
				}
				
				if (lbl.equalsIgnoreCase("warden")) {
					
					if (player.hasPermission(IGRankNodes.WARDEN.getNode())) {
						
						if(IGList.clockedIn.contains(player)) {
							IGList.clockedIn.remove(player);
							player.sendMessage(GlobalTags.LOGO + "You have been clocked out.");
							player.getInventory().clear();
						} else {
							IGList.clockedIn.add(player);
							player.sendMessage(GlobalTags.LOGO + "You have been clocked in.");
							//Remove all items from inventory.
							player.getInventory().clear();
							//Add guard armor.
							player.getInventory().setHelmet(WardenArmor.getHelmet());
							player.getInventory().setChestplate(WardenArmor.getChestplate());
							player.getInventory().setLeggings(WardenArmor.getLeggings());
							player.getInventory().setBoots(WardenArmor.getBoots());
							//Add guard equipment
							player.getInventory().addItem(StaffBaton.getBaton());
							player.getInventory().addItem(StaffBaton.getSolitaryBaton());
							player.getInventory().addItem(StaffBaton.getShockBaton());
						}
					} else {
						player.sendMessage(GlobalMessages.NO_PERMISSIONS);
					}
					
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
}
