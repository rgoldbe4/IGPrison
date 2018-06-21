package us.ignitiongaming.util.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import us.ignitiongaming.enums.IGGuardBatons;
import us.ignitiongaming.enums.IGRanks;
import us.ignitiongaming.factory.rank.IGRankFactory;

public class GuardShockBaton {
	
	private ItemStack item = new ItemStack(Material.BLAZE_ROD);
	private ItemMeta itemMeta = item.getItemMeta();
	public GuardShockBaton() {
		setupItemMeta();
		item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
		item.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 10);
	
	}
	
	private void setupItemMeta() {
		itemMeta.setDisplayName(IGGuardBatons.SHOCK_BATON.getTag());
		List<String> lore = new ArrayList<>();
		
		lore.add(IGRankFactory.getIGRankByRank(IGRanks.GUARD).getTag());
		lore.add("§6§lShock Baton");
		itemMeta.setLore(lore);
		
		item.setItemMeta(itemMeta);
	}
	
	public ItemStack getBaton() {
		return item;
	}
	
	
}
