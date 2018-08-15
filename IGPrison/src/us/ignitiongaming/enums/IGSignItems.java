package us.ignitiongaming.enums;

import org.bukkit.Material;

public enum IGSignItems {
	IRON ("IRON", 64),
	IRON_INGOT ("IRON_INGOT", 64),
	GOLD ("GOLD", 64),
	DIAMOND ("DIAMOND", 64),
	EMERALD ("EMERALD", 64),
	COAL ("COAL", 64),
	COBBLESTONE ("COBBLESTONE", 64),
	PICKAXE ("PICKAXE", 1),
	PLAYER_SKULL ("SKULL", 0),
	MELON ("MELON", 16),
	STICKS ("STICKS", 16),
	APPLES ("APPLES", 16),
	APPLE ("APPLE", 16);
	
	private String name;
	private int amount;
	
	
	private IGSignItems(String name, int amount) {
		this.name = name;
		this.amount = amount;
	}
	
	public String getName() { return name.toUpperCase(); }
	public static IGSignItems getItem(String name) {
		IGSignItems expected = IGSignItems.IRON;
		for (IGSignItems item : IGSignItems.values()) {
			if (item.getName().equalsIgnoreCase(name)) expected = item;
		}
		return expected;
	}
	
	public int getAmount() { return amount; }
	
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
