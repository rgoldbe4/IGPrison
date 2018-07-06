package us.ignitiongaming.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalMessages;
import us.ignitiongaming.config.ServerDefaults;
import us.ignitiongaming.entity.other.IGSetting;

public class DevelopmentCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				// [/igdev]
				if (lbl.equalsIgnoreCase("igdev")) {
					
					if (player.hasPermission("igprison.staff")) {
						if (args.length == 1) {
							
							// [/igdev defaults]
							if (args[0].equalsIgnoreCase("defaults")) {
								for (IGSetting setting : ServerDefaults.settings) {
									player.sendMessage(setting.getLabel() + " | " + setting.getValue().toString());
								}
							}
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
