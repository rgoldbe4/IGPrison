package us.ignitiongaming.entity.user;

import java.sql.ResultSet;

import us.ignitiongaming.database.QueryType;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.entity.HasID;
/**
 * This object is strictly meant for Website Ignition Gaming interaction. Not meant for Minecraft plugin.
 *
 */
public class IGUser extends HasID {

	public static final String TABLE_NAME = "user";
	
	private int playerID = 0;
	//Also has columns: username, password
	//However, both of these columns should never be accessed unless future code requires it. For now, do not send it...
	
	public void setPlayerID(int playerID) { this.playerID = playerID; }
	public int getPlayerID() { return playerID; }
	public boolean hasPlayerID() { return playerID != 0; }
	
	public boolean isValid() { return hasPlayerID(); }
	
	public void save() { 
		try {
			SQLQuery query = new SQLQuery(QueryType.UPDATE, TABLE_NAME);
			query.addID(getId());
			query.addSet("playerID", playerID);
			query.execute();
		} catch (Exception ex) {
			
		}
	}
	
	public void assign(ResultSet results) {
		try {
			setId(results);
			setPlayerID(results.getInt("playerID"));
		} catch (Exception ex) {
			
		}
	}
	
}
