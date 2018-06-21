package us.ignitiongaming.event.other;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import us.ignitiongaming.util.convert.ChatConverter;

public class FancySignEvent implements Listener {

	@EventHandler
	public static void onPlacingSign(SignChangeEvent event) {
		try {
			for (int i = 0; i < event.getLines().length; i++) {
				event.setLine(i, ChatConverter.convertToColor(event.getLine(i)));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
