package us.ignitiongaming.entity.other;

import java.sql.ResultSet;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.HasID;
import us.ignitiongaming.util.convert.BooleanConverter;
import us.ignitiongaming.util.convert.ChatConverter;

public class IGAnnouncement extends HasID {

	public static final String TABLE_NAME = "announcement";
	
	private boolean staffOnly;
	private String text;
	
	public void setStaff(boolean staffOnly) { this.staffOnly = staffOnly; }
	public boolean isStaffOnly() { return staffOnly; }
	
	public void setText(String text) { this.text = text; }
	public String getText() { return text; }
	public String getColoredText() { return ChatConverter.convertToColor(text); }
	public String getTextNoColor() { return ChatConverter.stripColor(text); }
	
	public void assign(ResultSet results) {
		try {
			setId(results);
			setStaff(results.getBoolean("staff"));
			setText(results.getString("text"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void save() {
		SQLQuery query = new SQLQuery(QueryType.UPDATE, TABLE_NAME);
		query.addSet("staff", BooleanConverter.getIntegerFromBoolean(staffOnly));
		query.addSet("text", text);
		query.addId(getId());
		query.execute();
	}
	
	public void delete() {
		SQLQuery query = new SQLQuery(QueryType.DELETE, TABLE_NAME);
		query.addId(getId());
		query.execute();
	}
}
