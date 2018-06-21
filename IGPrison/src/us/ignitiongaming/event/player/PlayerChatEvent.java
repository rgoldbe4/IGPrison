package us.ignitiongaming.event.player;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import us.ignitiongaming.config.PlayerTags;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.rank.IGRank;
import us.ignitiongaming.enums.IGRanks;
import us.ignitiongaming.factory.player.IGPlayerDonatorFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerRankFactory;
import us.ignitiongaming.factory.rank.IGRankFactory;
import us.ignitiongaming.singleton.IGSingleton;

public class PlayerChatEvent implements Listener {
	
	@EventHandler
	public static void onPlayerTalk(AsyncPlayerChatEvent event) {
		ArrayList<UUID> staffchat = IGSingleton.getInstance().getStaffChatters();
		
		IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(event.getPlayer());
		IGRank igRank = IGPlayerRankFactory.getIGPlayerRank(igPlayer);
		if(staffchat.contains(igPlayer.getUUID())){	
			IGRank staff = IGRankFactory.getIGRankByRank(IGRanks.STAFF);
			IGRank guard = IGRankFactory.getIGRankByRank(IGRanks.STAFF);
			IGRank warden = IGRankFactory.getIGRankByRank(IGRanks.STAFF);
			for (Player online : Bukkit.getOnlinePlayers()){
				if(online.hasPermission(staff.getNode()) || online.hasPermission(guard.getNode()) || online.hasPermission(warden.getNode()) )online.sendMessage("§a[StaffChat] §r" + player.getName() + ": " + args[0]);
			}
			event.setCancelled(true);
		}		
		else event.setFormat((IGPlayerDonatorFactory.isIGPlayerDonator(igPlayer) && !igRank.isStaff()? PlayerTags.DONATOR:"") + igRank.getTag() + igRank.getNameColor() + igPlayer.getName() + " §r> " + event.getMessage());
	}
}
