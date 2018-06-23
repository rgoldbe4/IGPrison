package us.ignitiongaming.entity.player;

import java.sql.ResultSet;

import us.ignitiongaming.database.QueryType;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.entity.HasID;

public class IGPlayerNickname extends HasID {

	public static final String TABLE_NAME = "player_nickname";
	
	private int playerId;
	private String nickname;
	private String actualName;
	
	public void assign(ResultSet results) {
		try {
			setId(results.getInt("ID"));
			setPlayerId(results.getInt("playerID"));
			setNickname(results.getString("nickname"));
			setActualName(results.getString("actualname"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void setPlayerId(int playerId) { this.playerId = playerId; }
	public int getPlayerId() { return playerId; }

	public String getNickname() { return nickname; }
	public void setNickname(String nickname) { this.nickname = nickname; }

	public String getActualName() {	return actualName; }
	public void setActualName(String actualName) { this.actualName = actualName; }
	
	public void save() {
		try {
			//Determine if all values are filled.
			if (isValid()) {
				SQLQuery query = new SQLQuery(QueryType.UPDATE, TABLE_NAME);
				query.addSet("nickname", nickname);
				query.addSet("actualname", actualName);
				query.addSet("playerID", playerId);
				query.addWhere("ID", getId());
				query.execute();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private boolean isValid() {
		return (playerId > 0 && nickname != null && actualName != null && hasId());
	}
	
}