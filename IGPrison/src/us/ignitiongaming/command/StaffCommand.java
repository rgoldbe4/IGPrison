package us.ignitiongaming.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

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
				
				ScoreboardManager manager = Bukkit.getScoreboardManager();
				Scoreboard guardBoard = manager.getNewScoreboard();
				Scoreboard wardenBoard = manager.getNewScoreboard();
				
				Objective wardenObj = wardenBoard.registerNewObjective("showwarden", "dummy");
				wardenObj.setDisplaySlot(DisplaySlot.BELOW_NAME);
				wardenObj.setDisplayName(IGRankNodes.WARDEN.getTag());
				
				Objective guardObj = guardBoard.registerNewObjective("showguard", "dummy");
				guardObj.setDisplaySlot(DisplaySlot.BELOW_NAME);
				guardObj.setDisplayName(IGRankNodes.GUARD.getTag());
				
				if (lbl.equalsIgnoreCase("guard")) {
					if (player.hasPermission(IGRankNodes.GUARD.getNode())) {
						if (IGList.clockedIn.contains(player)) {
							IGList.clockedIn.remove(player);
							player.sendMessage(GlobalTags.LOGO + "You have been clocked out.");
							player.getInventory().clear();
							player.removeScoreboardTag("showguard");
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
							player.setScoreboard(guardBoard);
							
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
							player.removeScoreboardTag("showwarden");
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
							player.setScoreboard(wardenBoard);
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
