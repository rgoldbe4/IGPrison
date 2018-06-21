package us.ignitiongaming.entity.player;

import java.sql.ResultSet;

import us.ignitiongaming.entity.HasID;

public class IGPlayerDonator extends HasID {

	public static final String TABLE_NAME = "player_donator";
	
	private int playerId;
	
	public void assign(ResultSet results) {
		try {
			setId(results.getInt("ID"));
			setPlayerId(results.getInt("playerID"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void setPlayerId(int playerId) { this.playerId = playerId; }
	public int getPlayerId() { return playerId; }
	
}
