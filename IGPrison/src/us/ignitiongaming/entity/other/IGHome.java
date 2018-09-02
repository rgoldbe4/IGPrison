package us.ignitiongaming.entity.other;

import java.sql.ResultSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.HasID;

public class IGHome extends HasID {

	public static final String TABLE_NAME = "home";
	
	private double x, y, z;
	private float yaw;
	private String name;
	private String world;
	
	public void setName(String name) { this.name = name; }
	public String getName() { return name; }
	
	public void setX(double x) { this.x = x; }
	public double getX() { return x; }
	
	public void setY(double y) { this.y = y; }
	public double getY() { return y; }
	
	public void setZ(double z) { this.z = z; }
	public double getZ() { return z; }
	
	public void setYaw(float yaw) { this.yaw = yaw; }
	public float getYaw() { return yaw; }
	
	public void setWorld(String world) { this.world = world; }
	public void setWorld(World world) { this.world = world.getName(); }
	public String getWorldName() { return world; }
	public World getWorld() { return Bukkit.getWorld(world); }
	
	public Location toLocation() {
		Location home = new Location(getWorld(), x, y, z);
		home.setYaw(yaw);
		return home;
	}
	
	public void fromLocation(Location home) {
		setX(home.getX());
		setY(home.getY());
		setZ(home.getZ());
		setWorld(home.getWorld());
		setYaw(home.getYaw());
	}
	
	public boolean isValid() {
		return !(name == null || world == null);
	}
	
	public void assign(ResultSet results) {
		try {
			setId(results);
			setName(results.getString("name"));
			setX(results.getDouble("x"));
			setY(results.getDouble("y"));
			setZ(results.getDouble("z"));
			setYaw(results.getFloat("yaw"));
			setWorld(results.getString("world"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void save() {
		SQLQuery query = new SQLQuery(QueryType.UPDATE, TABLE_NAME);
		query.addSet("name", getName());
		query.addSet("x", getX());
		query.addSet("y", getY());
		query.addSet("z", getZ());
		query.addSet("yaw", getYaw());
		query.addSet("world", getWorldName());
		query.addId(getId());
		query.execute();
	}
}
