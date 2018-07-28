package us.ignitiongaming.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.singleton.IGList;

public class StaffCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				
				if (lbl.equalsIgnoreCase("guard")) {
					if (player.hasPermission(IGRankNodes.GUARD.getNode())) {
						if (IGList.clockedIn.contains(player)) {
							IGList.clockedIn.remove(player);
							player.sendMessage(GlobalTags.LOGO + "You have been clocked out.");
						} else {
							IGList.clockedIn.add(player);
							player.sendMessage(GlobalTags.LOGO + "You have been clocked in.");
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
