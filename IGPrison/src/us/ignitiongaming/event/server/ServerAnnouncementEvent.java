package us.ignitiongaming.event.server;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.entity.other.IGAnnouncement;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.factory.other.IGAnnouncementFactory;

public class ServerAnnouncementEvent implements Listener {

	@EventHandler
	public void onJoinShowAnnouncements(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		IGRankNodes rank = IGRankNodes.getPlayerRank(player);
		
		List<IGAnnouncement> announcements = IGAnnouncementFactory.getAnnouncements();
		
		for (IGAnnouncement announcement : announcements) {
			if (announcement.isStaffOnly()) {
				if (rank.isStaff()) player.sendMessage(GlobalTags.LOGO + announcement.getColoredText());
			} else {
				player.sendMessage(GlobalTags.LOGO + announcement.getColoredText());
			}
		}
	}
}
