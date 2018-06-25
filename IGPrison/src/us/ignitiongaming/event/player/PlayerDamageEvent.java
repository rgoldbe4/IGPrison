package us.ignitiongaming.event.player;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.factory.player.IGPlayerSolitaryFactory;
import us.ignitiongaming.singleton.IGSingleton;
import us.ignitiongaming.util.items.GuardSolitaryBaton;

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
	public void onSolitaryStickDamage(EntityDamageByEntityEvent event) {
		try {
			Entity damager = event.getDamager();
			Entity damaged = event.getEntity();
			if (damager instanceof Player && damaged instanceof Player) {
				Player damagingPlayer = (Player) damager;
				Player damagedPlayer = (Player) damaged;
				if (event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)|| event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
					ItemStack held = damagingPlayer.getInventory().getItemInMainHand();
					ArrayList<UUID> clockedin = IGSingleton.getInstance().getClockedIn();
					boolean isStaff = (damaged.hasPermission(IGRankNodes.STAFF.getNode()) || damaged.hasPermission(IGRankNodes.GUARD.getNode()) || damaged.hasPermission(IGRankNodes.WARDEN.getNode()));
					if (held != null)
						if(held.getItemMeta().getLore().contains(GuardSolitaryBaton.LORE) && 
								clockedin.contains(damagingPlayer.getUniqueId()) && !isStaff)
							IGPlayerSolitaryFactory.add(IGPlayerFactory.getIGPlayerByPlayer(damagedPlayer),new Date(System.currentTimeMillis() + 3600000));
				}
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void onShockBatonDamage(EntityDamageByEntityEvent event) {
		try {
			Entity damager = event.getDamager();
			Entity damaged = event.getEntity();
			if (damager instanceof Player && damaged instanceof Player) {
				Player damagingPlayer = (Player) damager;
				if (event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)|| event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
					ItemStack held = damagingPlayer.getInventory().getItemInMainHand();
					ArrayList<UUID> clockedin = IGSingleton.getInstance().getClockedIn();
					boolean isStaff = (damaged.hasPermission(IGRankNodes.STAFF.getNode()) || damaged.hasPermission(IGRankNodes.GUARD.getNode()) || damaged.hasPermission(IGRankNodes.WARDEN.getNode()));
					if (held != null)
						if(held.getItemMeta().getLore().contains(GuardSolitaryBaton.LORE) && 
								clockedin.contains(damagingPlayer.getUniqueId()) && !isStaff)
							damaged.getWorld().strikeLightning(damaged.getLocation());
				}
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
}
