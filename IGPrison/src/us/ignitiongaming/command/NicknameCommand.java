package us.ignitiongaming.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerNickname;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerNicknameFactory;

public class NicknameCommand  implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if(sender instanceof Player && args.length == 1) {
			Player player = (Player) sender;
			String nickname = args[0];
			IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
			if(lbl.equals("whois")) {
				IGPlayerNickname playerNick = IGPlayerNicknameFactory.getIGPlayerNicknameForString(nickname);
				if(playerNick!=null)player.sendMessage(nickname  + " is " + playerNick.getActualName() + "'s nickname");
				else player.sendMessage("This is either this players actual name or this player does not exist.");
			}
			if(lbl.equals("nickname")) {
				IGPlayerNicknameFactory.setIGPlayerNicknameForIGPlayer(igPlayer, nickname);		
			}
		}
		return false;
	}

}
