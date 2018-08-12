package us.ignitiongaming.entity.player;

import java.sql.ResultSet;
import java.util.Date;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.HasID;
import us.ignitiongaming.util.convert.DateConverter;

public class IGPlayerSpawn extends HasID {

	public static String TABLE_NAME = "player_spawn";
	
	private int playerId = 0;
	private Date cooldown;
	
	public void setPlayerId(int playerId) { this.playerId = playerId; }
	public int getPlayerId() {
		return playerId;
	}
	
	public void setCooldown(Date cooldown) { this.cooldown = cooldown; }
	public void setCooldown(String cooldown) { this.cooldown = DateConverter.convertStringDateTimeToDate(cooldown); }
	public void setCooldown() {
		//All cooldowns start at 1 hour.
		this.cooldown = DateConverter.addTimeFromString(DateConverter.getCurrentTime(), "1h");
	}
	public Date getCooldown() { return cooldown; }
	public String getCooldownString() { return DateConverter.convertDateToString(cooldown); }
		
	public boolean isAvailable() {
		Date now = DateConverter.getCurrentTime();
		return now.after(cooldown); //If now is after cooldown
	}
	
	public boolean isValid() {
		return (hasId() && playerId > 0 && cooldown != null);
	}
	
	public void save() {
		if (isValid()) {
			SQLQuery query = new SQLQuery(QueryType.UPDATE, TABLE_NAME);
			query.addSet("playerId", playerId);
			query.addSet("cooldown", getCooldownString());
			query.addId(getId());
			query.execute();
		}
	}
	
	public void delete() {
		if (isValid()) {
			SQLQuery query = new SQLQuery(QueryType.DELETE, TABLE_NAME);
			query.addId(getId());
			query.execute();
		}
	}
	
	public void assign(ResultSet results) {
		try {
			setId(results);
			setPlayerId(results.getInt("playerID"));
			setCooldown(results.getString("cooldown"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
