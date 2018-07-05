package us.ignitiongaming.enums;

import org.bukkit.Material;

public enum SignOres {

IRON ("IRON"), GOLD ("GOLD"), DIAMOND ("DIAMOND"), EMERALD ("EMERALD");
	
	private String name;
	
	private SignOres(String name) {
		this.name = name;
	}
	
	public String getName() { return name.toUpperCase(); }
	public static SignOres getItem(String name) {
		SignOres expected = SignOres.IRON;
		for (SignOres item : SignOres.values()) {
			if (item.getName().equalsIgnoreCase(name)) expected = item;
		}
		return expected;
	}
	
	/**
	 * Helper function to convert SignOre into associated Material.
	 * @param ore
	 * @return
	 */
	public Material toMaterial() {
		SignOres ore = getItem(name);
		Material material = Material.IRON_INGOT;
		switch (ore) {
			case IRON:
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
		}
		return material;
	}
}
