package us.ignitiongaming.util.handy;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import us.ignitiongaming.enums.IGMenuItems;
import us.ignitiongaming.enums.IGMenus;

public class InventoryMenu {

	public static Inventory getBuyCommandMenu(Player player) {
		Inventory menu = Bukkit.createInventory(null, 27, IGMenus.BUY_COMMANDS.getName());
		ItemStack close = new ItemStack(Material.BARRIER);
		ItemStack bedrock = new ItemStack(Material.BEDROCK);
		ItemStack fix = new ItemStack(Material.ANVIL);
		ItemStack workbench = new ItemStack(Material.WORKBENCH);
		ItemStack msg = new ItemStack(Material.NAME_TAG);
		ItemStack nickname = new ItemStack(Material.NAME_TAG);
		ItemStack enderchest = new ItemStack(Material.ENDER_CHEST);
		ItemMeta closeMeta = close.getItemMeta();
		ItemMeta bedrockMeta = bedrock.getItemMeta();
		ItemMeta fixMeta = fix.getItemMeta();
		ItemMeta workbenchMeta = workbench.getItemMeta();
		ItemMeta msgMeta = msg.getItemMeta();
		ItemMeta nicknameMeta = nickname.getItemMeta();
		ItemMeta enderchestMeta = enderchest.getItemMeta();
		
		// -- /fix --
		fixMeta.setLore(IGMenuItems.FIX.getLore());
		fixMeta.setDisplayName(IGMenuItems.FIX.getDisplayName());
		fix.setItemMeta(fixMeta);
		
		// -- /workbench --
		workbenchMeta.setLore(IGMenuItems.WORKBENCH.getLore());
		workbenchMeta.setDisplayName(IGMenuItems.WORKBENCH.getDisplayName());
		workbench.setItemMeta(workbenchMeta);
		
		// -- /msg --
		msgMeta.setLore(IGMenuItems.MSG.getLore());
		msgMeta.setDisplayName(IGMenuItems.MSG.getDisplayName());
		msg.setItemMeta(msgMeta);
		
		// -- /nickname --
		nicknameMeta.setLore(IGMenuItems.NICKNAME.getLore());
		nicknameMeta.setDisplayName(IGMenuItems.NICKNAME.getDisplayName());
		nickname.setItemMeta(nicknameMeta);
		
		// -- /enderchest --
		enderchestMeta.setLore(IGMenuItems.ENDERCHEST.getLore());
		enderchestMeta.setDisplayName(IGMenuItems.ENDERCHEST.getDisplayName());
		enderchest.setItemMeta(enderchestMeta);
		
		// -- Bedrock (Already bought it) --
		bedrockMeta.setDisplayName(IGMenuItems.PURCHASED.getDisplayName());
		bedrock.setItemMeta(bedrockMeta);
		
		// -- Close --
		closeMeta.setDisplayName(IGMenuItems.CLOSE.getDisplayName());
		close.setItemMeta(closeMeta);
		
		// Add items to inventory menu.
		menu.setItem(8, close);
		menu.setItem(11, player.hasPermission(IGMenuItems.FIX.getNode()) ? bedrock : fix);
		menu.setItem(12, player.hasPermission(IGMenuItems.WORKBENCH.getNode()) ? bedrock : workbench);
		menu.setItem(13, player.hasPermission(IGMenuItems.MSG.getNode()) ? bedrock : msg);
		menu.setItem(14, player.hasPermission(IGMenuItems.NICKNAME.getNode()) ? bedrock : nickname);
		menu.setItem(15, player.hasPermission(IGMenuItems.ENDERCHEST.getNode()) ? bedrock : enderchest);
		
		return menu;
	}
	
	public static Inventory getBuyDefianceMenu(Player player) {
		Inventory menu = Bukkit.createInventory(null, 27, IGMenus.DEFIANCE_POINTS.getName());
		ItemStack close = new ItemStack(Material.BARRIER);
		ItemMeta closeMeta = close.getItemMeta();
		
		// -- Close --
		closeMeta.setDisplayName(IGMenuItems.CLOSE.getDisplayName());
		close.setItemMeta(closeMeta);
				
		menu.setItem(8, close);
		
		return menu;
	}
}
