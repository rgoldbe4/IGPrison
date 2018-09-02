package us.ignitiongaming.entity.player;

import java.sql.ResultSet;
import java.util.Date;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.HasID;
import us.ignitiongaming.util.convert.DateConverter;

public class IGPlayerKicked extends HasID {

	public static final String TABLE_NAME = "player_kicked";
	private int playerId;
	private String reason;
	private int kickerID; //aka The Hulk
	private String occurred;
	
	public void assign(ResultSet results) {
		try {
			setId(results);
			setPlayerId(results.getInt("playerID"));
			setKickerId(results.getInt("staffID"));
			setReason(results.getString("Reason"));
			setOccurred(results.getString("occurred"));
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
	
	public String getOccurred() { return occurred; }
	public Date getOccurredDate() { return DateConverter.convertStringDateTimeToDate(occurred); }
	public void setOccurred(String occurred) { this.occurred = occurred; }
	public void setOccurred(Date date) { this.occurred = DateConverter.convertDateToString(date); }
	
	public void save() {
		SQLQuery query = new SQLQuery(QueryType.SELECT, TABLE_NAME);
		query.addSet("playerID", playerId);
		query.addSet("reason", reason);
		query.addSet("staffID", kickerID);
		query.addSet("occurred", occurred);
		query.addId(getId());
		query.execute();
	}
}
