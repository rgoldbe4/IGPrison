package us.ignitiongaming.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
					Location location = IGLocationFactory.getLocationByIGLocations(IGLocations.SPAWN).toLocation();
					location.setYaw(FacingDirection.EAST);
					player.teleport(location);
				}
				
				// [/warp <label>]
				if (lbl.equalsIgnoreCase("warp")) {
					if (args.length == 1) {
						IGLocations loc = IGLocations.getLocationByLabel(args[0]);
						player.teleport(IGLocationFactory.getLocationByIGLocations(loc).toLocation());
					}
				}
				
				// [/setspawn]
				if (lbl.equalsIgnoreCase("setspawn")) {
					Location plLoc = player.getLocation();
					IGLocation igLoc = IGLocationFactory.getLocationByIGLocations(IGLocations.SPAWN);
					igLoc.fromLocation(plLoc);
					igLoc.save();
				}
				
			
				
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
