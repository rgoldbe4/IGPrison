package us.ignitiongaming.command;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.entity.other.IGHome;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerHome;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerHomeFactory;
import us.ignitiongaming.util.convert.ChatConverter;

public class HomeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (lbl.equalsIgnoreCase("home")) {
					
					if (args.length == 0) {
						ChatConverter.clearPlayerChat(player);
						player.sendMessage("§eUsage: §f§o/home <home>");
					} else if (args.length == 1) {
						String name = args[0];
						teleportToHome(player, name);
					} else {
						ChatConverter.clearPlayerChat(player);
						player.sendMessage("§eUsage: §f§o/home <home>");
					}
					
				}
				
				if (lbl.equalsIgnoreCase("homes")) {
					ChatConverter.clearPlayerChat(player);
					displayHomeList(player);
				}
				
				if (lbl.equalsIgnoreCase("sethome")) {
					
					if (args.length == 0) {
						ChatConverter.clearPlayerChat(player);
						player.sendMessage("§eUsage: §f§o/sethome <home>");
					} else if (args.length == 1) {
						addHome(player, args[0]);
					} else {
						ChatConverter.clearPlayerChat(player);
						player.sendMessage("§eUsage: §f§o/sethome <home>");
					}
				}
				
				if (lbl.equalsIgnoreCase("delhome")) {
					if (args.length == 0) {
						ChatConverter.clearPlayerChat(player);
						player.sendMessage("§eUsage: §f§o/delhome <home>");
					} else if (args.length == 1) {
						hideHome(player, args[0]);
					} else {
						ChatConverter.clearPlayerChat(player);
						player.sendMessage("§eUsage: §f§o/delhome <home>");
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public void displayHomeList(Player player) {
		IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
		List<IGHome> homes = IGPlayerHomeFactory.getHomesByPlayer(igPlayer);
		
		if (homes.size() == 0) {
			player.sendMessage(GlobalTags.LOGO + "§4You do not have any homes.");
			return;
		}
		
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < homes.size(); i++) {
			builder.append(homes.get(i).getName() + ((i + 1 == homes.size() ? "" : "_")));
		}
		
		player.sendMessage(GlobalTags.LOGO + "Homes:");
		player.sendMessage("§7" + ChatConverter.toCamelCase(builder.toString()));
	}
	
	public void teleportToHome(Player player, String name) {
		IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
		if (IGPlayerHomeFactory.doesHomeExist(igPlayer, name)) {
			IGHome home = IGPlayerHomeFactory.getHomeByPlayerAndHome(igPlayer, name);
			player.teleport(home.toLocation());
			player.sendMessage(GlobalTags.LOGO + "§aTeleported to \"" + ChatConverter.toCamelCase(home.getName()) + "\"");
		} else {
			player.sendMessage(GlobalTags.LOGO + "§cInvalid home. §fTry §e/homes §fto view your list of homes.");
		}
		
	}

	public void addHome(Player player, String name) {
		Location location = player.getLocation();
		IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
		if (!IGPlayerHomeFactory.doesHomeExist(igPlayer, name)) {
			IGPlayerHomeFactory.add(igPlayer, location, name);
			player.sendMessage(GlobalTags.LOGO + "§aAdded home \"" + name + "\"");
		} else {
			IGHome home = IGPlayerHomeFactory.getHomeByPlayerAndHome(igPlayer, name);
			home.fromLocation(location);
			home.save();
			player.sendMessage(GlobalTags.LOGO + "§aUpdated home \"" + name + "\"");
		}
		
	}

	public void hideHome(Player player, String name) {
		IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
		
		if (IGPlayerHomeFactory.doesHomeExist(igPlayer, name)) {
			IGPlayerHome playerHome = IGPlayerHomeFactory.getPlayerHomeByPlayerAndHome(igPlayer, name);
			playerHome.setVisible(false);
			playerHome.save();
			player.sendMessage(GlobalTags.LOGO + "§aYou have deleted \"" + name + "\"");
		} else {
			player.sendMessage(GlobalTags.LOGO + "§cThe home you entered (" + name + ") does not exist.");
		}
	}
}
