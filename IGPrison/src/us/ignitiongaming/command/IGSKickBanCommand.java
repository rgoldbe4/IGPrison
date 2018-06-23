package us.ignitiongaming.command;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.database.ConvertUtils;
import us.ignitiongaming.entity.rank.IGRank;
import us.ignitiongaming.enums.IGRanks;
import us.ignitiongaming.factory.player.IGPlayerBannedFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerKickedFactory;
import us.ignitiongaming.factory.rank.IGRankFactory;
import us.ignitiongaming.util.convert.DateConverter;

public class IGSKickBanCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		// TODO Auto-generated method stub
		try{
		if (sender instanceof Player) {
			Player kicker = (Player) sender;
			if(lbl.contains("ban") && args.length >= 3){
				Player kicked = Bukkit.getPlayer(args[0]);
				String reason = ConvertUtils.getStringFromCommand(2, args);
				Date startDate = DateConverter.getCurrentTime();
				Date endDate = DateConverter.getCurrentTime();
				
				//Convert
				endDate = DateConverter.convertSingleArgumentContextToDate(args[1]);
				
				IGPlayerBannedFactory.add(IGPlayerFactory.getIGPlayerByPlayer(kicked), startDate, endDate, reason, IGPlayerFactory.getIGPlayerByPlayer(kicker));
				kicked.kickPlayer(" banned for " + DateConverter.compareDatesFriendly(startDate, endDate) + " for " + reason);
				if (lbl.contains("s")){
					IGRank staff = IGRankFactory.getIGRankByRank(IGRanks.STAFF);
					IGRank guard = IGRankFactory.getIGRankByRank(IGRanks.STAFF);
					IGRank warden = IGRankFactory.getIGRankByRank(IGRanks.STAFF);
					for ( Player online : Bukkit.getOnlinePlayers() ){
						if(online.hasPermission(staff.getNode()) || online.hasPermission(guard.getNode()) || online.hasPermission(warden.getNode())) 
							online.sendMessage("§b§l" + kicker.getName() + " banned " + kicked.getName() + " for " + DateConverter.compareDatesFriendly(startDate, endDate) + " for " + reason);
					}
				}
				else{
					for ( Player online : Bukkit.getOnlinePlayers() ){
							online.sendMessage("§b§l" + kicker.getName() + " banned " + kicked.getName() + " for " + DateConverter.compareDatesFriendly(startDate, endDate) + " for " + reason);
					}
				}
			}
			if(lbl.contains("kick") && args.length >= 2){
				Player kicked = Bukkit.getPlayer(args[0]);
				String reason =  ConvertUtils.getStringFromCommand(1, args);
				String message = kicker.getName() + " kicked" + kicked.getName() + " for " + reason;
				kicked.kickPlayer("kicked for " + reason);
				IGPlayerKickedFactory.add(IGPlayerFactory.getIGPlayerByPlayer(kicked), args[1], IGPlayerFactory.getIGPlayerByPlayer(kicker));
				if (lbl.contains("s")){
					IGRank staff = IGRankFactory.getIGRankByRank(IGRanks.STAFF);
					IGRank guard = IGRankFactory.getIGRankByRank(IGRanks.STAFF);
					IGRank warden = IGRankFactory.getIGRankByRank(IGRanks.STAFF);
					for ( Player online : Bukkit.getOnlinePlayers() ){
						if(online.hasPermission(staff.getNode()) || online.hasPermission(guard.getNode()) || online.hasPermission(warden.getNode())) 
							online.sendMessage(message);
					}
				}
				else{
					for ( Player online : Bukkit.getOnlinePlayers() ){
							online.sendMessage(message);
					}
				}
			}
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}


}
