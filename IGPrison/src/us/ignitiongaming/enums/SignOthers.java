package us.ignitiongaming.enums;

public enum SignOthers {

	PICKAXE ("PICKAXE"), HEADS ("HEADS");
	
	private String label;
	
	private SignOthers(String label) { 
		this.label = label;
	}
	
	public String getLabel() { return label; }
	
}
