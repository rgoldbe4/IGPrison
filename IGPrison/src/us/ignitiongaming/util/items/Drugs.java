package us.ignitiongaming.util.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import us.ignitiongaming.config.ServerDefaults;
import us.ignitiongaming.enums.IGDrugType;
import us.ignitiongaming.enums.IGSettings;

public class Drugs {

	
	private static ItemStack cactus = new ItemStack(Material.CACTUS);
	private static ItemStack sugarCane = new ItemStack(Material.SUGAR_CANE);
	
	
	public static ItemStack getAutoDrop() {
		int drugAmount = Integer.parseInt(ServerDefaults.getSetting(IGSettings.DEFAULT_DRUG_AMOUNT).getValue().toString());
		return getAutoDrop(drugAmount * 16);
	}
	
	public static ItemStack getAutoDrop(int amount) {
		//Update the amount...
		amount = amount / 16; // 64 cactus = 4 auto drop.
		ItemMeta cactusMeta = cactus.getItemMeta();
		cactusMeta.setLore(DrugLore.getAutoDropLore());
		cactusMeta.setDisplayName(IGDrugType.AUTO_DROP.getTitle());
		cactus.setItemMeta(cactusMeta);
		cactus.setAmount(amount);
		
		return cactus;
	}
	
	public static ItemStack getWarrior() {
		int drugAmount = Integer.parseInt(ServerDefaults.getSetting(IGSettings.DEFAULT_DRUG_AMOUNT).getValue().toString());
		return getWarrior(drugAmount * 16);
	}
	
	public static ItemStack getWarrior(int amount) {
		amount = amount / 16; // 64 sugar cane = 4 warrior.
		ItemMeta itemMeta = sugarCane.getItemMeta();
		itemMeta.setLore(DrugLore.getWarriorLore());
		itemMeta.setDisplayName(IGDrugType.WARRIOR.getTitle());
		sugarCane.setItemMeta(itemMeta);
		sugarCane.setAmount(amount);
		
		return sugarCane;
	}
}
