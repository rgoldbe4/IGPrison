package us.ignitiongaming.entity.player;

import java.sql.ResultSet;
import java.util.UUID;

import us.ignitiongaming.database.QueryType;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.entity.HasID;

public class IGPlayer extends HasID {
	/**
	 * IGPlayer's MySQL Table name.
	 */
	public static final String TABLE_NAME = "player";
	private String name;
	private UUID uuid;
	
	/**
	 * Assign all columns using a one-to-one ResultSet.
	 * @param results
	 */
	public void assign(ResultSet results) {
		try {
			setId(results);
			setName(results.getString("name"));
			setUUID(UUID.fromString(results.getString("uuid")));
		} catch (Exception ex) {
			
		}
	}
	
	public void setName(String name) { this.name = name; }
	public String getName() { return name; }
	
	public void setUUID(UUID uuid) { this.uuid = uuid; }
	public UUID getUUID() { return uuid; }
	
	/**
	 * 
	 * Determine if the entities is valid.
	 * @return
	 */
	public boolean isValid() {
		return (uuid == null || !hasId() || name == null);
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
				query.addWhere("ID", getId());
				query.execute();
			}
		} catch (Exception ex) {
			
		}
	}
}
