package us.ignitiongaming.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerStats;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerStatsFactory;

public class PlayerRecordEvent implements Listener {

	@EventHandler
	public static void onPlayerDeathEvent(PlayerDeathEvent event) {
		Player player = event.getEntity();
		//Determine if the entity was killed by another player.
		if (player.getKiller() != null) {
			Player killer = player.getKiller();
			IGPlayer igKiller = IGPlayerFactory.getIGPlayerByPlayer(killer);
			IGPlayerStats stats = IGPlayerStatsFactory.getIGPlayerStatsByIGPlayer(igKiller);
			stats.addKill();
			stats.save();
		}
		IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
		IGPlayerStats stats = IGPlayerStatsFactory.getIGPlayerStatsByIGPlayer(igPlayer);
		stats.addDeath();
		stats.save();
	}
}
