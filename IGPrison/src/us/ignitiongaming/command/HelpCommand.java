package us.ignitiongaming.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalMessages;
import us.ignitiongaming.util.convert.DateConverter;

public class HelpCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				// [/ighelp]
				if (lbl.equalsIgnoreCase("ighelp")) {
					if (player.hasPermission("igprison.ighelp")) {
						player.sendMessage(GlobalMessages.UNDER_CONSTRUCTION);
					}
				}
				
				// [/now] -> Display the time...
				if (lbl.equalsIgnoreCase("now")) {
					if (player.hasPermission("igprison.now")) {
						player.sendMessage("§eCurrent Time: §f" + DateConverter.toFriendlyDate(DateConverter.getCurrentTimeString()));
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
