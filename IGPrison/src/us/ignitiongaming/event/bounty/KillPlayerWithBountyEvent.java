package us.ignitiongaming.event.bounty;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.config.ServerDefaults;
import us.ignitiongaming.entity.bounty.IGBounty;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.enums.IGBountyProgress;
import us.ignitiongaming.factory.bounty.IGBountyFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.util.convert.CurrencyConverter;

public class KillPlayerWithBountyEvent implements Listener {

	@EventHandler
	public void onPlayerKilledWithBounty(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if (player.getKiller() instanceof Player) {
			Player killer = player.getKiller();
			IGPlayer igKiller = IGPlayerFactory.getIGPlayerByPlayer(killer);
			IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
			List<IGBounty> playerBounties = IGBountyFactory.getBountiesTargettingPlayer(igPlayer, IGBountyProgress.ONGOING);
			
			//Player has no ongoing bounties. Ignore this.
			if (playerBounties.size() == 0) return;
			
			//Handle each bounty.
			for (IGBounty bounty : playerBounties) {
				String currency = CurrencyConverter.convertToCurrency(bounty.getAmount());
				//Reward the killer.
				ServerDefaults.econ.depositPlayer(killer, bounty.getAmount());
				bounty.claimed();
				bounty.setClaimedId(igKiller.getId());
				bounty.save();
				Bukkit.broadcastMessage(GlobalTags.BOUNTY + "§e" + killer.getName() + "§f has claimed §6" + player.getName() + "§f's bounty for §a" + currency);
			}
		}
	
	}
}
