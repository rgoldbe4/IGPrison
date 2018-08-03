package us.ignitiongaming.entity.player;

import java.sql.ResultSet;

import us.ignitiongaming.entity.HasID;

public class IGPlayerKicked extends HasID {

	public static final String TABLE_NAME = "player_kicked";
	private int playerId;
	private String reason;
	private int kickerID; //aka The Hulk
	
	public void assign(ResultSet results) {
		try {
			setId(results);
			setPlayerId(results.getInt("playerID"));
			setKickerId(results.getInt("staffID"));
			setReason(results.getString("Reason"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void setPlayerId(int playerId) { this.playerId = playerId; }
	public int getPlayerId() { return playerId; }

	public String getReason() { return reason; }
	public void setReason(String reason) { this.reason = reason; }

	public int getKickerId() { return kickerID; }
	public void setKickerId(int banner) { this.kickerID = banner; }
}
