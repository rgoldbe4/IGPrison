package us.ignitiongaming.util.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import us.ignitiongaming.config.ServerDefaults;
import us.ignitiongaming.enums.IGSettings;

public class Drugs {

	
	private static ItemStack cactus = new ItemStack(Material.CACTUS);
	
	
	public static ItemStack getAutoDrop() {
		/* Item Meta */
		ItemMeta cactusMeta = cactus.getItemMeta();
		cactusMeta.setLore(DrugLore.getAutoDropLore());
		cactusMeta.setDisplayName("§eOne Punch Man");
		cactus.setItemMeta(cactusMeta);
		
		//Default drug amount
		int drugAmount = Integer.parseInt(ServerDefaults.getSetting(IGSettings.DEFAULT_DRUG_AMOUNT).getValue().toString());
		cactus.setAmount(drugAmount);
		
		return cactus;
		
	}
}
