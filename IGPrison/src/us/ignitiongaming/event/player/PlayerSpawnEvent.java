package us.ignitiongaming.event.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.enums.IGLocations;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.factory.other.IGLocationFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.util.handy.FacingDirection;

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
	
	@EventHandler
	public static void onPlayerJoinGoToSpawn(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		if (IGPlayerFactory.getIGPlayerByPlayer(player).isValid()) {
			Location location = IGLocationFactory.getSpawnByPlayerRank(player).toLocation();
			location.setYaw(FacingDirection.EAST);
			player.teleport(location);
		}
	}
	
	@EventHandler
	public static void onPlayerJoinFirstTime(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!event.getPlayer().hasPlayedBefore()) {
			player.teleport(IGLocationFactory.getLocationByIGLocations(IGLocations.TUTORIAL).toLocation());
			Bukkit.broadcastMessage(GlobalTags.LOGO + "Welcome to our server, " + player.getName() + "!");
			IGRankNodes rank = IGRankNodes.getPlayerRank(player);
			player.setPlayerListName(rank.getFormatting() + player.getName());
			player.getInventory().addItem(new ItemStack(Material.DIAMOND_PICKAXE));
		}
	}
}
