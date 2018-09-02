package us.ignitiongaming.event.solitary;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerSolitary;
import us.ignitiongaming.enums.IGLocations;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.factory.other.IGLocationFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerSolitaryFactory;
import us.ignitiongaming.util.convert.DateConverter;

public class VerifySolitaryEvent implements Listener {

	@EventHandler
	public void checkIfPlayerHasServed(AsyncPlayerChatEvent event) {
		try {
			Player player = event.getPlayer();
			IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
			
			//Only care if the player is in solitary.
			if (IGPlayerSolitaryFactory.isIGPlayerInSolitary(igPlayer)) {
				IGPlayerSolitary igPlayerSolitary = IGPlayerSolitaryFactory.getIGPlayerInSolitary(igPlayer);
				if (igPlayerSolitary.hasServed()) {
					player.sendMessage(GlobalTags.SOLITARY + "§eYou have served your time in solitary.");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group solitary user remove " + player.getName());
					player.teleport(IGLocationFactory.getSpawnByPlayerRank(player).toLocation());
					
					player.setPlayerListName(IGRankNodes.getPlayerFormatting(player));
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@EventHandler
	public static void onPlayerJoinRemoveFromSolitary(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
		
		if (igPlayer != null) {
			if (IGPlayerSolitaryFactory.isIGPlayerInSolitary(igPlayer)) {
				//We have a record of them, yes, but, are they IN solitary?
				IGPlayerSolitary playerSolitary = IGPlayerSolitaryFactory.getIGPlayerInSolitary(igPlayer);
				
				if (playerSolitary.hasServed()) {
					playerSolitary.setEnd(DateConverter.getCurrentTime());
					playerSolitary.save();
					player.teleport(IGLocationFactory.getSpawnByPlayerRank(player).toLocation());
					player.sendMessage(GlobalTags.SOLITARY + "§aYou are no longer in solitary.");
				} else {
					//Teleport them to solitary always
					player.teleport(IGLocations.SOLITARY.toLocation());
				}
			}
		}
	}
}
