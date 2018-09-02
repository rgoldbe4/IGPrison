package us.ignitiongaming.command;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalMessages;
import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.database.ConvertUtils;
import us.ignitiongaming.entity.other.IGLocation;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerSpawn;
import us.ignitiongaming.entity.player.IGPlayerStats;
import us.ignitiongaming.enums.IGCells;
import us.ignitiongaming.enums.IGLocations;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.factory.lockdown.IGLockdownFactory;
import us.ignitiongaming.factory.other.IGLocationFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerSpawnFactory;
import us.ignitiongaming.factory.player.IGPlayerStatsFactory;
import us.ignitiongaming.util.convert.DateConverter;

public class TeleportCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				// [/spawn]
				if (lbl.equalsIgnoreCase("spawn")) {
					IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
					//Determine if the player's spawn command is on cooldown.
					//This is exempt for staff and free players.
					IGRankNodes playerRank = IGRankNodes.getPlayerRank(player);
					Location location = IGLocationFactory.getSpawnByPlayerRank(player).toLocation();
					
					if (playerRank == IGRankNodes.SOLITARY) {
						player.sendMessage(GlobalTags.SOLITARY + "§4This command is disabled while in solitary.");
					} else if (playerRank == IGRankNodes.FREE || playerRank.isStaff()) {
						player.teleport(location);
					} else {
						//Determine if the command is on cooldown
						IGPlayerSpawn playerSpawn = IGPlayerSpawnFactory.getSpawnByPlayer(igPlayer);
						
						if (playerSpawn.isValid()) {
							if (playerSpawn.isAvailable()) {
								player.sendMessage(GlobalTags.LOGO + "§cYour /spawn command is now on cooldown for §l1 hour§r§c.");
								playerSpawn.setCooldown();
								playerSpawn.save();
								player.teleport(location);
							} else {
								//Determine if player has defiance points to spend.
								IGPlayerStats igPlayerStats = IGPlayerStatsFactory.getIGPlayerStatsByIGPlayer(igPlayer);
								if (igPlayerStats.getDonatorPoints() > 0) {
									igPlayerStats.removeDonatorPoint();
									igPlayerStats.save();
									player.sendMessage(GlobalTags.LOGO + "§dYou used one (1) " + GlobalTags.DEFIANCE + "§5Points §dto override your /spawn cooldown.");
									player.teleport(location);
								} else {
									player.sendMessage(GlobalTags.LOGO + "§4On cooldown! §cAvailable in: §f" + DateConverter.compareDatesToNowFriendly(playerSpawn.getCooldown()));
								}
							}
						} else {
							//Not available, so add them to the database.
							IGPlayerSpawnFactory.add(igPlayer);
							player.sendMessage(GlobalTags.LOGO + "§cYour /spawn command is now on cooldown for §l1 hour§r§c.");
							player.teleport(location);
						}
					}
				}
				
				// [/warp <label>]
				if (lbl.equalsIgnoreCase("igwarp")) {
					if (args.length == 1) {
						IGLocations loc = IGLocations.getLocationByLabel(args[0]);
						Bukkit.getLogger().log(Level.INFO, IGLocationFactory.getLocationByIGLocations(loc).toLocation().toString());
						player.teleport(IGLocationFactory.getLocationByIGLocations(loc).toLocation());
					}
					if (args.length == 0) player.sendMessage("Available warps: " + ConvertUtils.getStringFromCommand(0, IGLocationFactory.getAllLocations().toArray(new String[0])));
				}
				
				// [/setspawn]
				if (lbl.equalsIgnoreCase("setspawn")) {
					Location plLoc = player.getLocation();
					IGLocation igLoc = IGLocationFactory.getLocationByIGLocations(IGLocations.SPAWN);
					igLoc.fromLocation(plLoc);
					igLoc.save();
				}
				
				// [/goto <player>]
				if (lbl.equalsIgnoreCase("goto")) {
					
					if (args.length == 1) {
						Player target = Bukkit.getPlayer(args[0]);
						if (target != null) {
							player.teleport(target);
							target.sendMessage(player.getName() + " has teleported to you.");
							player.sendMessage("You have teleported to " + target.getName());
						} else {
							player.sendMessage("The player you request was offline.");
						}
					} else {
						player.sendMessage(GlobalMessages.INVALID_COMMAND);
					}
				}
				
				if (lbl.equalsIgnoreCase("bring")) {
					
					if (args.length > 0) {
						for (String arg : args) {
							Player target = Bukkit.getPlayer(arg);
							if (target != null) {
								target.teleport(player);
								target.sendMessage(player.getName() + " has teleported you to them!");
							} else {
								player.sendMessage("The player, "  + arg + ", is offline.");
							}
						}
					}
				}
				
				if (lbl.equalsIgnoreCase("visit")) {
					
					if (args.length == 1) {
						//Determine if cell block is in lockdown
						if (IGCells.isCell(args[0])) {
							IGCells cell = IGCells.getCell(args[0]);
							
							if (IGLockdownFactory.isCellInLockdown(cell)) {
								player.sendMessage(GlobalTags.LOGO + cell.getTag() + "§cis currently under lockdown. Try again later.");
							} else {
								player.teleport(IGLocations.getLocationByLabel(cell.getLabel() + "_visitation").toLocation());
								player.sendMessage(GlobalTags.LOGO + "You are now visiting " + cell.getTag() + ".");
							}
						} else {
							player.sendMessage(GlobalTags.LOGO + "You did not enter in the right cell. Try A, B, C, or D.");
						}
						
					} else {
						player.sendMessage(GlobalTags.LOGO + "Usage: /visit <a/b/c/d>");
						player.sendMessage(GlobalTags.LOGO + "§cYou may not visit cell blocks in lockdown.");
					}
				}
				
				if (lbl.equalsIgnoreCase("igsetwarp")) {
					if (args.length == 1) {
						IGLocations loc = IGLocations.getLocationByLabel(args[0]);
						Location location = player.getLocation();
						IGLocation warp = IGLocationFactory.getLocationByIGLocations(loc);
						warp.fromLocation(location);
						warp.save();
						player.sendMessage(GlobalTags.LOGO + "The warp, " + loc.getLabel().toUpperCase() + ", has been updated to your coordinates.");
					}
					if (args.length == 0 || args.length > 1) player.sendMessage("Available warps: " + ConvertUtils.getStringFromCommand(0, IGLocationFactory.getAllLocations().toArray(new String[0])));
				}
			
				
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
