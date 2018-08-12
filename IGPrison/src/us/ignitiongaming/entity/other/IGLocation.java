package us.ignitiongaming.entity.other;

import java.sql.ResultSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.HasID;
import us.ignitiongaming.enums.IGLocations;

public class IGLocation extends HasID {

	public static final String TABLE_NAME = "location";
	
	private String label, worldName;
	private int x, y, z;
	private float yaw;
	
	public void assign(ResultSet results) {
		try {
			setId(results.getInt("ID"));
			setLabel(results.getString("label"));
			setWorldName(results.getString("worldname"));
			setX(results.getInt("x"));
			setY(results.getInt("y"));
			setZ(results.getInt("z"));
			setDirection(results.getFloat("direction"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void fromLocation(Location location) {
		setX(location.getBlockX());
		setY(location.getBlockY());
		setZ(location.getBlockZ());
		setDirection(location.getYaw());
		setWorldName(location.getWorld().getName());
	}
	
	public void save() {
		SQLQuery query = new SQLQuery(QueryType.UPDATE, TABLE_NAME);
		query.addSet("label", label);
		query.addSet("worldname", worldName);
		query.addSet("x", x);
		query.addSet("y", y);
		query.addSet("z", z);
		query.addSet("direction", yaw);
		query.addId(getId());
		query.execute();
	}
	
	
	public void setLabel(String label) { this.label = label; }
	public String getLabel() { return label; }
	public void setLabel(IGLocations location) { this.label = location.getLabel(); }
	
	public void setWorldName(String worldName) { this.worldName = worldName; }
	public String getWorldName() { return worldName; }
	
	public void setX(int x) { this.x = x; }
	public int getX() { return x; }
	
	public void setY(int y) { this.y = y; }
	public int getY() { return y; }
	
	public void setZ(int z) { this.z = z; }
	public int getZ() { return z; }
	
	public void setDirection(float direction) { this.yaw = direction; }
	public float getDirection() { return yaw; }
	
	public Location toLocation() { return getLocation(); }
	
	public Location getLocation() {
		Location location = new Location(Bukkit.getWorld(worldName), x, y, z);
		location.setYaw(yaw);
		return location;
	}
}
