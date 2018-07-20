package us.ignitiongaming.entity;

import java.sql.ResultSet;

public class HasID {

	//ID of all entities are auto-increment, which means no ID can ever be 0. Therefore, make it a checkpoint in code.
	private int id = 0;
	/**
	 * Set the ID of the entity.
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Get the ID of entity.
	 * @return
	 */
	public int getId() { 
		return id;
	}
	
	public boolean hasId() {
		return !(id == 0);
	}
	/**
	 * Set the ID based on a singular ResultSet.
	 * @param result
	 */
	public void setId(ResultSet result) {
		try {
			id = result.getInt("ID");
		} catch (Exception ex) {
			ex.printStackTrace();
			id = 0;
		}
	}
}
