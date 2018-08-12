package us.ignitiongaming.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.config.ServerDefaults;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.enums.IGSettings;
import us.ignitiongaming.factory.other.IGLocationFactory;
import us.ignitiongaming.util.convert.CurrencyConverter;

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
						if ( playerBalance >= rankupCostD ) {
							Bukkit.broadcastMessage(GlobalTags.RANKUP + player.getName() + " has ranked up to " + IGRankNodes.C.getTag());
							player.sendMessage("§8Your balance is now: §a$" + (playerBalance - rankupCostD));
							Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " group set c");
							ServerDefaults.econ.withdrawPlayer(player, rankupCostD);
							player.setPlayerListName(IGRankNodes.getPlayerFormatting(player));
							Location location = IGLocationFactory.getSpawnByPlayerRank(player).toLocation();
							player.teleport(location);
						} else {
							player.sendMessage(GlobalTags.RANKUP + "§cYou need §a" + CurrencyConverter.convertToCurrency(rankupCostD - playerBalance) + "§c to rankup.");
						}
						break;
					case C:
						double rankupCostC = Double.parseDouble(IGSettings.RANKUP_C.toSetting().getValue().toString());
						if ( playerBalance >= rankupCostC ) {
							Bukkit.broadcastMessage(GlobalTags.RANKUP + player.getName() + " has ranked up to " + IGRankNodes.B.getTag());
							player.sendMessage("§8Your balance is now: §a$" + (playerBalance - rankupCostC));
							Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " group set b");
							ServerDefaults.econ.withdrawPlayer(player, rankupCostC);
							player.setPlayerListName(IGRankNodes.getPlayerFormatting(player));
							Location location = IGLocationFactory.getSpawnByPlayerRank(player).toLocation();
							player.teleport(location);
						} else {
							player.sendMessage(GlobalTags.RANKUP + "§cYou need §a" + CurrencyConverter.convertToCurrency(rankupCostC - playerBalance) + "§c to rankup.");
						}
						break;
					case B:
						double rankupCostB = Double.parseDouble(IGSettings.RANKUP_B.toSetting().getValue().toString());
						if ( playerBalance >= rankupCostB ) {
							Bukkit.broadcastMessage(GlobalTags.RANKUP + player.getName() + " has ranked up to " + IGRankNodes.A.getTag());
							player.sendMessage("§8Your balance is now: §a$" + (playerBalance - rankupCostB));
							Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " group set a");
							ServerDefaults.econ.withdrawPlayer(player, rankupCostB);
							player.setPlayerListName(IGRankNodes.getPlayerFormatting(player));
							Location location = IGLocationFactory.getSpawnByPlayerRank(player).toLocation();
							player.teleport(location);
						} else {
							player.sendMessage(GlobalTags.RANKUP + "§cYou need §a" + CurrencyConverter.convertToCurrency(rankupCostB - playerBalance) + "§c to rankup.");
						}
						break;
					case A:
						double rankupCostA = Double.parseDouble(IGSettings.RANKUP_A.toSetting().getValue().toString());
						if ( playerBalance >= rankupCostA ) {
							Bukkit.broadcastMessage(GlobalTags.RANKUP + player.getName() + " has ranked up to " + IGRankNodes.FREE.getTag());
							player.sendMessage("§8Your balance is now: §a" + (playerBalance - rankupCostA));
							Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " group set free");
							ServerDefaults.econ.withdrawPlayer(player, rankupCostA);
							player.setPlayerListName(IGRankNodes.getPlayerFormatting(player));
							Location location = IGLocationFactory.getSpawnByPlayerRank(player).toLocation();
							player.teleport(location);
						} else {
							player.sendMessage(GlobalTags.RANKUP + "§cYou need §a$" + CurrencyConverter.convertToCurrency(rankupCostA - playerBalance) + "§c to rankup.");
						}
						break;
					case FREE:
					case GUARD:
					case WARDEN:
					case STAFF:
					case SOLITARY:
					default:
						player.sendMessage(GlobalTags.RANKUP + "§4You cannot rankup any higher.");
						break;
					}
				}
				
				// [/setrank]
				if (lbl.equalsIgnoreCase("setrank")) {
					if (args.length == 2) {
						Player target = Bukkit.getPlayer(args[0]);
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + args[0] + " group set " + args[1]);
						if (target != null) {
							player.sendMessage(GlobalTags.LOGO + target.getName() + " has been added to " + IGRankNodes.getPlayerRank(target).getTag());
							target.setPlayerListName(IGRankNodes.getPlayerFormatting(target));
						} else {
							player.sendMessage(GlobalTags.LOGO + "The player, " + args[0] + ", has been added to §8" + args[1].toUpperCase() + "§f.");
						}
					} else {
						displayRanks(player);
					}
				}
				
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	private void displayRanks(Player player) {
		StringBuilder builder = new StringBuilder();
		builder.append("Ranks: ");
		for (IGRankNodes rank : IGRankNodes.values()) {
			builder.append(rank.getTag() + " ");
		}
		
		player.sendMessage(builder.toString());
	}

}
