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
import us.ignitiongaming.entity.rank.IGRank;
import us.ignitiongaming.enums.IGRanks;
import us.ignitiongaming.factory.player.IGPlayerDonatorFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerRankFactory;
import us.ignitiongaming.factory.rank.IGRankFactory;
import us.ignitiongaming.singleton.IGSingleton;
import us.ignitiongaming.util.convert.ChatConverter;

public class PlayerChatEvent implements Listener {
	
	@EventHandler
	public static void onPlayerTalk(AsyncPlayerChatEvent event) {
		ArrayList<UUID> staffchat = IGSingleton.getInstance().getStaffChatters();
		event.setMessage(ChatConverter.convertToColor(event.getMessage()));
		
		IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(event.getPlayer());
		IGRank igRank = IGPlayerRankFactory.getIGPlayerRank(igPlayer);
		String nickname = igPlayer.getNickname();
		System.out.println(nickname);
		String name = nickname.equals("") ? igPlayer.getName() : "~" + nickname;
		if(staffchat.contains(igPlayer.getUUID())){	
			IGRank staff = IGRankFactory.getIGRankByRank(IGRanks.STAFF);
			IGRank guard = IGRankFactory.getIGRankByRank(IGRanks.STAFF);
			IGRank warden = IGRankFactory.getIGRankByRank(IGRanks.STAFF);
			for (Player online : Bukkit.getOnlinePlayers()){
				if( online.hasPermission(staff.getNode()) || online.hasPermission(guard.getNode()) || online.hasPermission(warden.getNode()) ) 
					online.sendMessage("§b§l" + name + "§r§b: " + event.getMessage());
			}
			event.setCancelled(true);
		}		
		else {
			String donator = IGPlayerDonatorFactory.isIGPlayerDonator(igPlayer) && !igRank.isStaff() ? GlobalTags.DONATION : "";
			System.out.println(donator);
			String rank = "";
			if(igRank != null)rank = igRank.getTag();
			System.out.println(rank);
			String nameColor = "";
			if(igRank != null)nameColor = igRank.getNameColor();
			System.out.println(nameColor);
			event.setFormat(
				 donator + 
					rank + nameColor + name + " §r> " + event.getMessage()
			);
		}
	}
}
