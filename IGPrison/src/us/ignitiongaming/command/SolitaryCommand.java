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
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerSolitary;
import us.ignitiongaming.enums.IGLocations;
import us.ignitiongaming.factory.other.IGLocationFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerSolitaryFactory;
import us.ignitiongaming.util.convert.ChatConverter;
import us.ignitiongaming.util.convert.DateConverter;

public class SolitaryCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (lbl.equalsIgnoreCase("solitary")) {
					
					if (args.length == 0) {
						displayHelp(player);
					}
					else if (args.length == 1) {
						
						if (args[0].equalsIgnoreCase("help")) {
							displayHelp(player);
						}
						else if (args[0].equalsIgnoreCase("context")) {
							displayContext(player);
						}
						else if (args[0].equalsIgnoreCase("list")) {
							displayList(player);
						}
						else {
							player.sendMessage(GlobalMessages.INVALID_COMMAND);
						}
					}
					else if (args.length == 2) {
						if (args[0].equalsIgnoreCase("info")) {
							displayPlayer(player, args[1]);
						}
						else if (args[0].equalsIgnoreCase("remove")) {
							removePlayer(player, args[1]);
						} else {
							player.sendMessage(GlobalMessages.INVALID_COMMAND);
						}
					}
					else if (args.length == 3) {
						if (args[0].equalsIgnoreCase("add")) {
							addPlayer(player, args[1], args[2]);
						} else {
							player.sendMessage(GlobalMessages.INVALID_COMMAND);
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
	
	private void displayHelp(Player player) {
		player.sendMessage("----- " + GlobalTags.SOLITARY + " -----");
		player.sendMessage(" /solitary -> Shows this.");
		player.sendMessage(" /solitary help -> Shows this.");
		player.sendMessage(" /solitary context -> Display all context you can use for <duration> arguments.");
		player.sendMessage(" /solitary add <player> <duration> -> Add a player to solitary.");
		player.sendMessage(" /solitary remove <player> -> Remove a player from solitary.");
		player.sendMessage(" /solitary list -> Display all current players in solitary.");
		player.sendMessage(" /solitary info <player> -> Display in-depth information on a player.");
	}

	private void displayContext(Player player) {
		ChatConverter.clearPlayerChat(player);
		player.sendMessage("m = Minutes");
		player.sendMessage("M = Months");
		player.sendMessage("s = Seconds");
		player.sendMessage("d = Days");
		player.sendMessage("y = Years");
	}

	private void displayList(Player player) {
		List<IGPlayerSolitary> allPlayers = IGPlayerSolitaryFactory.getAllPlayersInSolitary();
		ChatConverter.clearPlayerChat(player);
		if (allPlayers.size() == 0) player.sendMessage(GlobalTags.SOLITARY + "§4There are no players currently in solitary.");
		for (IGPlayerSolitary playerSolitary : allPlayers) {
			IGPlayer currentPlayer = IGPlayerFactory.getIGPlayerById(playerSolitary.getPlayerId());
			String friendlyStart = playerSolitary.getStartFriendly();
			String friendlyEnd = playerSolitary.getEndFriendly();
			String durationContext = DateConverter.compareDatesFriendly(playerSolitary.getStartDate(), playerSolitary.getEndDate());
			
			player.sendMessage("§8§l[ §r§f" + currentPlayer.getName() + " §8§l]");
			player.sendMessage("§8Start: §7" + friendlyStart);
			player.sendMessage("§8Stop: §7" + friendlyEnd);
			player.sendMessage("§8Duration: §7" + durationContext);
		}
	}

	private void displayPlayer(Player player, String target) {
		IGPlayer igPlayer = IGPlayerFactory.getIGPlayerFromName(target);
		
		//Step 1: Determine if the player is valid.
		if (igPlayer.isValid()) {
			
			//Step 2: Determine if the player is in solitary.
			if (IGPlayerSolitaryFactory.isIGPlayerInSolitary(igPlayer)) {
				ChatConverter.clearPlayerChat(player);
				IGPlayerSolitary playerSolitary = IGPlayerSolitaryFactory.getIGPlayerInSolitary(igPlayer);
				String friendlyStart = playerSolitary.getStartFriendly();
				String friendlyEnd = playerSolitary.getEndFriendly();
				String durationContext = DateConverter.compareDatesFriendly(playerSolitary.getStartDate(), playerSolitary.getEndDate());
				
				player.sendMessage("§8§l[ §r§f" + igPlayer.getName() + " §8§l]");
				player.sendMessage("§8Start: §7" + friendlyStart);
				player.sendMessage("§8Stop: §7" + friendlyEnd);
				player.sendMessage("§8Duration: §7" + durationContext);
				
			} else {
				player.sendMessage(GlobalTags.SOLITARY + "§4" + igPlayer.getName() + " is not in solitary.");
			}
		} else {
			player.sendMessage(GlobalTags.SOLITARY + "§4The player, " + target + ", was not found.");
		}
	}

	private void removePlayer(Player player, String target) {
		IGPlayer igPlayer = IGPlayerFactory.getIGPlayerFromName(target);
		
		//Step 1: Determine if the player is valid
		if (igPlayer.isValid()) {
			
			//Step 2: Determine if the player is in solitary
			if (IGPlayerSolitaryFactory.isIGPlayerInSolitary(igPlayer)) {
				IGPlayerSolitary playerSolitary = IGPlayerSolitaryFactory.getIGPlayerInSolitary(igPlayer);
				
				//Logic: Remove the solitary, tp the player back to where they should be, and let them know they were freed by player.
				playerSolitary.setEnd(DateConverter.getCurrentTime());
				playerSolitary.save();
				
				player.sendMessage(GlobalTags.SOLITARY + "§aYou have freed §f" + igPlayer.getName() + "§a from solitary.");
				
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group solitary user remove " + igPlayer.getName());
				
				//Determine if the target is online.
				Player targetPlayer = Bukkit.getPlayer(igPlayer.getName());
				
				//Teleport them if they are.
				if (targetPlayer != null) targetPlayer.teleport(IGLocationFactory.getSpawnByPlayerRank(targetPlayer).toLocation());
				
				//Tell them they were freed.
				if (targetPlayer != null) targetPlayer.sendMessage(GlobalTags.SOLITARY + "§eYou were released from solitary by §f" + player.getName() + "§e.");
				
			} else {
				player.sendMessage(GlobalTags.SOLITARY + "§4" + igPlayer.getName() + " is not in solitary.");
			}
			
		} else {
			player.sendMessage(GlobalTags.SOLITARY + "§4The player, " + target + ", was not found.");
		}
		
	}

	private void addPlayer(Player player, String target, String context) {
		try {
			IGPlayer igTarget = IGPlayerFactory.getIGPlayerFromName(target);
			Player targetPlayer = Bukkit.getPlayer(target);
			Date end = DateConverter.convertSingleArgumentContextToDate(context);
			String duration = DateConverter.compareDatesFriendly(DateConverter.getCurrentTime(), end);
			
			//Step 1: Determine if target is legit.
			if (igTarget.isValid()) {
				
				//Step 2: Determine if they are in solitary.
				if (!IGPlayerSolitaryFactory.isIGPlayerInSolitary(igTarget)) {
					
					//Step 3: Well, add to DB and let both players know the situation.
					IGPlayerSolitaryFactory.add(igTarget, end);
					
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group solitary user add " + player.getName());
					
					if (targetPlayer != null) {
						targetPlayer.sendMessage(GlobalTags.SOLITARY + "§cYou were sent to solitary by §f" + player.getName() + "§c.");
						targetPlayer.sendMessage(GlobalTags.SOLITARY + "§fYou will be released in: §e" + duration);
					}
					
					player.sendMessage(GlobalTags.SOLITARY + "§aYou have sent §f" + igTarget.getName() + "§a to solitary for §f" + duration + "§f.");
					
					if (targetPlayer != null) targetPlayer.teleport(IGLocations.SOLITARY.toLocation());
					
				} else {
					player.sendMessage(GlobalTags.SOLITARY + "§4The player you specified is already in solitary.");
					player.sendMessage(GlobalTags.SOLITARY + "§4Run §f§o/solitary info " + igTarget.getName() + "§r§4 to view more info.");
				}
			} else {
				player.sendMessage(GlobalTags.SOLITARY + "§4The player, " + target + ", was not found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
	}
}
