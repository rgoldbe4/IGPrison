package us.ignitiongaming.util.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import us.ignitiongaming.enums.IGBatons;
import us.ignitiongaming.enums.IGRankNodes;

public class StaffBaton {
	public static ItemStack getBaton() {
		ItemStack item = new ItemStack(Material.STICK);
		ItemMeta itemMeta = item.getItemMeta();
		
		item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
		itemMeta.setDisplayName(IGBatons.GUARD_BATON.getTag());
		
		List<String> lore = new ArrayList<>();
		lore.add(IGRankNodes.STAFF.getTag());
		lore.add("Use this to PvP rebelling prisoners.");
		
		itemMeta.setLore(lore);
		
		item.setItemMeta(itemMeta);
		return item;
	}
	
	public static ItemStack getShockBaton() {
		ItemStack item = new ItemStack(Material.BLAZE_ROD);
		ItemMeta itemMeta = item.getItemMeta();
		
		item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
		item.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 10);
		itemMeta.setDisplayName(IGBatons.SHOCK_BATON.getTag());
		List<String> lore = new ArrayList<>();
		
		lore.add(IGRankNodes.STAFF.getTag());
		lore.add("Use this to PvP rebelling prisoners.");
		itemMeta.setLore(lore);
		
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack getSolitaryBaton() {
		ItemStack item = new ItemStack(Material.END_ROD);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(IGBatons.SOLITARY_STICK.getTag());
		
		List<String> lore = new ArrayList<>();
		lore.add(IGRankNodes.SOLITARY.getTag());
		lore.add("Left Click - Enact Solitary");
		lore.add("Right Click - Check For Contraband");
		
		itemMeta.setLore(lore);
		
		item.setItemMeta(itemMeta);
		
		return item;
	}
}
