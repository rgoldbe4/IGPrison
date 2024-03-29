package us.ignitiongaming.event.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.entity.gang.IGGang;
import us.ignitiongaming.entity.gang.IGPlayerGang;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.factory.gang.IGGangFactory;
import us.ignitiongaming.factory.gang.IGPlayerGangFactory;
import us.ignitiongaming.factory.player.IGPlayerDonatorFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.singleton.IGList;
import us.ignitiongaming.util.convert.ChatConverter;

public class PlayerChatEvent implements Listener {
	
	@EventHandler
	public static void onPlayerTalk(AsyncPlayerChatEvent event) {
		//Method Variables
		Player player = event.getPlayer();
		IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
		IGRankNodes playerRank = IGRankNodes.getPlayerRank(player);
		boolean isPlayerDonator = IGPlayerDonatorFactory.isIGPlayerDonator(igPlayer);
		
		
		//Step 1: Determine if the player has the ability to change the color of chat (Donator, Staff, Guard, Warden)
		if (player.hasPermission(IGRankNodes.STAFF.getNode()) 
				|| player.hasPermission(IGRankNodes.GUARD.getNode())
				|| player.hasPermission(IGRankNodes.WARDEN.getNode())
				|| isPlayerDonator) {
			event.setMessage(ChatConverter.convertToColor(event.getMessage()));
		}
		
		//Step 2: Determine if the player has a nickname (added this in object to handle logic).
		String name = igPlayer.getDisplayName();
		
		//Step 3: Determine if the player is in Staff Chat
		if ( IGList.staffChat.contains(player) ){
			for (Player online : Bukkit.getOnlinePlayers()){
				if( online.hasPermission(IGRankNodes.STAFF.getNode()) || online.hasPermission(IGRankNodes.GUARD.getNode()) || online.hasPermission(IGRankNodes.WARDEN.getNode()) ) 
					online.sendMessage("�b�l" + name + "�r�b: " + event.getMessage());
			}
			event.setCancelled(true);
		}
		//Step 4: Determine if the player is in Gang Chat
		else if ( IGList.gangChat.contains(player)) {
			IGPlayerGang igPlayerGang = IGPlayerGangFactory.getPlayerGangFromPlayer(igPlayer);
			event.setCancelled(true); //Cancel the event so no one else sees chat.
			
			for (Player online : Bukkit.getOnlinePlayers()) {
				IGPlayer igOnline = IGPlayerFactory.getIGPlayerByPlayer(online);
				//Determine if the online player is in a gang.
				if (IGPlayerGangFactory.isPlayerInGang(igOnline)) {
					//Determine if the online player is in the same gang.
					IGPlayerGang igOnlineGang = IGPlayerGangFactory.getPlayerGangFromPlayer(igOnline);
					if (igOnlineGang.getGangId() == igPlayerGang.getGangId()) {
						//Send the message to the player.
						online.sendMessage("�5�l" + name + "�5: " + event.getMessage());
					}
				}
			}
		
		}
		//Step 5: If all else fails, accept the event and pass through normal chatting system.
		else {
			
			String format = "";
			boolean isPlayerInGang = IGPlayerGangFactory.isPlayerInGang(igPlayer);
			
			// Add the donation tag if the player is a donator.
			if ( isPlayerDonator && !playerRank.isStaff() && playerRank != IGRankNodes.SOLITARY )
				format += GlobalTags.DONATION;
			
			// Add the player's gang tag if they are in a gang.
			if ( isPlayerInGang ) {
				IGPlayerGang igPlayerGang = IGPlayerGangFactory.getPlayerGangFromPlayer(igPlayer);
				IGGang igGang = IGGangFactory.getGangById(igPlayerGang.getGangId());
				format += "�8[�9" + igGang.getName() + "�8] �r";
			}

			// General Formatting For All Text Messages
			format += playerRank.getFormatting() + name + " �r> " + event.getMessage();
			
			// [#60] - If a player types in % into chat, it throws an exception because of conversion in .setFormat();
			format = format.replace("%", "");
			
			event.setFormat(format);
		}
	}
}