package us.ignitiongaming.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalMessages;
import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.util.convert.ChatConverter;

public class NicknameCommand  implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player); 
			if (lbl.equals("whois")) {
				// [/whois]
				if (args.length == 1) {
					String nickname = ChatConverter.stripColor(args[0]);
					IGPlayer igTarget = IGPlayerFactory.getIGPlayerForNickname(nickname);
					
					if (igTarget.isValid()) {
						player.sendMessage(GlobalTags.LOGO + "§7" + args[0] + "§f belongs to §7" + igTarget.getName());
					} else {
						player.sendMessage(GlobalTags.LOGO + "§4The nickname, " + nickname + ", is not owned by anyone.");
					}
					
				} else {
					player.sendMessage(GlobalMessages.INVALID_COMMAND);
				}
			}
			if (lbl.equals("nickname")) {
				// [/nickname]
				if (args.length == 0) {
					igPlayer.setNickname("");
					igPlayer.save();
					player.sendMessage(GlobalTags.LOGO + "§aYour nickname has been reset.");
				// [/nickname <nickname>]
				} else if (args.length == 1) {
					String nickname = ChatConverter.stripColor(args[0]);
					if (!isNicknameInUse(nickname)) {
						igPlayer.setNickname(nickname);
						igPlayer.save();
						player.sendMessage(GlobalTags.LOGO + "§aYour nickname is now: §l" + nickname);
					} else {
						player.sendMessage(GlobalTags.LOGO + "§4The nickname you wanted is either a player's name or is in use.");
					}
				} else {
					player.sendMessage(GlobalMessages.INVALID_COMMAND);
				}
				
			}
		}
		return false;
	}
	
	private boolean isNicknameInUse(String nickname) {
		List<IGPlayer> allPlayers = IGPlayerFactory.getAllPlayers();
		boolean isInUse = false;
		
		for(IGPlayer igPlayer : allPlayers) {
			if (igPlayer.getName().equalsIgnoreCase(nickname)) isInUse = true;
			if (igPlayer.hasNickname()) {
				if (igPlayer.getNickname().equalsIgnoreCase(nickname))
					isInUse = true;
			}
		}
		
		return isInUse;
	}

}
