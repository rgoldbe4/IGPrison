package us.ignitiongaming.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import us.ignitiongaming.enums.IGLocations;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.factory.other.IGLocationFactory;

public class PlayerSpawnEvent implements Listener {

	@EventHandler
	public static void onPlayerRespawn(PlayerRespawnEvent event) {
		try {
			Player player = event.getPlayer();
			
			//Determine if the player is staff...
			boolean isStaff = IGRankNodes.getPlayerRank(player).isStaff();
			
			//Spawn the player in the location based on rank. Staff -> Spawn.
			if (player.hasPermission(IGRankNodes.FREE.getNode()) || isStaff) {
				player.teleport(IGLocationFactory.getLocationByIGLocations(IGLocations.SPAWN).toLocation());
			} else if (player.hasPermission(IGRankNodes.A.getNode())) {
				player.teleport(IGLocationFactory.getLocationByIGLocations(IGLocations.A).toLocation());
			} else if (player.hasPermission(IGRankNodes.B.getNode())) {
				player.teleport(IGLocationFactory.getLocationByIGLocations(IGLocations.B).toLocation());
			} else if (player.hasPermission(IGRankNodes.C.getNode())) {
				player.teleport(IGLocationFactory.getLocationByIGLocations(IGLocations.C).toLocation());
			} else {
				player.teleport(IGLocationFactory.getLocationByIGLocations(IGLocations.D).toLocation());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
