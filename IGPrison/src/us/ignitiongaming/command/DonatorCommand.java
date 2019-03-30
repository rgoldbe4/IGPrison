package us.ignitiongaming.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerStats;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerStatsFactory;
import us.ignitiongaming.util.handy.InventoryMenu;

public class DonatorCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				// [/donate]
				if (lbl.equalsIgnoreCase("donate")) {
					player.sendMessage(GlobalTags.DONATION + "§8URL: §7§o§nhttp://ignitiongaming.heliohost.org/mc/donate§r");
				}
				
				if (lbl.equalsIgnoreCase("shop")) {
					player.openInventory(InventoryMenu.getMainMenu());
				}
				
				// [/points]
				if (lbl.equalsIgnoreCase("points")) {
					IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
					IGPlayerStats igStats = IGPlayerStatsFactory.getIGPlayerStatsByIGPlayer(igPlayer);
					player.sendMessage(GlobalTags.DEFIANCE_POINTS + igStats.getDonatorPoints());
				}
				
				if (lbl.equalsIgnoreCase("discord")) {
					player.sendMessage(GlobalTags.LOGO + "Join our Discord: §b§nhttps://discord.gg/HJwzmSM");
				}
				
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
