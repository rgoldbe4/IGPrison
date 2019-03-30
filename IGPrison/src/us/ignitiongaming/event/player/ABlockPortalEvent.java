package us.ignitiongaming.event.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class ABlockPortalEvent implements Listener {

	@EventHandler
	public static void onPortalTravel(PlayerPortalEvent event)
	{
	    if ( event.getCause() == PlayerPortalEvent.TeleportCause.END_PORTAL ) {
	    	event.setCancelled(true);
	    }
	}
}
