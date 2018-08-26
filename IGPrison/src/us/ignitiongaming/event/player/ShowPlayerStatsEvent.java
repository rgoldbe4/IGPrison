package us.ignitiongaming.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.config.ServerDefaults;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerStats;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerStatsFactory;
import us.ignitiongaming.util.convert.CurrencyConverter;
import us.ignitiongaming.util.handy.ScoreboardAnnouncer;

public class ShowPlayerStatsEvent implements Listener {

	@EventHandler
	public void onPlayerRightClickPlayer(PlayerInteractEntityEvent event) {
		try {
			if (event.getRightClicked() instanceof Player) {
				Player player = event.getPlayer();
				Player target = (Player) event.getRightClicked();
				
				IGPlayer igTarget = IGPlayerFactory.getIGPlayerByPlayer(target);
				IGPlayerStats stats = IGPlayerStatsFactory.getIGPlayerStatsByIGPlayer(igTarget);
				
				ScoreboardAnnouncer scoreboard = new ScoreboardAnnouncer(player);
				scoreboard.setTitle(GlobalTags.LOGO);
				scoreboard.addLine("§dPoints §7>> §f" + stats.getDonatorPoints());
				scoreboard.addLine("§4Deaths §7>> §f" + stats.getDeaths());
				scoreboard.addLine("§eKills §7>> §f" + stats.getKills());
				scoreboard.addLine("§2Money §7>> §f" + CurrencyConverter.convertToCurrency(ServerDefaults.econ.getBalance(target)));
				scoreboard.addSpacer();
				scoreboard.addLine(IGRankNodes.getPlayerFormatting(target));
				scoreboard.addTimer(8);
				scoreboard.hook();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
