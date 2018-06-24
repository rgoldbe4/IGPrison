package us.ignitiongaming.entity.rank;

import java.sql.ResultSet;

import us.ignitiongaming.entity.HasID;
import us.ignitiongaming.enums.IGRanks;

@Deprecated
public class IGRank extends HasID {

	public static final String TABLE_NAME = "rank";
	
	private String name, node, tag, nameColor;
	
	public void assign(ResultSet results) {
		try {
			setId(results.getInt("ID"));
			setName(results.getString("name"));
			setNode(results.getString("node"));
			setTag(results.getString("tag"));
			setNameColor(results.getString("namecolor"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void setName(String name) { this.name = name; }
	public String getName() { return name; }
	
	public void setNode(String node) { this.node = node; }
	public String getNode() { return node; }
	
	public void setTag(String tag) { this.tag = tag; }
	public String getTag() { return tag; }
	
	public void setNameColor(String nameColor) { this.nameColor = nameColor; }
	public String getNameColor() { return nameColor; }
	
	public boolean isValid() {
		return (!hasId() || node == null || name == null || tag == null);
	}
	public boolean isStaff(){
		if(name.equals(IGRanks.GUARD)) return true;
		if(name.equals(IGRanks.WARDEN)) return true;
		if(name.equals(IGRanks.STAFF)) return true;
		return false;
	}
}
