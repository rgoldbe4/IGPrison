package us.ignitiongaming.util.items;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import us.ignitiongaming.enums.IGWardenArmor;

public class WardenArmor {

	private static ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
	private static ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
	private static ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
	private static ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
	
	public static ItemStack getHelmet() {
		/* Setup Item Meta */
		ItemMeta itemMeta = helmet.getItemMeta();
		itemMeta.setDisplayName(IGWardenArmor.HELMET.getLabel());
		itemMeta.setLore(WardenArmorLore.getHelmetLore());
		helmet.setItemMeta(itemMeta);
		
		/* Setup Enchantments */
		helmet.addEnchantments(getUnsafeEnchantments());
		return helmet;
	}
	
	public static ItemStack getChestplate() {
		/* Setup Item Meta */
		ItemMeta itemMeta = chestplate.getItemMeta();
		itemMeta.setDisplayName(IGWardenArmor.CHESTPLATE.getLabel());
		itemMeta.setLore(WardenArmorLore.getChestplateLore());
		chestplate.setItemMeta(itemMeta);
		
		/* Setup Enchantments */
		chestplate.addEnchantments(getUnsafeEnchantments());
		return chestplate;
	}
	
	public static ItemStack getLeggings() {
		/* Setup Item Meta */
		ItemMeta itemMeta = leggings.getItemMeta();
		itemMeta.setDisplayName(IGWardenArmor.LEGGINGS.getLabel());
		itemMeta.setLore(WardenArmorLore.getLeggingLore());
		leggings.setItemMeta(itemMeta);
		
		/* Setup Enchantments */
		leggings.addEnchantments(getUnsafeEnchantments());
		return leggings;
	}
	
	public static ItemStack getBoots() {
		/* Setup Item Meta */
		ItemMeta itemMeta = boots.getItemMeta();
		itemMeta.setDisplayName(IGWardenArmor.BOOTS.getLabel());
		itemMeta.setLore(WardenArmorLore.getBootsLore());
		boots.setItemMeta(itemMeta);
		
		/* Setup Enchantments */
		boots.addEnchantments(getUnsafeEnchantments());
		return boots;
	}
	
	public static Map<Enchantment, Integer> getUnsafeEnchantments() {
		Map<Enchantment, Integer> enchantments = new HashMap<>();
		try {
			/* Protections */
			enchantments.put(Enchantment.PROTECTION_ENVIRONMENTAL, 7);
			enchantments.put(Enchantment.PROTECTION_EXPLOSIONS, 7);
			enchantments.put(Enchantment.PROTECTION_FALL, 7);
			enchantments.put(Enchantment.PROTECTION_FIRE, 7);
			enchantments.put(Enchantment.PROTECTION_PROJECTILE, 7);
			
			/* Other */
			enchantments.put(Enchantment.BINDING_CURSE, 10);
			enchantments.put(Enchantment.THORNS, 7);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return enchantments;
	}
}
