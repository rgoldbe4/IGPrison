package us.ignitiongaming.event.player;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import us.ignitiongaming.singleton.IGSingleton;

public class PlayerDamageEvent implements Listener {
	
	@EventHandler
	public void onPickaxeDamagePlayer(EntityDamageByEntityEvent event) {
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
	@EventHandler
	public void onGuardDamageGuard(EntityDamageByEntityEvent event) {
		try {
			Entity damager = event.getDamager();
			Entity damaged = event.getEntity();
			if (damager instanceof Player && damaged instanceof Player) {
				Player damagingPlayer = (Player) damager;
				Player damagedPlayer = (Player) damager;
				ArrayList<UUID> clockedin = IGSingleton.getInstance().getClockedIn();
				if(clockedin.contains(damagingPlayer.getUniqueId()) && clockedin.contains(damagedPlayer.getUniqueId()))event.setCancelled(true);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
