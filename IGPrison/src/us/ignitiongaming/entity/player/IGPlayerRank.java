package us.ignitiongaming.entity.player;

import java.sql.ResultSet;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.HasID;

public class IGPlayerRank extends HasID {

	public static final String TABLE_NAME = "player_rank";
	
	private int playerId = 0, rankId = 0;
	
	public void assign(ResultSet results) {
		try {
			setId(results);
			setPlayerId(results.getInt("playerID"));
			setRankId(results.getInt("rankID"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void save() {
		if (isValid()) {
			SQLQuery query = new SQLQuery(QueryType.UPDATE, TABLE_NAME);
			query.addSet("playerID", playerId);
			query.addSet("rankID", rankId);
			query.addId(getId());
			query.execute();
		}
	}
	public void setPlayerId(int playerId) { this.playerId = playerId; }
	public int getPlayerId() { return playerId; }
	
	public void setRankId(int rankId) { this.rankId = rankId; }
	public int getRankId() { return rankId; }
	
	public boolean isValid() {
		return !(!hasId() || rankId == 0 || playerId == 0);
	}
}
