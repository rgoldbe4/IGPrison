package us.ignitiongaming.entity.player;

import java.sql.ResultSet;
import java.util.UUID;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.HasID;

public class IGPlayer extends HasID {
	/**
	 * IGPlayer's MySQL Table name.
	 */
	public static final String TABLE_NAME = "player";
	private String name, ip;
	private UUID uuid;
	private String nickname;
	
	/**
	 * Assign all columns using a one-to-one ResultSet.
	 * @param results
	 */
	public void assign(ResultSet results) {
		try {
			setId(results);
			setName(results.getString("name"));
			setUUID(UUID.fromString(results.getString("uuid")));
			setIP(results.getString("ip"));
			setNickname(results.getString("nickname"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void setName(String name) { this.name = name; }
	public String getName() { return name; }
	
	public void setUUID(UUID uuid) { this.uuid = uuid; }
	public UUID getUUID() { return uuid; }
	
	public void setIP(String ip) { this.ip = ip; }
	public String getIP() { return ip; }
	
	public String getNickname() { return nickname; }
	public void setNickname(String nickname) { this.nickname = nickname; }
	public boolean hasNickname() { 
		if (nickname == null) return false;
		return nickname.length() > 0;
	}
	
	/**
	 * Get's the display name of the player (Nickname > Name)
	 * @return
	 */
	public String getDisplayName() {
		if (hasNickname()) return nickname;
		return name;
	}
	
	/**
	 * 
	 * Determine if the entities is valid.
	 * @return
	 */
	public boolean isValid() {
		return (uuid != null && hasId() && name != null && ip != null && nickname != null);
	}
	
	/**
	 * Save the entity back to the database.
	 */
	public void save() {
		try {
			//Determine if all values are filled.
			if (isValid()) {
				SQLQuery query = new SQLQuery(QueryType.UPDATE, TABLE_NAME);
				query.addSet("uuid", uuid);
				query.addSet("name", name);
				query.addSet("ip", ip);
				query.addSet("nickname", nickname);
				query.addWhere("ID", getId());
				query.execute();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
