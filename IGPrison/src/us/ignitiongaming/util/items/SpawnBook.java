package us.ignitiongaming.util.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import us.ignitiongaming.enums.IGGuardBatons;

public class SpawnBook {
	private ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
	private ItemMeta itemMeta = item.getItemMeta();
	public SpawnBook() {
		BookGenerator.createBook(itemMeta,"", "Da Rules");
		setupItemMeta();
		
	
	}
	
	private void setupItemMeta() {
		itemMeta.setDisplayName(IGGuardBatons.SHOCK_BATON.getTag());
		List<String> lore = new ArrayList<>();
		
		lore.add("§6§lSpawn Book");
		itemMeta.setLore(lore);
		
		item.setItemMeta(itemMeta);
	}
	
	public ItemStack getBaton() {
		return item;
	}
	
}
