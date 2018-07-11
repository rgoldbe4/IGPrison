package us.ignitiongaming.entity.gang;

import java.sql.ResultSet;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.HasID;
import us.ignitiongaming.enums.IGGangRank;

public class IGPlayerGang extends HasID {

	public static final String TABLE_NAME = "player_gang";
	
	private int playerID = -1, gangID = -1, gangRankID = -1;
	
	public void setPlayerID(int playerID) { this.playerID = playerID; }
	public int getPlayerID() { return playerID; }
	
	public void setGangID(int gangID) { this.gangID = gangID; }
	public int getGangID() { return gangID; }
	
	public void setGangRank(IGGangRank igGangRank) { this.gangRankID = igGangRank.getId(); }
	public IGGangRank getGangRank() { return IGGangRank.getRankByID(gangRankID); }
	
	public void assign(ResultSet results) {
		try {
			setId(results);
			setPlayerID(results.getInt("playerID"));
			setGangID(results.getInt("gangID"));
			IGGangRank igGangRank = IGGangRank.getRankByID(results.getInt("gangRankID"));
			setGangRank(igGangRank);			
		} catch (Exception ex) {
			
		}
	}
	
	public boolean isValid() {
		return !(!hasId() || playerID == -1 || gangID == -1 || gangRankID == -1);
	}
	
	public void save() {
		SQLQuery query = new SQLQuery(QueryType.UPDATE, TABLE_NAME);
		query.addSet("playerID", playerID);
		query.addSet("gangID", gangID);
		query.addSet("gangRankID", gangRankID);
		query.addID(getId());
		query.execute();
	}
	
}
