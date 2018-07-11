package us.ignitiongaming.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.entity.gang.IGGang;
import us.ignitiongaming.entity.gang.IGPlayerGang;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.factory.gang.IGGangFactory;
import us.ignitiongaming.factory.gang.IGPlayerGangFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;

public class GangCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				
				if (lbl.equalsIgnoreCase("gang")) {
					IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
					boolean isPlayerInGang = IGPlayerGangFactory.isPlayerInGang(igPlayer);
					
					// [/gang] Display info on gang of current member
					if (args.length == 0) {
						
						if (isPlayerInGang) {
							getGangInformation(player, igPlayer);
						} else {
							
						}
					}
					
				}
				
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	private void getGangInformation(Player player, IGPlayer igPlayer) {
		//Get the player's current PlayerGang information to get a Gang.
		IGPlayerGang playerGang = IGPlayerGangFactory.getPlayerGangFromPlayer(igPlayer);
		IGGang gang = IGGangFactory.getGangById(playerGang.getGangID());
		List<IGPlayerGang> playersInGang = IGPlayerGangFactory.getPlayersInGang(gang.getId());
		
		player.sendMessage("=== { " + gang.getName().toUpperCase() + " } ===");
		player.sendMessage("Money Pot: $" + gang.getMoney());
		player.sendMessage("Defiance Points: " + gang.getPoints());
		player.sendMessage("-----------------------");
		
		//Go through each gang and find out their name and rank... Sort by ranks (Leader > Officer > Member) in query.
		for (IGPlayerGang playerInGang : playersInGang) {
			IGPlayer igPlayerInGang = IGPlayerFactory.getIGPlayerById(playerInGang.getPlayerID());
			player.sendMessage(playerInGang.getGangRank().getLabel() + " " + igPlayerInGang.getName());
		}
		
	}

}
