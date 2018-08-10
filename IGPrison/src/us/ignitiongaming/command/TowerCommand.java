package us.ignitiongaming.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalMessages;
import us.ignitiongaming.enums.IGLocations;

public class TowerCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (lbl.equalsIgnoreCase("tower")) {
				
				if (args.length == 0) {
					player.sendMessage("Usage: /tower <a/b/c/d>");
				} else if (args.length == 1) {
					String entry = args[0].toLowerCase();
					Location tower = IGLocations.getLocationByLabel(entry + "_block_tower").toLocation();
					player.teleport(tower);					
				} else {
					player.sendMessage(GlobalMessages.INVALID_COMMAND);
				}
			}
			
		}
		return false;
	}

}
