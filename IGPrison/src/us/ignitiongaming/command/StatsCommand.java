package us.ignitiongaming.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.config.ServerDefaults;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerStats;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.enums.IGSettings;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerStatsFactory;
import us.ignitiongaming.util.convert.CurrencyConverter;
import us.ignitiongaming.util.handy.ScoreboardAnnouncer;

public class StatsCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (lbl.equalsIgnoreCase("ig")) {
				IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
				IGPlayerStats stats = IGPlayerStatsFactory.getIGPlayerStatsByIGPlayer(igPlayer);
				IGRankNodes rank = IGRankNodes.getPlayerRank(player);
				
				ScoreboardAnnouncer scoreboard = new ScoreboardAnnouncer(player);
				scoreboard.setTitle(GlobalTags.LOGO);
				if (rank.isInJail()) {
					double rankup = 0;
					switch (rank) {
						default:
							break;
						case D:
							rankup = Double.parseDouble(IGSettings.RANKUP_D.toSetting().getValue().toString());
							break;
						case C:
							rankup = Double.parseDouble(IGSettings.RANKUP_C.toSetting().getValue().toString());
							break;
						case B:
							rankup = Double.parseDouble(IGSettings.RANKUP_B.toSetting().getValue().toString());
							break;
						case A:
							rankup = Double.parseDouble(IGSettings.RANKUP_A.toSetting().getValue().toString());
							break;
					}
					
					scoreboard.addLine("§bRankup §7>> §2" + CurrencyConverter.convertToCurrency(rankup - ServerDefaults.econ.getBalance(player)));
					scoreboard.addSpacer();
				}
				scoreboard.addLine("§dPoints §7>> §f" + stats.getDonatorPoints());
				scoreboard.addLine("§4Deaths §7>> §f" + stats.getDeaths());
				scoreboard.addLine("§eKills §7>> §f" + stats.getKills());
				scoreboard.addLine("§2Money §7>> §2" + CurrencyConverter.convertToCurrency(ServerDefaults.econ.getBalance(player)));
				scoreboard.addSpacer();
				scoreboard.addLine(IGRankNodes.getPlayerFormatting(player));
				scoreboard.addTimer(5);
				scoreboard.hook();
			}
		}
		
		return false;
	}

}
