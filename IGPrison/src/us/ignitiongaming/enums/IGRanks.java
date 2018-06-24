package us.ignitiongaming.enums;

public enum IGRanks {
	//Table labels.
	D ("D"), C ("C"), B ("B"), A("A"), FREE ("Free"), GUARD ("Guard"), WARDEN ("Warden"), STAFF ("Staff");
	
	private String name;
	
	IGRanks(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
