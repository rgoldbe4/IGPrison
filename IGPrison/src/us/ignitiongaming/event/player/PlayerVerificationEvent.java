package us.ignitiongaming.event.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import us.ignitiongaming.config.GlobalMessages;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerBanned;
import us.ignitiongaming.entity.player.IGPlayerStats;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.factory.player.IGPlayerBannedFactory;
import us.ignitiongaming.factory.player.IGPlayerDonatorFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerStatsFactory;
import us.ignitiongaming.util.convert.DateConverter;

public class PlayerVerificationEvent implements Listener {

	/*
	 * Features in this class:
	 * - Check if player exists in database (IGPlayer)
	 * - Check if player changed their name (via Mojang)
	 * - Check if the player has donated.
	 * - Update the player's recent login.
	 */
	
	@EventHandler
	public void addPlayerToDatabase(PlayerJoinEvent event) {
		try {
			Player player = event.getPlayer();
			IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
			//Either returns an entity (which means it exists) or returns null (add it!).
			if (igPlayer == null) {
				IGPlayerFactory.add(player);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@EventHandler
	public void verifyNameChange(PlayerJoinEvent event) {
		try {
			Player player = event.getPlayer();
			IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
			
			if (igPlayer.isValid()) {
				if (!igPlayer.getName().equalsIgnoreCase(player.getName())) {
					igPlayer.setName(player.getName());
					igPlayer.save();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@EventHandler
	public void verifyIfPlayerIsDonator(PlayerJoinEvent event) {
		try {
			Player player = event.getPlayer();
			IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
			if (igPlayer.isValid()) {
				boolean isDonator = IGPlayerDonatorFactory.isIGPlayerDonator(igPlayer);
				if (isDonator) {
					player.sendMessage(GlobalMessages.THANK_YOU_DONATE);
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@EventHandler
	public void updateLatestLogin(PlayerJoinEvent event) {
		try {
			Player player = event.getPlayer();
			IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
			if (igPlayer.isValid()) {
				IGPlayerStats igStats = IGPlayerStatsFactory.getIGPlayerStatsByIGPlayer(igPlayer);
				igStats.setLastLogin(DateConverter.getCurrentTimeString());
				igStats.save();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@EventHandler
	public void updateIP(PlayerJoinEvent event) {
		try {
			Player player = event.getPlayer();
			IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
			if (igPlayer.isValid()) {
				String playerIP = player.getAddress().getAddress().getHostAddress();
				if (!igPlayer.getIP().equalsIgnoreCase(playerIP)) {
					igPlayer.setIP(playerIP);
					igPlayer.save();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@EventHandler
	public static void onBannedPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
		if (igPlayer.isValid()){
			if (IGPlayerBannedFactory.isBanned(igPlayer)){
				IGPlayerBanned playerBan = IGPlayerBannedFactory.getPlayerBan(igPlayer);
				
				if (playerBan.isPermanent()) {
					player.kickPlayer("You were banned permanently for \"" + playerBan.getReason() + "\"");
				} else {
					player.kickPlayer("You were banned for \"" + playerBan.getReason() + "\" until " + DateConverter.toFriendlyDate(playerBan.getEnd()));
				}
			}
		}
	}
	
	
	@EventHandler
	public static void onPlayerChatWithoutIGPlayer(AsyncPlayerChatEvent event) {
		try {
			Player player = event.getPlayer();
			IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
			
			if (!igPlayer.isValid()) {
				
				/* Add them to the DB
				 * Alert them they were added
				 * Cancel the chat event.
				 */
				IGPlayerFactory.add(player);
				
				player.sendMessage("You have been added to our records. Please type your chat message again.");
				
				//Cancel event
				event.setCancelled(true);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	//Don't fire this event.
	public static void onPlayerJoinAddTheirRank(PlayerJoinEvent event) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		
		Objective objective = board.registerNewObjective("showrank", "dummy");
		objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
		objective.setDisplayName(IGRankNodes.getPlayerRank(event.getPlayer()).getTag());
		
		event.getPlayer().setScoreboard(board);
	}
}
