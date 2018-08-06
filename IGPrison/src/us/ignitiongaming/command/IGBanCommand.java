package us.ignitiongaming.command;


import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.database.ConvertUtils;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.factory.player.IGPlayerBannedFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.util.convert.ChatConverter;
import us.ignitiongaming.util.convert.DateConverter;

public class IGBanCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (lbl.equalsIgnoreCase("igban")) {
				//Default reason in case one is not given.
				String reason = "You were banned by " + player.getName();
				IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
				
				if (args.length == 0) {
					ChatConverter.clearPlayerChat(player);
					player.sendMessage("§eUsage: §r§o/igban <player> <-s> <context> <reason>");
				}
				else if (args.length == 2) {
					// [/igban <player> <context>]
					boolean isSilent = false;
					IGPlayer igTarget = IGPlayerFactory.getIGPlayerFromName(args[0]);
					String context = args[1];
					banPlayer(player, igPlayer, igTarget, context, isSilent, reason);
				}
				else if (args.length == 3) {
					// [/igban <player> -s <context>]
					boolean isSilent = args[1].equalsIgnoreCase("-s");
					IGPlayer igTarget = IGPlayerFactory.getIGPlayerFromName(args[0]);
					String context = args[2];
					banPlayer(player, igPlayer, igTarget, context, isSilent, reason);
				}
				else if (args.length > 3) {
					// [/igban <player> <-s> <context> <reason>]
					boolean isSilent = args[1].equalsIgnoreCase("-s");
					IGPlayer igTarget = IGPlayerFactory.getIGPlayerFromName(args[0]);
					
					if (!isSilent) {
						// [/igban <player> <context> <reason>]
						String context = args[1];
						reason = ConvertUtils.getStringFromCommand(2, args);
						banPlayer(player, igPlayer, igTarget, context, isSilent, reason);
					} else {
						// [/igban <player> -s <context> <reason>]
						String context = args[2];
						reason = ConvertUtils.getStringFromCommand(3, args);
						banPlayer(player, igPlayer, igTarget, context, isSilent, reason);
					}
					
				}
			}
		}
		
		return false;
	}
	
	private void banPlayer(Player player, IGPlayer igPlayer, IGPlayer igTarget, String context, boolean isSilent, String reason) {
		Player target = Bukkit.getPlayer(igTarget.getName());
		
		//Data Verification: Make sure igTarget is valid.
		if (!igTarget.isValid()) player.sendMessage(GlobalTags.LOGO + "§4The player you entered was not found.");
		
		//Step 1: Determine if the player is already banned.
		if (!IGPlayerBannedFactory.isBanned(igTarget)) {
			
			//Step 2: Determine if the context is 0 (or 'perm')
			boolean isPermanent = (context.equalsIgnoreCase("0") || context.contains("perm"));

			if (isPermanent) {
				//Add the ban to the database.
				IGPlayerBannedFactory.addPermanent(igPlayer, igTarget, reason);	
				
				//Find the player (if online) and kick them for <reason>
				if (target != null) target.kickPlayer(reason);
				
				//Display the ban in chat (or just to the player).
				if (isSilent) player.sendMessage(GlobalTags.LOGO + "§aYou have banned §l" + igTarget.getName() + "§r§a permanently.");
				else Bukkit.broadcastMessage(GlobalTags.LOGO + igPlayer.getName() + " has banned §e§l" + igTarget.getName() + "§r permanently.");
				
				
			} else {
				//Add the ban to the database.
				Date end = DateConverter.convertSingleArgumentContextToDate(context);
				IGPlayerBannedFactory.add(igPlayer, igTarget, end, reason);
				
				//Find the player (if online) and kick them for <reason>
				if (target != null) target.kickPlayer(reason);
				
				if (isSilent) player.sendMessage(GlobalTags.LOGO + "§aYou have banned §l" + igTarget.getName() + "§r§a until §e" + DateConverter.toFriendlyDate(end));
				else Bukkit.broadcastMessage(GlobalTags.LOGO + igPlayer.getName() + " has banned §e§l" + igTarget.getName() + "§r.");
				
			}
			
		} else {
			player.sendMessage(GlobalTags.LOGO + "§4The player you entered, " + igTarget.getName() + ", is already banned.");
		}
	}

}
