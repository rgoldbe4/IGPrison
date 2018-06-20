package us.ignitiongaming.entity.player;

import java.sql.ResultSet;
import java.util.Date;

import us.ignitiongaming.entity.HasID;
import us.ignitiongaming.util.convert.DateConverter;

public class IGPlayerSolitary extends HasID {

	public static final String TABLE_NAME = "player_solitary";
	
	public int playerId = 0;
	public String start, end;
	
	public void assign(ResultSet results) {
		try {
			setId(results.getInt("ID"));
			setPlayerId(results.getInt("playerID"));
			setStart(results.getString("start"));
			setEnd(results.getString("end"));
		} catch (Exception ex) {
			
		}
	}
	
	public void setPlayerId(int playerId) { this.playerId = playerId; }
	public int getPlayerId() { return playerId; }
	
	public String getStart() { return start; }
	public void setStart(String start) { this.start = start; }
	public Date getStartDate() { return DateConverter.convertStringDateTimeToDate(start); }
	public void setStart(Date start) { this.start = DateConverter.convertDateToString(start); }
	public String getStartFriendly() { return DateConverter.toFriendlyDate(start); }
	
	public String getEnd() { return end; }
	public void setEnd(String end) { this.end = end; }
	public Date getEndDate() { return DateConverter.convertStringDateTimeToDate(end); }
	public void setEnd(Date end) { this.end = DateConverter.convertDateToString(end); }
	public String getEndFriendly() { return DateConverter.toFriendlyDate(end); }
	
	/* Helper Methods */
	public boolean hasServed() {
		Date current = DateConverter.getCurrentTime();
		Date end = getEndDate();
		return (current.after(end)); //Logic: if current time is after the end, they have served.
	}
	
	public boolean isValid() {
		return !(!hasId() || start == null || end == null || playerId == 0);
	}
}
