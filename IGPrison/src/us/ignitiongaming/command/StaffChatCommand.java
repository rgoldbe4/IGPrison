package us.ignitiongaming.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.database.ConvertUtils;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.singleton.IGList;

public class StaffChatCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (lbl.equalsIgnoreCase("sc") || lbl.equalsIgnoreCase("staffchat")) {
					if (args.length != 0){
						for ( Player online : Bukkit.getOnlinePlayers() ){
							boolean hasStaff = IGRankNodes.getPlayerRank(player).isStaff();
							if (hasStaff) 
								online.sendMessage("§b§l" + player.getName() + "§r§b: " + ConvertUtils.getStringFromCommand(0, args));
						}
					}
					else {
						if (IGList.staffChat.contains(player)) IGList.staffChat.remove(player);
						else IGList.staffChat.add(player);
						player.sendMessage("§b§lToggled Staff Chat");
					}
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
}
