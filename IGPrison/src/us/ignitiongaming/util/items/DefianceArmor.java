package us.ignitiongaming.util.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DefianceArmor {

	public static ItemStack getHelmet() {
		ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
		helmet = addOverallEnchantments(helmet);
		helmet.addUnsafeEnchantment(Enchantment.OXYGEN, 4);
		helmet.addUnsafeEnchantment(Enchantment.WATER_WORKER, 2);
		ItemMeta itemMeta = helmet.getItemMeta();
		itemMeta.setDisplayName("§5Defiance Helmet");
		itemMeta.setLore(DefianceLore.getGeneralLore());
		helmet.setItemMeta(itemMeta);
		return helmet;
	}
	
	public static ItemStack getChestplate() {
		ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
		chestplate = addOverallEnchantments(chestplate);
		ItemMeta itemMeta = chestplate.getItemMeta();
		itemMeta.setDisplayName("§5Defiance Chestplate");
		itemMeta.setLore(DefianceLore.getGeneralLore());
		chestplate.setItemMeta(itemMeta);
		return chestplate;
	}
	
	public static ItemStack getLeggings() {
		ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
		leggings = addOverallEnchantments(leggings);
		ItemMeta itemMeta = leggings.getItemMeta();
		itemMeta.setDisplayName("§5Defiance Leggings");
		itemMeta.setLore(DefianceLore.getGeneralLore());
		leggings.getItemMeta().setDisplayName("§5Defiance Leggings");
		leggings.setItemMeta(itemMeta);
		return leggings;
	}
	
	public static ItemStack getBoots() {
		ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
		boots = addOverallEnchantments(boots);
		boots.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 4);
		boots.addUnsafeEnchantment(Enchantment.FROST_WALKER, 2);
		boots.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 5);
		ItemMeta itemMeta = boots.getItemMeta();
		itemMeta.setDisplayName("§5Defiance Boots");
		itemMeta.setLore(DefianceLore.getGeneralLore());
		boots.getItemMeta().setDisplayName("§5Defiance Boots");
		boots.setItemMeta(itemMeta);
		return boots;
	}
	
	private static ItemStack addOverallEnchantments(ItemStack item) {
		item.addUnsafeEnchantment(Enchantment.THORNS, 1);
		item.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 5);
		item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
		item.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 5);
		item.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 5);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
		return item;
	}
}
