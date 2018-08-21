package us.ignitiongaming.entity.player;

import java.sql.ResultSet;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.HasID;

public class IGPlayerHome extends HasID {

	public static final String TABLE_NAME = "player_home";
	
	private int playerId, homeId;
	
	public void setPlayerId(int playerId) { this.playerId = playerId; }
	public int getPlayerId() { return playerId; }
	
	public void setHomeId(int homeId) { this.homeId = homeId; }
	public int getHomeId() { return homeId; }
	
	public void assign(ResultSet results) {
		try {
			setId(results);
			setPlayerId(results.getInt("playerID"));
			setHomeId(results.getInt("homeID"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean isValid() {
		return !(hasId() || playerId <= 0 || homeId <= 0);
	}
	
	public void save() {
		SQLQuery query = new SQLQuery(QueryType.UPDATE, TABLE_NAME);
		query.addSet("playerID", getPlayerId());
		query.addSet("homeID", getHomeId());
		query.addSet("visible", getVisible());
		query.addId(getId());
		query.execute();
	}
	
	public void delete() {
		SQLQuery query = new SQLQuery(QueryType.UPDATE, TABLE_NAME);
		query.addSet("visible", getVisible());
		query.addId(getId());
		query.execute();
	}
}
