package us.ignitiongaming.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalMessages;

public class AdminCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				// [/iga]
				if (lbl.equalsIgnoreCase("iga")) {
					
					if (player.hasPermission("igprison.staff")) {
						
						// [/iga help]
						if (args.length == 1) {
							if (args[0].equalsIgnoreCase("help")) {
								
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
