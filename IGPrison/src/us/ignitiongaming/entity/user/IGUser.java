package us.ignitiongaming.entity.user;

import java.sql.ResultSet;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.HasID;
/**
 * This object is strictly meant for Website Ignition Gaming interaction. Not meant for Minecraft plugin.
 *
 */
public class IGUser extends HasID {

	public static final String TABLE_NAME = "user";
	
	private int playerId = 0;
	//Also has columns: username, password
	//However, both of these columns should never be accessed unless future code requires it. For now, do not send it...
	
	public void setPlayerId(int playerId) { this.playerId = playerId; }
	public int getPlayerId() { return playerId; }
	public boolean hasPlayerId() { return playerId != 0; }
	
	public boolean isValid() { return hasPlayerId(); }
	
	public void save() { 
		try {
			SQLQuery query = new SQLQuery(QueryType.UPDATE, TABLE_NAME);
			query.addSet("playerID", playerId);
			query.addId(getId());
			query.execute();
		} catch (Exception ex) {
			
		}
	}
	
	public void assign(ResultSet results) {
		try {
			setId(results);
			setPlayerId(results.getInt("playerID"));
		} catch (Exception ex) {
			
		}
	}
	
}
