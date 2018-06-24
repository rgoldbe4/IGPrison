package us.ignitiongaming.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalMessages;
import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.config.ServerDefaults;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.enums.IGSettings;

public class RankupCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				// [/rankup]
				if (lbl.equalsIgnoreCase("rankup")) {
					double playerBalance = ServerDefaults.econ.getBalance(player);
					IGRankNodes playerRank = IGRankNodes.getPlayerRank(player);
					
					switch (playerRank) {
					case D:
						double rankupCostD = Double.parseDouble(IGSettings.RANKUP_D.toSetting().getValue().toString());
						if ( playerBalance >= rankupCostD) {
							Bukkit.broadcastMessage(GlobalTags.RANKUP + player.getName() + " has ranked up to " + IGRankNodes.C.getTag());
							player.sendMessage("§8Your balance is now: §a$" + (playerBalance - rankupCostD));
							Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " group set c");
						} else {
							player.sendMessage(GlobalTags.RANKUP + "§cYou need §a$" + (rankupCostD - playerBalance) + "§c to rankup.");
						}
						break;
					case C:
						double rankupCostC = Double.parseDouble(IGSettings.RANKUP_C.toSetting().getValue().toString());
						if ( playerBalance >= rankupCostC) {
							Bukkit.broadcastMessage(GlobalTags.RANKUP + player.getName() + " has ranked up to " + IGRankNodes.B.getTag());
							player.sendMessage("§8Your balance is now: §a$" + (playerBalance - rankupCostC));
							Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " group set b");
						} else {
							player.sendMessage(GlobalTags.RANKUP + "§cYou need §a$" + (rankupCostC - playerBalance) + "§c to rankup.");
						}
						break;
					case B:
						double rankupCostB = Double.parseDouble(IGSettings.RANKUP_B.toSetting().getValue().toString());
						if ( playerBalance >= rankupCostB) {
							Bukkit.broadcastMessage(GlobalTags.RANKUP + player.getName() + " has ranked up to " + IGRankNodes.A.getTag());
							player.sendMessage("§8Your balance is now: §a$" + (playerBalance - rankupCostB));
							Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " group set a");
						} else {
							player.sendMessage(GlobalTags.RANKUP + "§cYou need §a$" + (rankupCostB - playerBalance) + "§c to rankup.");
						}
						break;
					case A:
						double rankupCostA = Double.parseDouble(IGSettings.RANKUP_A.toSetting().getValue().toString());
						if ( playerBalance >= rankupCostA) {
							Bukkit.broadcastMessage(GlobalTags.RANKUP + player.getName() + " has ranked up to " + IGRankNodes.FREE.getTag());
							player.sendMessage("§8Your balance is now: §a$" + (playerBalance - rankupCostA));
							Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " group set free");
						} else {
							player.sendMessage(GlobalTags.RANKUP + "§cYou need §a$" + (rankupCostA - playerBalance) + "§c to rankup.");
						}
						break;
					case FREE:
					case GUARD:
					case WARDEN:
					case STAFF:
					case SOLITARY:
					default:
						player.sendMessage("You cannot rankup any higher.");
						break;
					}
				}
				
				// [/setrank]
				if (lbl.equalsIgnoreCase("setrank")) {
					if (args.length == 2) {
						if (player.hasPermission("igprison.staff")) {
							Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + args[0] + " group set " + args[1]);
							player.sendMessage(args[0] + " has been added to " + args[1]);
							
						} else {
							player.sendMessage(GlobalMessages.NO_PERMISSIONS);
						}
					}
				}
				
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
