package us.ignitiongaming.command;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.entity.rank.IGRank;
import us.ignitiongaming.enums.IGRanks;
import us.ignitiongaming.factory.player.IGPlayerBannedFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerKickedFactory;
import us.ignitiongaming.factory.rank.IGRankFactory;

public class IGSKickBanCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		// TODO Auto-generated method stub
		try{
		if (sender instanceof Player) {
			Player kicker = (Player) sender;
			Player kicked = Bukkit.getPlayer(args[0]);
			if(lbl.contains("ban") && args.length == 3){
				int days = 0;
				int hours = 0;
				int minutes = 0;
				int seconds = 0;
				long start  = System.currentTimeMillis();
				for(String s : args[1].split("|")){
					if(s.endsWith("d"))days =  Integer.parseInt(s.substring(0, s.length()-2));
					if(s.endsWith("h"))hours = Integer.parseInt(s.substring(0, s.length()-2));
					if(s.endsWith("m"))minutes = Integer.parseInt(s.substring(0, s.length()-2));
					if(s.endsWith("s"))seconds = Integer.parseInt(s.substring(0, s.length()-2));
				}
				long end = start + ((((days*24)+hours)*60+minutes)*60+seconds)*1000;
				Date startDate = new Date(start);
				Date endDate = new Date(end);
				IGPlayerBannedFactory.add(IGPlayerFactory.getIGPlayerByPlayer(kicked), startDate, endDate, args[2], IGPlayerFactory.getIGPlayerByPlayer(kicker));
				String message = kicker.getName() + " banned " + args[0] + " for " + days + " days, " +  hours + " hours, " + minutes + " minutes, and" + seconds + " seconds for " + args[2];
				if (lbl.contains("s")){
					IGRank staff = IGRankFactory.getIGRankByRank(IGRanks.STAFF);
					IGRank guard = IGRankFactory.getIGRankByRank(IGRanks.STAFF);
					IGRank warden = IGRankFactory.getIGRankByRank(IGRanks.STAFF);
					for ( Player online : Bukkit.getOnlinePlayers() ){
						if(online.hasPermission(staff.getNode()) || online.hasPermission(guard.getNode()) || online.hasPermission(warden.getNode())) 
							online.sendMessage("§b§l" +message);
					}
				}
				else{
					for ( Player online : Bukkit.getOnlinePlayers() ){
							online.sendMessage(message);
					}
				}
			}
			if(lbl.contains("kick") && args.length == 2){
				String message = kicker.getName() + " kicked" + args[0] + " for " + args[1];
				Bukkit.getPlayer(args[0]).kickPlayer("kicked for " + args[1]);
				IGPlayerKickedFactory.add(IGPlayerFactory.getIGPlayerByPlayer(kicked), args[2], IGPlayerFactory.getIGPlayerByPlayer(kicker));
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
