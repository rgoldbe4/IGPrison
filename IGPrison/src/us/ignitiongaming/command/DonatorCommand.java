package us.ignitiongaming.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalMessages;
import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerStats;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerStatsFactory;

public class DonatorCommand implements CommandExecutor{
	// change this to required points and the claim donator function should work.
	private static final int REQUIRED_POINTS = Integer.MAX_VALUE;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				// [/donate]
				if (lbl.equalsIgnoreCase("donate")) {
					player.sendMessage(GlobalTags.DONATION + "�8URL: �7�o�nhttp://www.ignitiongaming.us/donate�r");
				}
				
				// [/claimdonator]
				if (lbl.equalsIgnoreCase("claimdonator")) {
					IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
					IGPlayerStats igStats = IGPlayerStatsFactory.getIGPlayerStatsByIGPlayer(igPlayer);
					if(igStats.getDonatorPoints() >= REQUIRED_POINTS){
						//add function to set to donator
						player.sendMessage(GlobalTags.DONATION + "Congratulations! You have achieved the rank of donator");
					}
					else player.sendMessage(GlobalTags.DONATION + "You have not achieved the required ammount of donator points. You have " + igStats.getDonatorPoints() + " points but you need " + REQUIRED_POINTS + " points");
					player.sendMessage(GlobalTags.DONATION + GlobalMessages.UNDER_CONSTRUCTION);
				}
				
				// [/points]
				if (lbl.equalsIgnoreCase("points") || lbl.equalsIgnoreCase("donatorpoints")) {
					IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
					IGPlayerStats igStats = IGPlayerStatsFactory.getIGPlayerStatsByIGPlayer(igPlayer);
					player.sendMessage(GlobalTags.DONATION + " Current Points: " + igStats.getDonatorPoints());
				}
				
				
			}
		} catch (Exception ex) {
			
		}
		return false;
	}

}
