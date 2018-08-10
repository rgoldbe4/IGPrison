package us.ignitiongaming.enums;

public enum IGGangRank {
	MEMBER (1, "§6Member§r"),
	OFFICER (2, "§bOfficer§r"),
	LEADER (3, "§cLeader§r");
	
	private int ID;
	private String label;
	
	private IGGangRank(int ID, String label) { 
		this.ID = ID;
		this.label = label;
	}
	
	public int getId() { return ID; }
	public String getLabel() { return label; }
	
	public static IGGangRank getRankById(int ID) {
		IGGangRank rank = IGGangRank.MEMBER;
		for (IGGangRank igGangRank : IGGangRank.values()) {
			if (igGangRank.getId() == ID) rank = igGangRank;
		}
		return rank;
	}
}
