package us.ignitiongaming.entity.menu;

import java.sql.ResultSet;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.HasID;

public class IGMenuItem extends HasID {

	public static final String TABLE_NAME = "menu_item";
	
	private int points;
	private String label;
	
	public int getPoints() { return points;	}
	public void setPoints(int points) { this.points = points; }
	
	public String getLabel() { return label; }
	public void setLabel(String label) { this.label = label; }
	
	public void assign(ResultSet results) {
		try {
			setId(results);
			setPoints(results.getInt("points"));
			setLabel(results.getString("label"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void save() {
		SQLQuery query = new SQLQuery(QueryType.UPDATE, TABLE_NAME);
		query.addSet("points", points);
		query.addSet("label", label);
		query.addId(getId());
		query.execute();
	}
}
