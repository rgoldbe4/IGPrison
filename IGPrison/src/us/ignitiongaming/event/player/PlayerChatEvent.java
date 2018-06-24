package us.ignitiongaming.event.player;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.factory.player.IGPlayerDonatorFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.singleton.IGSingleton;
import us.ignitiongaming.util.convert.ChatConverter;

public class PlayerChatEvent implements Listener {
	
	@EventHandler
	public static void onPlayerTalk(AsyncPlayerChatEvent event) {
		ArrayList<UUID> staffchat = IGSingleton.getInstance().getStaffChatters();
		event.setMessage(ChatConverter.convertToColor(event.getMessage()));
		Player player = event.getPlayer();
		IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(event.getPlayer());
		String nickname = igPlayer.getNickname();
		String name = nickname.equals("") ? igPlayer.getName() : "~" + nickname;
		IGRankNodes playerRank = IGRankNodes.getPlayerRank(player);
		if ( staffchat.contains(igPlayer.getUUID()) ){
			for (Player online : Bukkit.getOnlinePlayers()){
				if( online.hasPermission(IGRankNodes.STAFF.getNode()) || online.hasPermission(IGRankNodes.GUARD.getNode()) || online.hasPermission(IGRankNodes.WARDEN.getNode()) ) 
					online.sendMessage("§b§l" + name + "§r§b: " + event.getMessage());
			}
			event.setCancelled(true);
		}		
		else {
			
			String format = "";
			
			if ( IGPlayerDonatorFactory.isIGPlayerDonator(igPlayer) 
						&& !playerRank.isStaff() 
						&& playerRank != IGRankNodes.SOLITARY )
				format += GlobalTags.DONATION;
			
			format += playerRank.getTag() + playerRank.getNameColor() + name + " §r> " + event.getMessage();
			
			event.setFormat(format);
		}
	}
}