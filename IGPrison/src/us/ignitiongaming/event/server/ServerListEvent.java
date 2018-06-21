package us.ignitiongaming.event.server;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import us.ignitiongaming.config.ServerDefaults;
import us.ignitiongaming.enums.IGSettings;

public class ServerListEvent implements Listener {

	@EventHandler
	public static void OnPlayerViewOutsideServer(ServerListPingEvent event) {
		try {
			event.setMotd(ServerDefaults.getSetting(IGSettings.DEFAULT_MOTD).getValue().toString());
			//event.setMotd(ServerDefaults.DEFAULT_MOTD);
		}
		catch (Exception ex) {
			
		}
	}
}
