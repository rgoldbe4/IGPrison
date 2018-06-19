package us.ignitiongaming.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerStats;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerStatsFactory;

public class DonatorCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				// [/donate]
				if (lbl.equalsIgnoreCase("donate")) {
					player.sendMessage("§8Donation URL: §l§o§ahttp://www.ignitiongaming.us/donate§r");
				}
				
				// [/claimdonator]
				if (lbl.equalsIgnoreCase("claimdonator")) {
					
				}
				
				// [/points]
				if (lbl.equalsIgnoreCase("points") || lbl.equalsIgnoreCase("donatorpoints")) {
					IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
					IGPlayerStats igStats = IGPlayerStatsFactory.getIGPlayerStatsByIGPlayer(igPlayer);
					player.sendMessage("§8[§5Donation§8] §rCurrent Points: " + igStats.getDonatorPoints());
				}
				
				
			}
		} catch (Exception ex) {
			
		}
		return false;
	}

}
