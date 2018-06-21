package us.ignitiongaming.enums;

public enum IGGuardBatons {
	SHOCK_BATON("§6§lSHOCK BATON"), BATON("§2GUARD BATON");
	
	private String tag;
	
	private IGGuardBatons(String tag) { this.tag = tag; }
	
	public String getTag() { return tag; }
	
	
}
