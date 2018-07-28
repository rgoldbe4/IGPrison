package us.ignitiongaming.enums;

public enum IGBountyProgress {
	ONGOING(0, "§eONGOING"), 
	CLAIMED(1, "§2CLAIMED"), 
	CANCELLED(2, "§4CANCELLED");
	
	private int id;
	private String label;
	private IGBountyProgress(int id, String label) { 
		this.id = id; 
		this.label = label;
	}
	
	public int getId() { 
		return id;
	}
	
	public String getLabel() { 
		return label;
	}
	
	public static IGBountyProgress getProgressById(int id) {
		for (IGBountyProgress progress : IGBountyProgress.values()) {
			if (progress.getId() == id) return progress;
		}
		return IGBountyProgress.ONGOING;
	}
}
