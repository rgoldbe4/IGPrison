package us.ignitiongaming.command;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.rank.IGRank;
import us.ignitiongaming.enums.IGRanks;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.rank.IGRankFactory;
import us.ignitiongaming.singleton.IGSingleton;

public class StaffChatCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				IGSingleton igs = IGSingleton.getInstance();
				Player player = (Player) sender;
				IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
				UUID playerID = igPlayer.getUUID();
				if(args.length == 1){
					IGRank staff = IGRankFactory.getIGRankByRank(IGRanks.STAFF);
					IGRank guard = IGRankFactory.getIGRankByRank(IGRanks.STAFF);
					IGRank warden = IGRankFactory.getIGRankByRank(IGRanks.STAFF);
					for (Player player2 : Bukkit.getOnlinePlayers()){
						if(player2.hasPermission(staff.getNode()))player2.sendMessage("§a[StaffChat] §r" + igPlayer.getName() + ": " + args[0]);
						if(player2.hasPermission(guard.getNode()))player2.sendMessage("§a[StaffChat] §r" + igPlayer.getName() + ": " + args[0]);
						if(player2.hasPermission(warden.getNode()))player2.sendMessage("§a[StaffChat] §r" + igPlayer.getName() + ": " + args[0]);	
					}
				}
				else{
					igs.toggleStaffChatter(playerID);
				}
			}
		} catch (Exception ex) {
			
		}
		return false;
	}
	
}
