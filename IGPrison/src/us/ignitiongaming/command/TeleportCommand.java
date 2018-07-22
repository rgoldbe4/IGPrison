package us.ignitiongaming.command;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalMessages;
import us.ignitiongaming.database.ConvertUtils;
import us.ignitiongaming.entity.other.IGLocation;
import us.ignitiongaming.enums.IGLocations;
import us.ignitiongaming.factory.other.IGLocationFactory;
import us.ignitiongaming.util.handy.FacingDirection;

public class TeleportCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				// [/spawn]
				if (lbl.equalsIgnoreCase("spawn")) {
					//-- TO DO --
					//Teleport based on rank, rather than the default spawn.
					Location location = IGLocationFactory.getSpawnByPlayerRank(player).toLocation();
					location.setYaw(FacingDirection.EAST);
					player.teleport(location);
				}
				
				// [/warp <label>]
				if (lbl.equalsIgnoreCase("warp")) {
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
				
			
				
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
