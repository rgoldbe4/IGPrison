package us.ignitiongaming.command;

import java.util.List;

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
import us.ignitiongaming.util.convert.DateConverter;

public class LockdownCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				// [/lockdown]
				if (lbl.equalsIgnoreCase("lockdown")) {
					
					if (player.hasPermission("igprison.lockdown")) {
						if (args.length == 0) {
							List<IGLockdown> currentLockdowns = IGLockdownFactory.getCurrentLockdowns();
							
							if (currentLockdowns.size() == 0) player.sendMessage(GlobalTags.LOCKDOWN + "�cNo current lockdowns.");
							//Display a message for each lockdown.
							for (IGLockdown lockdown : currentLockdowns) {
								player.sendMessage("�8[[");
								player.sendMessage("�eCell: �f" + lockdown.getCell().getLabel());
								player.sendMessage("�eStarted By: �f" + lockdown.getStartPlayer().getName());
								player.sendMessage("�eStarted At: �f" + lockdown.getStartedFriendly());
								player.sendMessage("�8]]"); 
							}
						}
						
						else if (args.length == 1) {
							// [/lockdown help]
							if (args[0].equalsIgnoreCase("help")) {
								player.sendMessage(" -- " + GlobalTags.LOCKDOWN + " --");
								player.sendMessage("�7�o/lockdown ->�r Show all current lockdowns.");
								player.sendMessage("�7�o/lockdown help ->�r Displays command list.");
								player.sendMessage("�7�o/lockdown all->�r Display the entire lockdown history.");
								player.sendMessage("�7�o/lockdown start <cell> ->�r Starts a lockdown in <cell>.");
								player.sendMessage("�7�o/lockdown stop <cell> ->�r Ends a lockdown in <cell>");
								player.sendMessage("�8�lCells:�r A, B, C, D");
							}
							
							else if (args[0].equalsIgnoreCase("all")) {
								List<IGLockdown> currentLockdowns = IGLockdownFactory.getAllLockdowns();
								for (IGLockdown lockdown : currentLockdowns) {
									player.sendMessage("�8[[");
									player.sendMessage("�eCell: �f" + lockdown.getCell().getLabel());
									player.sendMessage("�eStarted By: �f" + lockdown.getStartPlayer().getName());
									player.sendMessage("�eStarted At: �f" + lockdown.getStartedFriendly());
									player.sendMessage("�8]]"); 
								}
							}
							
						}
						
						else if (args.length == 2) {
							if (args[0].equalsIgnoreCase("start")) {
								IGCells cell = IGCells.getCell(args[1]);
								//Make sure the cell is not under lockdown already.
								if (!IGLockdownFactory.isCellInLockdown(cell)) {
									//Add the lockdown.
									IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
									IGLockdownFactory.add(cell.toCell(), igPlayer);
									IGRankNodes igRank = IGRankNodes.valueOf(args[1].toUpperCase());
									//Deny PVP
									Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "region flag " + cell.getLabel() + "_block_pvp pvp -w world deny");
									Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), 
											"region flag " + cell.getLabel() + "_block_pvp greeting -w world " + igRank.getTag() + "&b&lLockdown!");
									
									Bukkit.broadcastMessage(GlobalTags.LOCKDOWN + igRank.getTag() + "�c�lBLOCK under lockdown!");
								} else {
									player.sendMessage(GlobalTags.LOCKDOWN + args[1].toUpperCase() + " �4�lBLOCK is already under lockdown!");
								}
							}
							
							if (args[0].equalsIgnoreCase("stop") || args[0].equalsIgnoreCase("end")) {
								IGCells cell = IGCells.getCell(args[1]);
								
								//Make sure the cell is under lockdown already.
								if (IGLockdownFactory.isCellInLockdown(cell)) {
									IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
									List<IGLockdown> currentLockdowns = IGLockdownFactory.getCurrentLockdowns();
									//Go through all of the current lockdowns and find the cell... Then... Add an "end".
									for (IGLockdown lockdown : currentLockdowns) {
										if (lockdown.getCell().getLabel().equalsIgnoreCase(cell.getLabel())) {
											//Allow PVP
											IGRankNodes igRank = IGRankNodes.valueOf(args[1].toUpperCase());
											Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "region flag " + cell.getLabel() + "_block_pvp pvp -w world allow");
											Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), 
													"region flag " + cell.getLabel() + "_block_pvp greeting -w world " + igRank.getTag() + "&4&lPvP Enabled");
											lockdown.setEndId(igPlayer.getId());
											lockdown.setEnded(DateConverter.getCurrentTime());
											lockdown.save();
											Bukkit.broadcastMessage(GlobalTags.LOCKDOWN + igRank.getTag() + "�a�lBLOCK cleared lockdown!");
										}
									}
								} else {
									player.sendMessage(GlobalTags.LOCKDOWN + "�4Cell " + args[1].toUpperCase() + " is not under lockdown.");
								}
							}
						} else {
							player.sendMessage(GlobalMessages.NO_PERMISSIONS);
						}
					}
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	
}
