package us.ignitiongaming.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.ignitiongaming.singleton.IGSingleton;

public class ClockInOutCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (lbl.equalsIgnoreCase("clockin")) {
					IGSingleton.getInstance().clockIn(player.getUniqueId());
				}
				if (lbl.equalsIgnoreCase("clockout")) {
					IGSingleton.getInstance().clockOut(player.getUniqueId());
				}			
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
}
