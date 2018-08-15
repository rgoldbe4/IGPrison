package us.ignitiongaming.event.gang;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import us.ignitiongaming.IGPrison;
import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.enums.IGDrugType;
import us.ignitiongaming.singleton.IGList;
import us.ignitiongaming.util.convert.TickConverter;

public class DrugUseEvent implements Listener {

	@EventHandler
	public static void onFastMiningUse(PlayerInteractEvent event) {
		try {
			//Ensure the user is attempting to consume the drug via right click.
			if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

				Player player = (Player) event.getPlayer();
				if (player.getInventory().getItemInMainHand() == null) return;
				
				ItemStack itemInHand = player.getInventory().getItemInMainHand();
				
				//#15 - Exception when right clicking with bare hand. Check for nulls in this specific instance.
				if (itemInHand.getItemMeta() == null) return;
				if (itemInHand.getItemMeta().getLore() == null) return;
				
				//Determine if player is using auto drop.
				if (itemInHand.getItemMeta().getLore().get(0).equalsIgnoreCase(GlobalTags.DRUGS + IGDrugType.AUTO_DROP.getTitle())) {
					
					if (!IGList.drillUse.contains(player)) {
						//Ensure we don't delete an entire stack for nothing...
						if (itemInHand.getAmount() > 1)
							itemInHand.setAmount(itemInHand.getAmount() - 1);
						else
							player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
						
						//Add default speed for 10 seconds.
						player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, TickConverter.getTicksInSeconds(10), 0));
						player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, TickConverter.getTicksInSeconds(10), 0));
						
						player.sendMessage(GlobalTags.DRUGS + "You have consumed: " + IGDrugType.AUTO_DROP.getTitle());
						
						//Add them to the list.
						IGList.drillUse.add(player);
						//Now, in 15 seconds, cancel the event.
						Bukkit.getScheduler().runTaskLater(IGPrison.plugin, new Runnable()
						{
							@Override
							public void run(){
								if (IGList.drillUse.contains(player))
								{
									IGList.drillUse.remove(player);
								}
							}
						}, TickConverter.getTicksInSeconds(10));
					} else {
						player.sendMessage(GlobalTags.DRUGS + "§4You currently have this drug in use.");
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@EventHandler
	public static void onDrillClick(PlayerInteractEvent event) {
		try {
			if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
				if (IGList.drillUse.contains(event.getPlayer())) {
					Block clickedBlock = event.getClickedBlock();
					if (clickedBlock.getType() == Material.STONE 
							|| clickedBlock.getType() == Material.IRON_ORE 
							|| clickedBlock.getType() == Material.DIAMOND_ORE
							|| clickedBlock.getType() == Material.GRASS 
							|| clickedBlock.getType() == Material.EMERALD_ORE 
							|| clickedBlock.getType() == Material.COAL_ORE
							|| clickedBlock.getType() == Material.COBBLESTONE 
							|| clickedBlock.getType() == Material.GOLD_ORE) {
						event.getClickedBlock().breakNaturally();
					}
				}
			}
		}
		catch (Exception ex) {
			
		}
	}
	
	@EventHandler
	public static void onWarriorUse(PlayerInteractEvent event) {
		try {
			//Ensure the user is attempting to consume the drug via right click.
			if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

				Player player = (Player) event.getPlayer();
				if (player.getInventory().getItemInMainHand() == null) return;
				
				ItemStack itemInHand = player.getInventory().getItemInMainHand();
				
				//#15 - Exception when right clicking with bare hand. Check for nulls in this specific instance.
				if (itemInHand.getItemMeta() == null) return;
				if (itemInHand.getItemMeta().getLore() == null) return;
				
				//Determine if player is using warrior.
				if (itemInHand.getItemMeta().getLore().get(0).equalsIgnoreCase(GlobalTags.DRUGS + IGDrugType.WARRIOR.getTitle())) {
					//Ensure we don't delete an entire stack for nothing...
					if (itemInHand.getAmount() > 1)
						itemInHand.setAmount(itemInHand.getAmount() - 1);
					else
						player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
					
					//Add default speed for 10 seconds.
					player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, TickConverter.getTicksInSeconds(10), 0));
					player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, TickConverter.getTicksInSeconds(10), 0));
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, TickConverter.getTicksInSeconds(10), 0));
					player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, TickConverter.getTicksInSeconds(10), 0));
					
					player.sendMessage(GlobalTags.DRUGS + "You have consumed: " + IGDrugType.WARRIOR.getTitle());
					
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
