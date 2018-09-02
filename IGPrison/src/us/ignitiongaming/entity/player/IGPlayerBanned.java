package us.ignitiongaming.entity.player;

import java.sql.ResultSet;
import java.util.Date;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.HasID;
import us.ignitiongaming.util.convert.BooleanConverter;
import us.ignitiongaming.util.convert.DateConverter;

public class IGPlayerBanned extends HasID {

	public static final String TABLE_NAME = "player_banned";
	private int playerId;
	private String endDate;
	private String startDate;
	private String reason;
	private int bannerID; //aka The Hulk
	private int permanent = 0;
	
	public void assign(ResultSet results) {
		try {
			setId(results);
			setPlayerId(results.getInt("playerID"));
			setEndDate(results.getString("banEnd"));
			setStartDate(results.getString("banStart"));
			setBannerId(results.getInt("staffID"));
			setReason(results.getString("Reason"));
			setPermanent(results.getInt("permanent"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void setPlayerId(int playerId) { this.playerId = playerId; }
	public int getPlayerId() { return playerId; }

	public String getEndDate() { return endDate; }
	public void setEndDate(String endDate) { this.endDate = endDate; }
	public Date getEnd() { return DateConverter.convertStringDateTimeToDate(endDate); }

	public String getStartDate() { return startDate;}
	public void setStartDate(String startDate) { this.startDate = startDate; }
	public Date getStart() { return DateConverter.convertStringDateTimeToDate(startDate); }

	public String getReason() { return reason; }
	public void setReason(String reason) { this.reason = reason; }

	public int getBannerId() { return bannerID; }
	public void setBannerId(int banner) { this.bannerID = banner; }
	
	public boolean isPermanent() { return BooleanConverter.getBooleanFromInteger(permanent); }
	public void setPermanent() { permanent = BooleanConverter.getIntegerFromBoolean(true); }
	public void setPermanent(int permanent) { this.permanent = permanent; }
	
	public boolean isBanned() {
		if (BooleanConverter.getBooleanFromInteger(permanent)) return true; //Always banned if permanent;
		Date currentTime = DateConverter.getCurrentTime();
		Date endTime = DateConverter.convertStringDateTimeToDate(endDate);
		
		return !currentTime.after(endTime);
	}
	
	public void save() {
		SQLQuery query = new SQLQuery(QueryType.UPDATE, TABLE_NAME);
		query.addSet("playerID", playerId);
		query.addSet("Reason", reason);
		query.addSet("banStart", startDate);
		query.addSet("banEnd", endDate);
		query.addSet("permanent", permanent);
		query.addId(getId());
		query.execute();
	}
}
