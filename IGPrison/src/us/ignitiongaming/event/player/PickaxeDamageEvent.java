package us.ignitiongaming.event.player;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class PickaxeDamageEvent implements Listener {
	
	@EventHandler
	public void onPlayerDamagePlayer(EntityDamageByEntityEvent event) {
		try {
		Entity damager = event.getDamager();
		Entity damaged = event.getEntity();
		if (damager instanceof Player && damaged instanceof Player) {
			Player damagingPlayer = (Player) damager;
			if (event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)|| event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
				ItemStack held = damagingPlayer.getInventory().getItemInMainHand();
				if (held != null)
					if (   held.getType() == Material.DIAMOND_PICKAXE
					    || held.getType() == Material.IRON_PICKAXE 
					    || held.getType() == Material.WOOD_PICKAXE
					    || held.getType() == Material.GOLD_PICKAXE )
						event.setCancelled(true);
			}
			
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
