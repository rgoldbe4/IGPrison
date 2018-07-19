package us.ignitiongaming.enums;

import org.bukkit.inventory.ItemStack;

import us.ignitiongaming.util.items.Drugs;

public enum IGDrugType {
	AUTO_DROP ("onepunchman");
	
	private String label;
	
	private IGDrugType(String label) { this.label = label; }
	
	public String getLabel() { return label; }
	
	public ItemStack toDrug() {
		switch (this) {
			case AUTO_DROP:
				return Drugs.getAutoDrop();
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
