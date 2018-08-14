package us.ignitiongaming.util.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DefianceWeapon {

	public static ItemStack getDefianceSword() {
		ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
		sword.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 3);
		sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 6);
		sword.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
		ItemMeta itemMeta = sword.getItemMeta();
		itemMeta.setDisplayName("§5Defiance Sword");
		itemMeta.setLore(DefianceLore.getGeneralLore());
		sword.setItemMeta(itemMeta);
		return sword;
	}
}
