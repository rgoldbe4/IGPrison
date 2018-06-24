package us.ignitiongaming.util.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import us.ignitiongaming.enums.IGGuardBatons;
import us.ignitiongaming.enums.IGRankNodes;

public class GuardBaton {
	
	private ItemStack item = new ItemStack(Material.STICK);
	private ItemMeta itemMeta = item.getItemMeta();
	public GuardBaton() {
		setupItemMeta();
		item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
		item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
	
	}
	
	private void setupItemMeta() {
		itemMeta.setDisplayName(IGGuardBatons.GUARD_BATON.getTag());
		List<String> lore = new ArrayList<>();	
		lore.add(IGRankNodes.GUARD.getTag());
		lore.add("§6§lGuard Baton");
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
	}
	
	public ItemStack getBaton() {
		return item;
	}
	
	
}
