package us.ignitiongaming.event.gang;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.entity.gang.IGGang;
import us.ignitiongaming.entity.gang.IGPlayerGangRequest;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.factory.gang.IGGangFactory;
import us.ignitiongaming.factory.gang.IGPlayerGangRequestFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;

public class PendingRequestEvent implements Listener {

	@EventHandler
	public static void onPlayerJoinWithRequest(PlayerJoinEvent event) {
		try {
			Player player = event.getPlayer();
			IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
			
			//Make sure that IGPlayer exists...
			if (igPlayer != null) {
				//Step 1: Determine if the player has unanswered requests.
				List<IGPlayerGangRequest> requests = IGPlayerGangRequestFactory.getUnansweredRequestByIGPlayer(igPlayer);
				
				if (requests.size() > 0) {
					//Step 2: Tell the player and let them know from what gangs.
					player.sendMessage(GlobalTags.GANG + 
							"§eYou have pending gang request(s). Type in §f§o/gang accept <gang>§r§e or §f§o/gang decline <gang>§r§eto answer the request.");
					player.sendMessage("The following gangs have sent you an invitation: ");
					for (IGPlayerGangRequest request : requests) {
						IGGang igGang = IGGangFactory.getGangById(request.getGangId());
						player.sendMessage("* " + igGang.getName());
					}
				}
			}
		} catch (Exception ex) {
			
			ex.printStackTrace();
		}
	}
}
