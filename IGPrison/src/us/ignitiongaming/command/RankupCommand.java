package us.ignitiongaming.command;

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
				}
				
				
			}
		} catch (Exception ex) {
			
		}
		return false;
	}

}
