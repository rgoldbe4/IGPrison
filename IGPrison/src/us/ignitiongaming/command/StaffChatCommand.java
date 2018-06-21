package us.ignitiongaming.command;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.database.ConvertUtils;
import us.ignitiongaming.entity.rank.IGRank;
import us.ignitiongaming.enums.IGRanks;
import us.ignitiongaming.factory.rank.IGRankFactory;
import us.ignitiongaming.singleton.IGSingleton;

public class StaffChatCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				IGSingleton igs = IGSingleton.getInstance();
				Player player = (Player) sender;
				UUID playerID = player.getUniqueId();
				if (lbl.equalsIgnoreCase("sc") || lbl.equalsIgnoreCase("staffchat")) {
					if (args.length != 0){
						IGRank staff = IGRankFactory.getIGRankByRank(IGRanks.STAFF);
						IGRank guard = IGRankFactory.getIGRankByRank(IGRanks.STAFF);
						IGRank warden = IGRankFactory.getIGRankByRank(IGRanks.STAFF);
						for ( Player online : Bukkit.getOnlinePlayers() ){
							if(online.hasPermission(staff.getNode()) || online.hasPermission(guard.getNode()) || online.hasPermission(warden.getNode())) 
								online.sendMessage("§b§l" + player.getName() + "§r§b: " + ConvertUtils.getStringFromCommand(0, args));
						}
					}
					else {
						igs.toggleStaffChatter(playerID);
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
