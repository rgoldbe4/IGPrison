package us.ignitiongaming.entity.rank;

import java.sql.ResultSet;

import us.ignitiongaming.entity.HasID;

public class IGRank extends HasID {

	public static final String TABLE_NAME = "rank";
	
	private String name, node, tag;
	
	public void assign(ResultSet results) {
		try {
			setId(results.getInt("ID"));
			setName(results.getString("name"));
			setNode(results.getString("node"));
			setTag(results.getString("tag"));
		} catch (Exception ex) {
			
		}
	}
	
	public void setName(String name) { this.name = name; }
	public String getName() { return name; }
	
	public void setNode(String node) { this.node = node; }
	public String getNode() { return node; }
	
	public void setTag(String tag) { this.tag = tag; }
	public String getTag() { return tag; }
	
	public boolean isValid() {
		return (!hasId() || node == null || name == null || tag == null);
	}
}
