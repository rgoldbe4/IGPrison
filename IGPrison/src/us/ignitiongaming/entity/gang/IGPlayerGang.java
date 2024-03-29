package us.ignitiongaming.entity.gang;

import java.sql.ResultSet;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.HasID;
import us.ignitiongaming.enums.IGGangRank;

public class IGPlayerGang extends HasID {

	public static final String TABLE_NAME = "player_gang";
	
	private int playerID = -1, gangID = -1, gangRankID = -1;
	
	public void setPlayerId(int playerId) { this.playerID = playerId; }
	public int getPlayerId() { return playerID; }
	
	public void setGangId(int gangId) { this.gangID = gangId; }
	public int getGangId() { return gangID; }
	
	public void setGangRank(IGGangRank igGangRank) { this.gangRankID = igGangRank.getId(); }
	public IGGangRank getGangRank() { return IGGangRank.getRankById(gangRankID); }
	
	public void promote() {
		//Make sure the player is not a leader. Otherwise they can be promoted
		if (getGangRank() != IGGangRank.LEADER) {
			gangRankID++;
			IGGangRank igGangRank = IGGangRank.getRankById(gangRankID);
			setGangRank(igGangRank);
		}
	}
	
	public void demote() {
		//Make sure the player is not a member. Otherwise they can be demoted.
		if (getGangRank() != IGGangRank.MEMBER) {
			gangRankID--;
			IGGangRank igGangRank = IGGangRank.getRankById(gangRankID);
			setGangRank(igGangRank);
		}
	}
	
	public void assign(ResultSet results) {
		try {
			setId(results);
			setPlayerId(results.getInt("playerID"));
			setGangId(results.getInt("gangID"));
			IGGangRank igGangRank = IGGangRank.getRankById(results.getInt("gangRankID"));
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
		query.addId(getId());
		query.execute();
	}
	
	public void delete() {
		SQLQuery query = new SQLQuery(QueryType.DELETE, TABLE_NAME);
		query.addId(getId());
		query.execute();
	}
	
}
