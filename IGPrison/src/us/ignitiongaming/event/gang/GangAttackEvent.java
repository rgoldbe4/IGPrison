package us.ignitiongaming.event.gang;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.entity.gang.IGPlayerGang;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.factory.gang.IGPlayerGangFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;

public class GangAttackEvent implements Listener {

	@EventHandler
	public static void OnGangMemberAttackFellowGangMember(EntityDamageByEntityEvent event) {
		//Ensure that it's Player on Player combat.
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			Player attacker = (Player) event.getDamager();
			Player defender = (Player) event.getEntity();
			IGPlayer igAttacker = IGPlayerFactory.getIGPlayerByPlayer(attacker);
			IGPlayer igDefender = IGPlayerFactory.getIGPlayerByPlayer(defender);
			
			boolean isAttackerInGang = IGPlayerGangFactory.isPlayerInGang(igAttacker);
			boolean isDefenderInGang = IGPlayerGangFactory.isPlayerInGang(igDefender);
			
			//Determine if both players are in a gang
			if (isAttackerInGang && isDefenderInGang) {
				IGPlayerGang igAttackerGang = IGPlayerGangFactory.getPlayerGangFromPlayer(igAttacker);
				IGPlayerGang igDefenderGang = IGPlayerGangFactory.getPlayerGangFromPlayer(igDefender);
				
				if (igAttackerGang.getGangID() == igDefenderGang.getGangID()) {
					attacker.sendMessage(GlobalTags.GANG + "§4You cannot attack a fellow gang member.");
				}
			}
			
			
		}
	}
}
