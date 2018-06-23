package us.ignitiongaming.entity.player;

import java.sql.ResultSet;

import us.ignitiongaming.entity.HasID;

public class IGPlayerBanned extends HasID {

	public static final String TABLE_NAME = "player_banned";
	private int playerId;
	private String endDate;
	private String startDate;
	private String reason;
	private int bannerID; //aka The Hulk
	
	public void assign(ResultSet results) {
		try {
			setId(results.getInt("ID"));
			setPlayerId(results.getInt("playerID"));
			setEndDate(results.getString("banEnd"));
			setStartDate(results.getString("banStart"));
			setBanner(results.getInt("staffID"));
			setReason(results.getString("Reason"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void setPlayerId(int playerId) { this.playerId = playerId; }
	public int getPlayerId() { return playerId; }

	public String getEndDate() { return endDate; }
	public void setEndDate(String endDate) { this.endDate = endDate; }

	public String getStartDate() { return startDate;}
	public void setStartDate(String startDate) { this.startDate = startDate;	}

	public String getReason() { return reason; }
	public void setReason(String reason) { this.reason = reason; }

	public int getBanner() { return bannerID; }
	public void setBanner(int banner) { this.bannerID = banner; }
}
