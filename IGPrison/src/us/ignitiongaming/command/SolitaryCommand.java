package us.ignitiongaming.command;

import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalMessages;
import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.entity.other.IGLocation;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerSolitary;
import us.ignitiongaming.enums.IGLocations;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.factory.other.IGLocationFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerSolitaryFactory;
import us.ignitiongaming.util.convert.DateConverter;

public class SolitaryCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				// [/solitary <add/remove> <name> <context...>]
				if (lbl.equalsIgnoreCase("solitary")) {
					
					/*if (args.length < 2) {
						player.sendMessage(GlobalTags.SOLITARY);
						player.sendMessage(" /solitary -> Shows this.");
						player.sendMessage(" /solitary help -> Shows this.");
						player.sendMessage(" /solitary context -> Display all context you can use for <duration> arguments.");
						player.sendMessage(" /solitary add <player> <duration> -> Add a player to solitary.");
						player.sendMessage(" /solitary remove <player> -> Remove a player from solitary.");
						player.sendMessage(" /solitary list -> Display all current players in solitary.");
						player.sendMessage(" /solitary info <player> -> Display in-depth information on a player.");
					*/
					if (args.length < 2) {
						player.sendMessage(GlobalMessages.INVALID_COMMAND);
					} else {
						String addOrRemove = args[0];
						String playerName = args[1];
						Player pl = Bukkit.getPlayer(playerName);
						//Step 1: Determine if the player is already in solitary.
						if (pl != null) {
							IGPlayer igPl = IGPlayerFactory.getIGPlayerByPlayer(pl);
							boolean isPlayerInSolitary = IGPlayerSolitaryFactory.isIGPlayerInSolitary(igPl);
							//Determine if add or remove.
							if (!addOrRemove.equalsIgnoreCase("remove")) {
								if (!isPlayerInSolitary) {
									//Step 2: Add up all of the time contexts.
									Date end = DateConverter.getCurrentTime();
									for (int i = 2; i < args.length; i++) {
										end = DateConverter.addTimeFromString(end, args[i]);
									}
									
									//Step 3: Add them to the database.
									IGPlayerSolitaryFactory.add(igPl, end);
									
									//Step 4: Teleport them to solitary.
									IGLocation igLocation = IGLocationFactory.getLocationByIGLocations(IGLocations.SOLITARY);
									pl.teleport(igLocation.toLocation());
									//Enhancement: Add the addition group of solitary.
									Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " add " + IGRankNodes.SOLITARY.getNode());
									pl.sendMessage(GlobalTags.SOLITARY + "§cYou were sent to solitary!");
									pl.sendMessage("§cYou will be let out: §f" + DateConverter.toFriendlyDate(end));
									Bukkit.broadcastMessage(GlobalTags.SOLITARY + "§a" + args[1] + " §fwas put in solitary until §a" + DateConverter.toFriendlyDate(end) + "§f.");
									
								} else {
									//Player is already in solitary. Cannot add them.
									player.sendMessage(GlobalTags.SOLITARY + "§4Player is already in solitary. Failed to add them.");
								}
								
							} else {
								//Remove them from solitary
								if (isPlayerInSolitary) {
									IGPlayerSolitaryFactory.remove(igPl);
									pl.sendMessage(GlobalTags.SOLITARY + "§aYou were removed from solitary!");
									Bukkit.broadcastMessage(GlobalTags.SOLITARY + "§a" + args[1] + " §fwas removed from solitary.");
									Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " remove " + IGRankNodes.SOLITARY.getNode());
									//Teleport them back to the spawn area they deserve to be in
									player.teleport(IGLocationFactory.getSpawnByPlayerRank(pl).toLocation());
								} else {
									player.sendMessage(GlobalTags.SOLITARY + "§4Player is not in solitary. Failed to remove them.");
								}
							}
						} else {
							//Player is offline. Look them up based on IGPlayer. Add later.
							player.sendMessage(GlobalMessages.UNDER_CONSTRUCTION);
						}
						
					}
				}
				
				// [/solitarylist]
				if (lbl.equalsIgnoreCase("solitarylist")) {
					player.sendMessage("-- " + GlobalTags.SOLITARY + " --");
					List<IGPlayerSolitary> playersInSolitary = IGPlayerSolitaryFactory.getAllPlayersInSolitary();
					if (playersInSolitary.size() == 0) player.sendMessage("§4No players in solitary.");
					for (IGPlayerSolitary solitary : playersInSolitary) {
						IGPlayer igPlayer = IGPlayerFactory.getIGPlayerById(solitary.getPlayerId());
						String duration = DateConverter.compareDatesFriendly(solitary.getStartDate(), solitary.getEndDate());
						player.sendMessage("§8[[");
						player.sendMessage("§eName:  §f" + igPlayer.getName());
						player.sendMessage("§aStart: §f" + solitary.getStartFriendly());
						player.sendMessage("§cEnd:   §f" + solitary.getEndFriendly());
						player.sendMessage("§7Duration:§f " + duration);
						player.sendMessage("§8]]");
								
					}
				}
				
			
				
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
