package us.ignitiongaming.command;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.IGPrison;
import us.ignitiongaming.config.GlobalMessages;
import us.ignitiongaming.config.ServerDefaults;
import us.ignitiongaming.database.Database;
import us.ignitiongaming.entity.other.IGSetting;
import us.ignitiongaming.enums.IGEnvironments;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.factory.other.IGSettingFactory;
import us.ignitiongaming.util.items.DefianceArmor;
import us.ignitiongaming.util.items.DefianceWeapon;

public class AdminCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				// [/iga]
				if (lbl.equalsIgnoreCase("iga")) {
					
					if (player.hasPermission("igprison.staff")) {
						
						// [/iga help]
						if (args.length == 1) {
							if (args[0].equalsIgnoreCase("help")) {
								
							}
							
							if (args[0].equalsIgnoreCase("refresh")) {
								//Update settings!
								ServerDefaults.settings = IGSettingFactory.getSettings();
								player.sendMessage("Plugin data refreshed.");
							}
							
							if (args[0].equalsIgnoreCase("defiance")) {
								player.getInventory().setHelmet(DefianceArmor.getHelmet());
								player.getInventory().setChestplate(DefianceArmor.getChestplate());
								player.getInventory().setLeggings(DefianceArmor.getLeggings());
								player.getInventory().setBoots(DefianceArmor.getBoots());
								player.getInventory().setItemInMainHand(DefianceWeapon.getDefianceSword());
							}
							
							if (args[0].equalsIgnoreCase("settings")) {
								for (IGSetting setting : ServerDefaults.settings) {
									player.sendMessage(setting.getLabel() + " = " + setting.getValue());
								}
								player.sendMessage("Environment: " + IGPrison.environment);	
								
							}
							
							if (args[0].equalsIgnoreCase("db")) {
								player.sendMessage("Is Database Connected? " + !Database.connection.isClosed());
								//Reconnect if not connected.
								if (Database.connection.isClosed()) {
									if (IGPrison.environment.equals(IGEnvironments.MAIN)) {
										Database.ConnectToMain();
										Bukkit.getLogger().log(Level.INFO, "Connected to Main Database");
									}
									else {
										Database.ConnectToTesting();
										Bukkit.getLogger().log(Level.INFO, "Connected to Testing Database");
									}
									player.sendMessage("Reconnected to database.");
								}
							}
							
							if (args[0].equalsIgnoreCase("ranks")) {
								for (IGRankNodes rank : IGRankNodes.values()) {
									player.sendMessage(rank.getFormatting());
								}
							}
							
							
						}
						
					} else {
						player.sendMessage(GlobalMessages.NO_PERMISSIONS);
					}
										
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
