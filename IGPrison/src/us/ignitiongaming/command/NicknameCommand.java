package us.ignitiongaming.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.factory.player.IGPlayerFactory;

public class NicknameCommand  implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			String nickname = "";
			if(args.length == 1) nickname = args[0];
			IGPlayer igPlayer; 
			if(lbl.equals("whois")) {
				igPlayer = IGPlayerFactory.getIGPlayerForNickname(nickname);
				if(igPlayer != null) {
					if(igPlayer.getName() != nickname) player.sendMessage(nickname  + " is " + igPlayer.getName() + "'s nickname");
					else player.sendMessage(nickname  + " is " + igPlayer.getName() + "'s actual name");
				}
				
				else player.sendMessage("This is either this players actual name or this player does not exist.");
			}
			if(lbl.equals("nickname")) {
				if(args.length == 2) {
					String who = args[1];
					igPlayer = IGPlayerFactory.getIGPlayerByPlayer(Bukkit.getPlayer(who));
				}
				else {
					igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
				}
				igPlayer.setNickname(nickname);	
				igPlayer.save();
			}
		}
		return false;
	}

}
