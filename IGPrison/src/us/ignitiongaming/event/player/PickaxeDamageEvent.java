package us.ignitiongaming.event.player;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class PickaxeDamageEvent implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerDamagePlayer(EntityDamageByEntityEvent event) {
		try {
		Entity damager = event.getDamager();
		Entity damaged = event.getEntity();
		event.getCause();
		if(damager instanceof Player && damaged instanceof Player){
			Player damagingPlayer = (Player) damager;
			if(event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)|| event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
				ItemStack held = damagingPlayer.getItemInHand();
				if(held != null)
					if(held.getTypeId() == 270 || held.getTypeId() == 274 || held.getTypeId() == 257 || held.getTypeId() == 285 || held.getTypeId() == 278)
						event.setCancelled(true);
			}
			
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
