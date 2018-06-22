package us.ignitiongaming.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import us.ignitiongaming.entity.rank.IGRank;
import us.ignitiongaming.enums.IGLocations;
import us.ignitiongaming.enums.IGRanks;
import us.ignitiongaming.factory.other.IGLocationFactory;
import us.ignitiongaming.factory.rank.IGRankFactory;

public class PlayerSpawnEvent implements Listener {

	@EventHandler
	public static void onPlayerRespawn(PlayerRespawnEvent event) {
		try {
			//Apparently, this is triggered when a player respawns...
			Player player = event.getPlayer();
			IGRank cellC = IGRankFactory.getIGRankByRank(IGRanks.C);
			IGRank cellB = IGRankFactory.getIGRankByRank(IGRanks.B);
			IGRank cellA = IGRankFactory.getIGRankByRank(IGRanks.A);
			IGRank free = IGRankFactory.getIGRankByRank(IGRanks.FREE);
			
			if (player.hasPermission(free.getNode())) {
				player.teleport(IGLocationFactory.getLocationByIGLocations(IGLocations.SPAWN).toLocation());
				player.sendMessage("FREE");
			} else if (player.hasPermission(cellA.getNode())) {
				player.teleport(IGLocationFactory.getLocationByIGLocations(IGLocations.A).toLocation());
				player.sendMessage("A");
			} else if (player.hasPermission(cellB.getNode())) {
				player.teleport(IGLocationFactory.getLocationByIGLocations(IGLocations.B).toLocation());
				player.sendMessage("B");
			} else if (player.hasPermission(cellC.getNode())) {
				player.teleport(IGLocationFactory.getLocationByIGLocations(IGLocations.C).toLocation());
				player.sendMessage("C");
			} else {
				player.teleport(IGLocationFactory.getLocationByIGLocations(IGLocations.D).toLocation());
				player.sendMessage("D");
			}
			
			player.sendMessage("§dYou have been put in your designated spawn area.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
