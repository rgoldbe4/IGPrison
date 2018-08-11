package us.ignitiongaming.enums;

import org.bukkit.inventory.ItemStack;

import us.ignitiongaming.util.items.Drugs;

public enum IGDrugType {
	AUTO_DROP ("fastmining", "§eFast Mining"),
	WARRIOR ("warrior", "§dWarrior");
	
	private String label, title;
	
	private IGDrugType(String label, String title) { this.label = label; this.title = title; }
	
	public String getLabel() { return label; }
	public String getTitle() { return title; }
	
	public ItemStack toDrug() {
		switch (this) {
			case AUTO_DROP:
				return Drugs.getAutoDrop();
			case WARRIOR:
				return Drugs.getWarrior();
			default:
				return null;	
		}
	}
	
	public static boolean isDrugType(String label) {
		for (IGDrugType drugType : IGDrugType.values()) {
			if (drugType.getLabel().equalsIgnoreCase(label)) return true;
		}
		return false;
	}
	
	public static IGDrugType getDrugByLabel(String label) {
		for (IGDrugType drugType : IGDrugType.values()) {
			if (drugType.getLabel().equalsIgnoreCase(label)) return drugType;
		}
		return null;
	}
}
