package us.ignitiongaming.enums;

public enum IGGangRank {
	MEMBER (1, "§7§lMember§r"),
	OFFICER (2, "§a§lOfficer§r"),
	LEADER (3, "§b§lLeader§r");
	
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
