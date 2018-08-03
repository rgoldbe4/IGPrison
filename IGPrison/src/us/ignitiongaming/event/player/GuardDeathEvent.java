package us.ignitiongaming.event.player;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import us.ignitiongaming.config.GlobalMessages;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerStats;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerStatsFactory;
import us.ignitiongaming.singleton.IGList;

public class GuardDeathEvent implements Listener {

	@EventHandler
	public static void onGuardDeath(PlayerDeathEvent event) {
		try {
			//Ensure the player is Guard, Warden, or Staff.
			if (event.getEntity().hasPermission("igprison.guard") || event.getEntity().hasPermission("igprison.warden") || event.getEntity().hasPermission("igprison.staff")) {
				//Make sure the player does not drop anything.
				event.setKeepInventory(true);
				
				//Add the player's head to their inventory...
				if (event.getEntity().getKiller() != null) {
					if (event.getEntity().getKiller() instanceof Player) {
						Player killer = event.getEntity().getKiller();
						IGPlayer igKiller = IGPlayerFactory.getIGPlayerByPlayer(killer);
						IGPlayerStats igStats = IGPlayerStatsFactory.getIGPlayerStatsByIGPlayer(igKiller);
						if(IGList.clockedIn.contains(event.getEntity())) {
							igStats.addDonatorPoint();
							event.setDeathMessage(GlobalMessages.GUARD_DEATH);
						}
						igStats.save();
					}
				}				
			}	
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@EventHandler
	public static void onPlayerDeath(PlayerDeathEvent event) {
		try {
			if (event.getEntity().getKiller() != null) {
				if (event.getEntity().getKiller() instanceof Player) {
					//Get killer.
					Player killer = event.getEntity().getKiller();
					
					//Drop the player's head.
					ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
					SkullMeta sm = (SkullMeta) item.getItemMeta();
					sm.setOwningPlayer(event.getEntity());
					item.setItemMeta(sm);

					killer.getInventory().addItem(item);
					
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
