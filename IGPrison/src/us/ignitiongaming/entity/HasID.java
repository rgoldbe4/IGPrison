package us.ignitiongaming.entity;

import java.sql.ResultSet;

import us.ignitiongaming.util.convert.BooleanConverter;

public class HasID {

	//ID of all entities are auto-increment, which means no ID can ever be 0. Therefore, make it a checkpoint in code.
	private int id = 0;
	//Default the visibility to false.
	private boolean visible = false;
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
	/**
	 * Does the ID exist for the entity.
	 * @return
	 */
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
	
	public void setVisible(int visible) { 
		setVisible(BooleanConverter.getBooleanFromInteger(visible));
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void setVisible(ResultSet result) {
		try {
			visible = BooleanConverter.getBooleanFromInteger(result.getInt("visible"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public int getVisible() { 
		return BooleanConverter.getIntegerFromBoolean(visible);
	}
	
	public boolean isVisible() {
		return visible == true;
	}
	
	public boolean isShowing() { return isVisible(); }
	public boolean isHiding() { return !isShowing(); }
}
