package us.ignitiongaming.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalMessages;

public class RankupCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				// [/rankup]
				if (lbl.equalsIgnoreCase("rankup")) {
					player.sendMessage(GlobalMessages.UNDER_CONSTRUCTION);
				}
				
				// [/setrank]
				if (lbl.equalsIgnoreCase("setrank")) {
					player.sendMessage(GlobalMessages.UNDER_CONSTRUCTION);
					if (args.length == 2) {
						if (player.hasPermission("igprison.staff")) {
							Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + args[0] + " group add " + args[1]);
							player.sendMessage(args[0] + " has been added to " + args[1]);
							
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
