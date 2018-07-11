package us.ignitiongaming.enums;

public enum IGGangRank {
	MEMBER (1, "Member"),
	OFFICER (2, "Officer"),
	LEADER (3, "Leader");
	
	private int ID;
	private String label;
	
	private IGGangRank(int ID, String label) { 
		this.ID = ID;
		this.label = label;
	}
	
	public int getId() { return ID; }
	public String getLabel() { return label; }
	
	public static IGGangRank getRankByID(int ID) {
		IGGangRank rank = IGGangRank.MEMBER;
		for (IGGangRank igGangRank : IGGangRank.values()) {
			if (igGangRank.getId() == ID) rank = igGangRank;
		}
		return rank;
	}
}
