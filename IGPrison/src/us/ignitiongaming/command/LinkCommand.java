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
					
					if (IGPrison.environment == IGEnvironments.MAIN) {
						if (args.length == 1) {
							IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
							//Now see if they have any links.
							IGLink link = IGLinkFactory.getLinkByIGPlayer(igPlayer);
							
							if (link.isValid()) {
								//They have a link... Check if the code is OK.
								String code = link.getCode();
								boolean isCorrect = code.equalsIgnoreCase(args[0]);
								if (isCorrect) {
									link.setConfirm(true);
									link.save();
									player.sendMessage("Your account has been verified!");
									//Now update the IGUser with the correct playerID
									IGUserFactory.setPlayerIDByUserID(link.getUserId(), igPlayer.getId());
								}
							} else {
								player.sendMessage("Your account has no pending link code. Please visit http://www.ignitiongaming.us/mc/donate/link.php to retrieve a code.");
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
