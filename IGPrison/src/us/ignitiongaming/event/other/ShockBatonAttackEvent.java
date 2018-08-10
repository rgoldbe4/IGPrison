package us.ignitiongaming.event.other;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import us.ignitiongaming.enums.IGBatons;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.util.convert.ChatConverter;
import us.ignitiongaming.util.convert.TickConverter;

public class ShockBatonAttackEvent implements Listener {
	
	@EventHandler
	public void onUseOfShockBaton(EntityDamageByEntityEvent event) {
		try {
			//Ignore the event if it's not player on player.
			if (!(event.getEntity() instanceof Player)) return;
			if (!(event.getDamager() instanceof Player)) return;
			
			Player attacker = (Player) event.getDamager();
			Player defender = (Player) event.getEntity();
			
			IGRankNodes attackerRank = IGRankNodes.getPlayerRank(attacker);
			if (attackerRank.isStaff()) {
				//If item in hand doesn't exist.
				if (attacker.getInventory().getItemInMainHand() == null) return;
				if (attacker.getInventory().getItemInMainHand().getItemMeta() == null) return;
				
				ItemStack item = attacker.getInventory().getItemInMainHand();	
				
				if (item.getType() == Material.BLAZE_ROD) {
					String stickName = ChatConverter.stripColor(item.getItemMeta().getDisplayName());
					if (stickName.equalsIgnoreCase(IGBatons.SHOCK_BATON.getStripped())) {
						int ticks = TickConverter.getTicksInSeconds(15); //15 seconds.
						PotionEffect effect = new PotionEffect(PotionEffectType.CONFUSION, ticks, 10);
						defender.addPotionEffect(effect);
						Bukkit.getWorld("world").strikeLightningEffect(defender.getLocation());
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
