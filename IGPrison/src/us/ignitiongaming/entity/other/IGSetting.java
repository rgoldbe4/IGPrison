package us.ignitiongaming.entity.other;

import java.sql.ResultSet;

import us.ignitiongaming.database.QueryType;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.entity.HasID;

public class IGSetting extends HasID {
	public static final String TABLE_NAME = "settings";
	
	private String label;
	private Object value;
	
	public void assign(ResultSet results) {
		try {
			setId(results);
			setLabel(results.getString("label"));
			setValue(results.getObject("value"));
		} catch (Exception ex) {
			
		}
	}
	
	public boolean isValid() {
		return !(!hasId() || label == null || value == null);
	}
	public void save() {
		SQLQuery query = new SQLQuery(QueryType.UPDATE, TABLE_NAME);
		query.addSet("label", label);
		query.addSet("value", value);
		query.addID(getId());
		query.execute();
	}
	
	public void setLabel(String label) { this.label = label; }
	public String getLabel() { return label; }
	
	public void setValue(Object value) { this.value = value; }
	public Object getValue() { return value; }
	
	
}
