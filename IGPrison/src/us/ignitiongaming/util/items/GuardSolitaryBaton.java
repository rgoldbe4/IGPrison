package us.ignitiongaming.util.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import us.ignitiongaming.enums.IGGuardBatons;
import us.ignitiongaming.enums.IGRankNodes;

public class GuardSolitaryBaton {
	
	private ItemStack item = new ItemStack(Material.BONE);
	private ItemMeta itemMeta = item.getItemMeta();
	public GuardSolitaryBaton() {
		setupItemMeta();
	
	}
	
	private void setupItemMeta() {
		itemMeta.setDisplayName(IGGuardBatons.SHOCK_BATON.getTag());
		List<String> lore = new ArrayList<>();
		
		lore.add(IGRankNodes.GUARD.getTag());
		lore.add("§6§lShock Baton");
		itemMeta.setLore(lore);
		
		item.setItemMeta(itemMeta);
	}
	
	public ItemStack getBaton() {
		return item;
	}
	
	
}
