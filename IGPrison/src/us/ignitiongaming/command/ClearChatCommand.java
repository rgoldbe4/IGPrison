package us.ignitiongaming.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.util.convert.ChatConverter;

public class ClearChatCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (lbl.contains("clearchat")) {
					for(Player online: Bukkit.getOnlinePlayers()) {
						ChatConverter.clearPlayerChat(online);
						online.sendMessage(GlobalTags.LOGO + player.getName() + " cleared chat.");
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
}