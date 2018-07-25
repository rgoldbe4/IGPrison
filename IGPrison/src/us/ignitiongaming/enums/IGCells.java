package us.ignitiongaming.enums;

import us.ignitiongaming.entity.other.IGCell;
import us.ignitiongaming.factory.other.IGCellFactory;

public enum IGCells {

	A("A"), B("B"), C("C"), D("D");
	
	private String label;
	
	private IGCells(String label) { this.label = label; }
	
	public String getLabel() { return label; }
	
	public static IGCells getCell(String label) {
		IGCells cell = IGCells.D;
		for (IGCells c : IGCells.values()) {
			if (c.getLabel().equalsIgnoreCase(label)) cell = c;
		}
		return cell;
	}
	
	public IGCell toCell() {
		IGCells cell = getCell(label);
		IGCell igCell = IGCellFactory.getCellByIGCells(cell);
		return igCell;
	}
}
