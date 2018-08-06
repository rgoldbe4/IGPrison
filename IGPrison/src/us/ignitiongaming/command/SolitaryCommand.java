package us.ignitiongaming.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalMessages;
import us.ignitiongaming.config.GlobalTags;

public class SolitaryCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (lbl.equalsIgnoreCase("solitary")) {
					
					if (args.length == 0) {
						displayHelp(player);
					}
					else if (args.length == 1) {
						
						if (args[0].equalsIgnoreCase("help")) {
							displayHelp(player);
						}
						else if (args[0].equalsIgnoreCase("context")) {
							
						}
						else if (args[0].equalsIgnoreCase("list")) {
							
						}
						else {
							player.sendMessage(GlobalMessages.INVALID_COMMAND);
						}
					}
					else {
						player.sendMessage(GlobalMessages.INVALID_COMMAND);
					}
					
					
				
				}
				
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	private void displayHelp(Player player) {
		player.sendMessage("----- " + GlobalTags.SOLITARY + " -----");
		player.sendMessage(" /solitary -> Shows this.");
		player.sendMessage(" /solitary help -> Shows this.");
		player.sendMessage(" /solitary context -> Display all context you can use for <duration> arguments.");
		player.sendMessage(" /solitary add <player> <duration> -> Add a player to solitary.");
		player.sendMessage(" /solitary remove <player> -> Remove a player from solitary.");
		player.sendMessage(" /solitary list -> Display all current players in solitary.");
		player.sendMessage(" /solitary info <player> -> Display in-depth information on a player.");
	}

}
