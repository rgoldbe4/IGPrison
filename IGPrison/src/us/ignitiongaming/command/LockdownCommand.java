package us.ignitiongaming.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalMessages;
import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.entity.lockdown.IGLockdown;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.enums.IGCells;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.factory.lockdown.IGLockdownFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.util.convert.BooleanConverter;
import us.ignitiongaming.util.convert.ChatConverter;
import us.ignitiongaming.util.convert.DateConverter;

public class LockdownCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				IGRankNodes playerRank = IGRankNodes.getPlayerRank(player);
				// [/lockdown]
				if (lbl.equalsIgnoreCase("lockdown")) {
					
					if (args.length == 0) {
						viewLockdowns(player);
					} 
					else if (args.length == 1) {
						
						if (args[0].equalsIgnoreCase("help")) {
							showHelp(player);
						}
						else if (args[0].equalsIgnoreCase("list")) {
							viewLockdowns(player);
						}
						else if (IGCells.isCell(args[0])) {
							//Make sure that only staff can access this command.
							if (playerRank.isStaff()) {
								IGCells cell = IGCells.getCell(args[0]);
								IGPlayer staff = IGPlayerFactory.getIGPlayerByPlayer(player);
								toggleLockdown(player, staff, cell);
							} else {
								player.sendMessage(GlobalMessages.NO_PERMISSIONS);
							}
						}
						else {
							showHelp(player);
						}
					}
					else {
						player.sendMessage(GlobalMessages.INVALID_COMMAND);
					}
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	private void viewLockdowns(Player player) {
		ChatConverter.clearPlayerChat(player);
		player.sendMessage("§8 -- " + GlobalTags.LOCKDOWN + "Status §8--");
		for (IGCells cell : IGCells.values()) {
			boolean isCellInLockdown = IGLockdownFactory.isCellInLockdown(cell);
			player.sendMessage(" " + cell.getTag() + ">> " + BooleanConverter.getYesNoFromBooleanWithColor(isCellInLockdown));
		}
	}
	
	private void showHelp(Player player) {
		ChatConverter.clearPlayerChat(player);
		player.sendMessage("§8 -- " + GlobalTags.LOCKDOWN + "Commands §8--");
		player.sendMessage(" /lockdown -> Displays the current lockdown status.");
		player.sendMessage(" /lockdown §olist§r -> Displays the current lockdown status.");
		player.sendMessage(" /lockdown §ohelp§r -> Displays list of commands.");
		player.sendMessage(" /lockdown §o<a/b/c/d>§r -> Toggle lockdown of cell.");
	}
	
	private void toggleLockdown(Player player, IGPlayer staff, IGCells cell) {
		
		if (IGLockdownFactory.isCellInLockdown(cell)) {
			//End lockdown
			IGLockdown lockdown = IGLockdownFactory.getLockdownByCell(cell);
			lockdown.setEndId(staff.getId());
			lockdown.setEnded(DateConverter.getCurrentTime());
			lockdown.save();
			
			//Update world region
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "region flag " + cell.getLabel().toLowerCase() + "_block_pvp pvp -w world allow");
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), 
					"region flag " + cell.getLabel() + "_block_pvp greeting -w world " + cell.getTag() + "&4&lPvP Enabled");
			
			//Tell server.
			Bukkit.broadcastMessage(GlobalTags.LOCKDOWN + cell.getTag() + "§ais no longer under lockdown.");
		} else {
			//Start lockdown.
			IGLockdownFactory.add(cell.toCell(), staff);
		
			//Update world region.
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "region flag " + cell.getLabel().toLowerCase() + "_block_pvp pvp -w world deny");
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), 
					"region flag " + cell.getLabel() + "_block_pvp greeting -w world " + cell.getTag() + "&b&lLockdown!");
			
			//Tell server.
			Bukkit.broadcastMessage(GlobalTags.LOCKDOWN + cell.getTag() + "§cis under lockdown.");
		}
	}
	
}
