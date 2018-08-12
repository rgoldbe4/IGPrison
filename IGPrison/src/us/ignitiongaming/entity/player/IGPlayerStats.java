package us.ignitiongaming.entity.player;


import java.sql.ResultSet;
import java.util.Date;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.HasID;
import us.ignitiongaming.util.convert.DateConverter;

public class IGPlayerStats extends HasID {

	public static final String TABLE_NAME = "player_stats";
	
	private int playerId = 0, kills = -1, deaths = -1, donatorPoints = -1;
	private String joined, lastLogin;
	
	public void assign(ResultSet results) {
		try {
			setId(results);
			setKills(results.getInt("kills"));
			setDeaths(results.getInt("deaths"));
			setDonatorPoints(results.getInt("donatorpoints"));
			setJoined(results.getString("joined"));
			setLastLogin(results.getString("lastlogin"));
			setPlayerId(results.getInt("playerId"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean isValid() {
		return (hasId() && kills > -1 && deaths > -1 && donatorPoints > -1 && joined != null && lastLogin != null);
	}
	
	public void save() {
		if (isValid()) {
			SQLQuery query = new SQLQuery(QueryType.UPDATE, TABLE_NAME);
			query.addSet("kills", kills);
			query.addSet("deaths", deaths);
			query.addSet("donatorpoints", donatorPoints);
			query.addSet("joined", joined);
			query.addSet("lastlogin", lastLogin);
			query.addWhere("playerID", playerId);
			query.execute();
		}
	}
	
	public void setKills(int kills) { this.kills = kills; }
	public void addKill() { kills++; }
	public void addKills(int kills) { this.kills += kills; }
	public int getKills() { return kills; }
	
	public void setDeaths(int deaths) { this.deaths = deaths; }
	public void addDeath() { deaths++; }
	public void addDeaths(int deaths) { this.deaths += deaths; }
	public int getDeaths() { return deaths; }
	
	public void setDonatorPoints(int donatorPoints) { this.donatorPoints = donatorPoints; }
	public void addDonatorPoint() { addDonatorPoints(1); }
	public void addDonatorPoints(int donatorPoints) { this.donatorPoints += donatorPoints; }
	public int getDonatorPoints() { return donatorPoints; }
	public void removeDonatorPoint() { removeDonatorPoints(1); }
	public void removeDonatorPoints(int donatorPoints) {
		this.donatorPoints -= donatorPoints;
	}
	
	public String getJoined() { return joined; }
	public void setJoined(String joinedDate) { this.joined = joinedDate; }
	public Date getJoinedToDate() { return DateConverter.convertStringDateTimeToDate(joined); }
	public String getJoinedFriendly() { return DateConverter.toFriendlyDate(joined); }
	
	public String getLastLogin() { return lastLogin; }
	public void setLastLogin(String lastLogin) { this.lastLogin = lastLogin; }
	public Date getLastLoginToDate() { return DateConverter.convertStringDateTimeToDate(lastLogin); }
	public String getLastLoginFriendly() { return DateConverter.toFriendlyDate(lastLogin); }
	
	public void setPlayerId(int playerId) { this.playerId = playerId; }
	public int getPlayerId() { return playerId; }

}
