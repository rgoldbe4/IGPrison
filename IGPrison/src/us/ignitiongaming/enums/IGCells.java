package us.ignitiongaming.enums;

import us.ignitiongaming.entity.other.IGCell;
import us.ignitiongaming.factory.other.IGCellFactory;

public enum IGCells {

	A("A", "§8[§cA§8] §r"), B("B", "§8[§eB§8] §r"), C("C", "§8[§2C§8] §r"), D("D", "§8[§5D§8] §r");
	
	private String label;
	private String tag;
	
	private IGCells(String label, String tag) { this.label = label; this.tag = tag; }
	
	public String getLabel() { return label; }
	public String getTag() { return tag; }
	
	public static IGCells getCell(String label) {
		if (!isCell(label)) return IGCells.D;
		IGCells cell = IGCells.D;
		for (IGCells c : IGCells.values()) {
			if (c.getLabel().equalsIgnoreCase(label)) cell = c;
		}
		return cell;
	}
	
	public static boolean isCell(String label) {
		for (IGCells c : IGCells.values()) {
			if (c.getLabel().equalsIgnoreCase(label)) return true;
		}
		return false;
	}
	
	public IGCell toCell() {
		IGCells cell = getCell(label);
		IGCell igCell = IGCellFactory.getCellByIGCells(cell);
		return igCell;
	}
}
