package us.ignitiongaming.entity.other;

import java.sql.ResultSet;

import us.ignitiongaming.database.QueryType;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.entity.HasID;

public class IGCell extends HasID {

	public static final String TABLE_NAME = "cell";
	
	public String label;
	
	public void assign(ResultSet results) {
		try {
			setId(results);
			setLabel(results.getString("label"));			
		} catch (Exception ex) {
			
		}
	}
	
	public boolean isValid() {
		return !(!hasId() || label == null);
	}
	
	public void save() {
		SQLQuery query = new SQLQuery(QueryType.UPDATE, TABLE_NAME);
		query.addSet("label", label);
		query.addWhere("ID", getId());
		query.execute();
	}
	
	public void setLabel(String label) { this.label = label; }
	public String getLabel() { return label; }
}
