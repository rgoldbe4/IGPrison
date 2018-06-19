package us.ignitiongaming.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalMessages;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerStats;
import us.ignitiongaming.entity.rank.IGRank;
import us.ignitiongaming.enums.IGRanks;
import us.ignitiongaming.factory.player.IGPlayerDonatorFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerRankFactory;
import us.ignitiongaming.factory.player.IGPlayerStatsFactory;
import us.ignitiongaming.factory.rank.IGRankFactory;

public class AdminCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				// [/iga]
				if (lbl.equalsIgnoreCase("iga")) {
					//Grab the IGRank of Staff, Guard, and Warden
					IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
					IGRank staff = IGRankFactory.getIGRankByRank(IGRanks.STAFF);
					IGRank guard = IGRankFactory.getIGRankByRank(IGRanks.GUARD);
					IGRank warden = IGRankFactory.getIGRankByRank(IGRanks.WARDEN);
					IGRank playerRank = IGPlayerRankFactory.getIGPlayerRank(igPlayer);
					//Grab all of the nodes...
					boolean hasStaff = player.hasPermission(staff.getNode()) || playerRank.getId() == staff.getId();
					boolean hasGuard = player.hasPermission(guard.getNode()) || hasStaff || playerRank.getId() == guard.getId();
					boolean hasWarden = player.hasPermission(warden.getNode()) || hasStaff || playerRank.getId() == warden.getId();
					
					// [/iga help]
					if (args.length == 1) {
						if (args[0].equalsIgnoreCase("help")) {
							if (hasWarden || hasStaff || hasGuard) {
								player.sendMessage(GlobalMessages.UNDER_CONSTRUCTION);
							}
						}
						
						if (args[0].equalsIgnoreCase("pi")) {
							IGPlayerStats stats = IGPlayerStatsFactory.getIGPlayerStatsByIGPlayer(igPlayer);
							boolean isDonator = IGPlayerDonatorFactory.isIGPlayerDonator(igPlayer);
							player.sendMessage("Player ID: " + igPlayer.getId());
							player.sendMessage("Rank: " + playerRank.getTag());
							player.sendMessage("Kills: " + stats.getKills());
							player.sendMessage("Deaths: " + stats.getDeaths());
							player.sendMessage("Donator Points: " + stats.getDonatorPoints());
							player.sendMessage("Joined: " + stats.getJoinedFriendly());
							player.sendMessage("Last Login: " + stats.getLastLoginFriendly());
							player.sendMessage("Is Donator: " + isDonator);
							
						}
					}
					
				}
				
				
			}
		} catch (Exception ex) {
			
		}
		return false;
	}

}
