package us.ignitiongaming.event.other;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import us.ignitiongaming.enums.IGGuardBatons;

public class ShockBatonAttackEvent implements Listener {
	
	@EventHandler
	public void onUseOfShockBaton(EntityDamageByEntityEvent event) {
		try {
			if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
				Player attacker = (Player) event.getDamager();
				Player defender = (Player) event.getEntity();
				String attackerWeaponName = ChatColor.stripColor(attacker.getInventory().getItemInMainHand().getItemMeta().getDisplayName());
				String batonWeaponName = ChatColor.stripColor(IGGuardBatons.SHOCK_BATON.getTag());
				if (attackerWeaponName.equalsIgnoreCase(batonWeaponName)) {
					PotionEffect effect = new PotionEffect(PotionEffectType.CONFUSION, 15 * 20, 10);
					defender.addPotionEffect(effect);
					Bukkit.getWorld("world").strikeLightningEffect(defender.getLocation());
				}
			}
		} catch (Exception ex) {
			
		}
	}
}
