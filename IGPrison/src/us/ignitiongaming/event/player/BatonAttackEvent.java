package us.ignitiongaming.event.player;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import us.ignitiongaming.enums.IGBatons;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.util.convert.ChatConverter;

public class BatonAttackEvent implements Listener {

	@EventHandler
	public static void onBatonAttack(EntityDamageByEntityEvent event) {
		try {
			Bukkit.broadcastMessage("Testing...");
			//Ignore the event if it's not player on player.
			if (!(event.getEntity() instanceof Player)) return;
			if (!(event.getDamager() instanceof Player)) return;
			
			Player attacker = (Player) event.getEntity();
			Player defender = (Player) event.getDamager();
			
			IGRankNodes attackerRank = IGRankNodes.getPlayerRank(attacker);
			
			if (attackerRank.isStaff()) {
				Bukkit.broadcastMessage("It Hit here");
				//If item in hand doesn't exist.
				if (attacker.getInventory().getItemInMainHand() == null) return;
				if (attacker.getInventory().getItemInMainHand().getItemMeta() == null) return;
				
				ItemStack item = attacker.getInventory().getItemInMainHand();				
				
				if (item.getType() == Material.STICK) {
					String stickName = ChatConverter.stripColor(item.getItemMeta().getDisplayName());
					if (stickName.equalsIgnoreCase(IGBatons.GUARD_BATON.getStripped())) {
						//Remove 1 heart from the player each hit.
						defender.setHealth(defender.getHealth() - 2);
					}
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
