package us.ignitiongaming.event.other;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import us.ignitiongaming.enums.IGBatons;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.util.convert.ChatConverter;

public class BatonAttackEvent implements Listener {

	
	public static void onBatonAttack(EntityDamageByEntityEvent event) {
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
				
				if (item.getType() == Material.STICK) {
					String stickName = ChatConverter.stripColor(item.getItemMeta().getDisplayName());
					if (stickName.equalsIgnoreCase(IGBatons.GUARD_BATON.getStripped())) {
						//Remove 1.5 heart from the player each hit.
						defender.setHealth(defender.getHealth() - 2.5);
					}
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
