package us.ignitiongaming.enums;

import org.bukkit.Material;

public enum IGSignItems {
	// <ITEM> (NAME, BUY, SELL)
	IRON ("IRON", 64, 1),
	IRON_INGOT ("IRON_INGOT", 64, 1),
	GOLD ("GOLD", 64, 1),
	DIAMOND ("DIAMOND", 64, 1),
	EMERALD ("EMERALD", 64, 1),
	COAL ("COAL", 64, 1),
	COBBLESTONE ("COBBLESTONE", 64, 1),
	PICKAXE ("PICKAXE", 1, 1),
	PLAYER_SKULL ("SKULL", 0, 1),
	MELON ("MELON", 16, 0),
	STICKS ("STICKS", 16, 0),
	APPLES ("APPLES", 16, 0),
	APPLE ("APPLE", 16, 0);
	
	private String name;
	private int buy, sell;
	
	
	private IGSignItems(String name, int buy, int sell) {
		this.name = name;
		this.buy = buy;
		this.sell = sell;
	}
	
	public String getName() { return name.toUpperCase(); }
	public static IGSignItems getItem(String name) {
		IGSignItems expected = IGSignItems.IRON;
		for (IGSignItems item : IGSignItems.values()) {
			if (item.getName().equalsIgnoreCase(name)) expected = item;
		}
		return expected;
	}
	
	public int getBuyAmount() { return buy; }
	public int getSellAmount() { return sell; }
	
	public String getLabel() { return name; }
	
	/**
	 * Helper function to convert SignOre into associated Material.
	 * @param ore
	 * @return
	 */
	public Material toMaterial() {
		IGSignItems ore = getItem(name);
		Material material = Material.IRON_INGOT;
		switch (ore) {
			case IRON:
			case IRON_INGOT:
				material = Material.IRON_INGOT;
				break;
			case GOLD:
				material = Material.GOLD_INGOT;
				break;
			case DIAMOND:
				material = Material.DIAMOND;
				break;
			case EMERALD:
				material = Material.EMERALD;
				break;
			case COAL:
				material = Material.COAL;
				break;
			case COBBLESTONE:
				material = Material.COBBLESTONE;
				break;
			case PICKAXE:
				material = Material.DIAMOND_PICKAXE;
				break;
			case PLAYER_SKULL:
				material = Material.SKULL_ITEM;
				break;
			case MELON:
				material = Material.MELON;
				break;
			case STICKS:
				material = Material.STICK;
				break;
			case APPLE:
			case APPLES:
				material = Material.APPLE;
				break;
			default:
				material = Material.COBBLESTONE;
				break;
		}
		return material;
	}
}
