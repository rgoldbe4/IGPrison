package us.ignitiongaming.entity.user;

import java.sql.ResultSet;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.HasID;
import us.ignitiongaming.util.convert.BooleanConverter;

public class IGLink extends HasID {

	public static final String TABLE_NAME = "user_minecraft";
	
	private int playerID = 0, userID = 0, confirm = -1;
	private String code;
	
	public void setPlayerID(int playerID) { this.playerID = playerID; }
	public int getPlayerID() { return playerID; }
	
	public void setUserID(int userID) { this.userID = userID; }
	public int getUserID() { return userID; }
	
	public void setCode(String code) { this.code = code; }
	public String getCode() { return code; }
	
	public void setConfirm(boolean confirm) { this.confirm = BooleanConverter.getIntegerFromBoolean(confirm); }
	public boolean isConfirmed() { return BooleanConverter.getBooleanFromInteger(confirm); }
	public int getConfirmed() { return confirm; }
	
	public void assign(ResultSet results) {
		try {
			setId(results);
			setPlayerID(results.getInt("playerID"));
			setUserID(results.getInt("userID"));
			setCode(results.getString("code"));
			setConfirm(BooleanConverter.getBooleanFromInteger(results.getInt("confirm"))); //Convert 0 => false, 1 => true
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean isValid() {
		return (!hasId() || playerID == 0 || userID == 0 || code == null || confirm == -1);
	}
	
	public void save() {
		try {
			SQLQuery query = new SQLQuery(QueryType.UPDATE, TABLE_NAME);
			query.addID(getId());
			query.addSet("playerID", playerID);
			query.addSet("userID", userID);
			query.addSet("code", code);
			query.addSet("confirm", getConfirmed());
			query.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
