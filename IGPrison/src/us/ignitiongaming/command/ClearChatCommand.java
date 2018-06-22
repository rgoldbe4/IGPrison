package us.ignitiongaming.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalMessages;
import us.ignitiongaming.config.ServerDefaults;
import us.ignitiongaming.entity.other.IGSetting;

public class ClearChatCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if(lbl.contains("clearchat")) {
				for(Player player: Bukkit.getOnlinePlayers()) {
					for(int i=0; i < 100; i ++)
					{
						player.sendMessage("");
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
}