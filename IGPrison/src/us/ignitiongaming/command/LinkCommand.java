package us.ignitiongaming.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.IGPrison;
import us.ignitiongaming.config.GlobalMessages;
import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.user.IGLink;
import us.ignitiongaming.entity.user.IGUser;
import us.ignitiongaming.enums.IGEnvironments;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.user.IGLinkFactory;
import us.ignitiongaming.factory.user.IGUserFactory;


public class LinkCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				
				if (lbl.equalsIgnoreCase("link")) {
					
					if (IGPrison.environment == IGEnvironments.MAIN || player.getName().equalsIgnoreCase("buffsovernexus")) {
						if (args.length == 0) {
							player.sendMessage(GlobalTags.LOGO + "§8URL: §7§o§nhttp://ignitiongaming.heliohost.org/mc/donate/link.php§r");
						}
						else if (args.length == 1) {
							IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
							boolean isAlreadyLinked = IGUserFactory.doesUserHavePlayerId(igPlayer.getId());
							//Now see if they have any links.
							
							if (!isAlreadyLinked) {
								IGLink link = IGLinkFactory.getLinkByCode(args[0]);
								
								if (link.isValid()) {
									IGUser user = IGUserFactory.getUserById(link.getUserId());
									user.setPlayerId(igPlayer.getId());
									user.save();
									link.setConfirm(true);
									link.save();
									player.sendMessage("Your account has been verified!");
								} else {
									player.sendMessage("You have entered in the wrong code for your account. Please visit http://ignitiongaming.heliohost.org/mc/donate/link.php to retrieve a code.");
								}
							} else {
								player.sendMessage("Your account has already been linked. Please report this if it is in error to the forums.");
							}
						} else {
							player.sendMessage(GlobalMessages.INVALID_COMMAND);
						}
					} else {
						player.sendMessage(GlobalTags.LOGO + "This server does not support linkage of players and users.");
					}
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	

}
