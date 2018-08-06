package us.ignitiongaming.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.database.ConvertUtils;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerKickedFactory;

public class IGKickCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
				
				if (lbl.equalsIgnoreCase("igkick")) {
					
					if (args.length == 0) {
						player.sendMessage("Usage: /igkick <player> <-s> <reason>");
						player.sendMessage("Example: /igkick BuffsOverNexus This is a broadcasted kick!");
						player.sendMessage("Example: /igkick BuffsOverNexus -s This is a silent kick!");
					}
					else if (args.length == 2 || args.length == 1) {
						boolean isSilent = (args.length == 1 ? false : args[1].equalsIgnoreCase("-s"));
						kickPlayer(igPlayer, player, args[0], "You have been kicked by " + player.getName(), isSilent);
					}
					else if (args.length > 2) {
						//Determine if this is a silent kick or not.
						boolean isSilent = args[1].equalsIgnoreCase("-s");
						//The reason starts after -s, so make sure we get the right ID
						String reason = ConvertUtils.getStringFromCommand( (isSilent ? 2 : 1) , args);
						
						kickPlayer(igPlayer, player, args[0], reason, isSilent);
						
					}
						
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	private void kickPlayer(IGPlayer igStaff, Player staff, String targetName, String reason, boolean isSilent) {
		Player target = Bukkit.getPlayer(targetName);
		
		//Determine if the player exists
		if (target != null) {
			IGPlayer igTarget = IGPlayerFactory.getIGPlayerByPlayer(target);
			
			//Record the kick
			IGPlayerKickedFactory.add(igTarget, reason, igStaff);
			
			//Broadcast the kick
			if (!isSilent) Bukkit.broadcastMessage(GlobalTags.LOGO + igTarget.getDisplayName() + " has been kicked from the server by " + staff.getName());
			else {
				staff.sendMessage(GlobalTags.LOGO + "You have kicked " + igTarget.getDisplayName() + " from the server.");
			}
			
			//Actually kick the player.
			target.kickPlayer(reason);
		} else {
			staff.sendMessage(GlobalTags.LOGO + "The player you requested to kick is not online.");
		}
	}

}
