package us.ignitiongaming.factory.player;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.other.IGHome;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerHome;
import us.ignitiongaming.factory.other.IGHomeFactory;
import us.ignitiongaming.util.convert.BooleanConverter;

public class IGPlayerHomeFactory {

	public static List<IGHome> getHomesByPlayer(IGPlayer igPlayer) {
		List<IGHome> homes = new ArrayList<>();
		try {
			List<IGPlayerHome> playerHomes = getPlayerHomesByPlayer(igPlayer);
			for (IGPlayerHome playerHome : playerHomes) {
				IGHome home = IGHomeFactory.getHomeById(playerHome.getHomeId());
				homes.add(home);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return homes;
	}
	
	public static List<IGPlayerHome> getPlayerHomesByPlayer(IGPlayer igPlayer) {
		List<IGPlayerHome> homes = new ArrayList<>();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerHome.TABLE_NAME);
			query.addWhere("playerID", igPlayer.getId());
			query.addWhere("visible", BooleanConverter.toInteger(true)); //Only show VISIBLE homes. Otherwise, they are "deleted".
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGPlayerHome home = new IGPlayerHome();
				home.assign(results);
				homes.add(home);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return homes;
	}
	
	public static IGPlayerHome getPlayerHomeByHomeId(int homeId) {
		IGPlayerHome home = new IGPlayerHome();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerHome.TABLE_NAME);
			query.addWhere("homeID", homeId);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				home.assign(results);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return home;
	}
	
	public static IGPlayerHome getPlayerHomeByPlayerAndHome(IGPlayer igPlayer, String name) {
		List<IGPlayerHome> playerHomes = getPlayerHomesByPlayer(igPlayer);
		for (IGPlayerHome playerHome : playerHomes) {
			IGHome home = IGHomeFactory.getHomeById(playerHome.getHomeId());
			if (home.getName().equalsIgnoreCase(name)) {
				return playerHome;
			}
		}
		
		return new IGPlayerHome();
	}
	
	public static boolean doesHomeExist(IGPlayer igPlayer, String homeName) {
		List<IGHome> homes = getHomesByPlayer(igPlayer);
		for (IGHome home : homes) {
			if (home.getName().equalsIgnoreCase(homeName)) return true;
		}
		return false;
	}
	
	public static IGHome getHomeByPlayerAndHome(IGPlayer igPlayer, String homeName) {
		List<IGHome> homes = getHomesByPlayer(igPlayer);
		for (IGHome h : homes) {
			if (h.getName().equalsIgnoreCase(homeName)) return h;
		}
		return new IGHome(); //Invalid home.
	}
	
	/**
	 * Adds a new IGHome and associates that home to the IGPlayer
	 * @param igPlayer
	 * @param location
	 * @param name
	 */
	public static void add(IGPlayer igPlayer, Location location, String name) {
		try {
			//Step 1: Add the HOME first.
			SQLQuery query = new SQLQuery(QueryType.INSERT, IGHome.TABLE_NAME);
			query.addGrabColumns("name", "x", "y", "z", "yaw", "world");
			query.addValues(name, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getWorld().getName());
			query.execute();
			
			//Step 2: Find the home we just created.
			SQLQuery homeQuery = new SQLQuery(QueryType.SELECT, IGHome.TABLE_NAME);
			homeQuery.addWhere("name", name);
			homeQuery.addWhere("world", location.getWorld().getName());
			ResultSet results = homeQuery.getResults();
			IGHome home = new IGHome();
			while (results.next()) {
				home.assign(results);
			}
			
			//Step 3: Add the PlayerHome
			SQLQuery playerHomeQuery = new SQLQuery(QueryType.INSERT, IGPlayerHome.TABLE_NAME);
			playerHomeQuery.addGrabColumns("playerID", "homeID");
			playerHomeQuery.addValues(igPlayer.getId(), home.getId());
			playerHomeQuery.execute();
		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		
	}
}
