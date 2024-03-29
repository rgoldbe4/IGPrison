package us.ignitiongaming.factory.other;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.bukkit.entity.Player;

import us.ignitiongaming.database.DatabaseUtils;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.other.IGLocation;
import us.ignitiongaming.enums.IGLocations;
import us.ignitiongaming.enums.IGRankNodes;

public class IGLocationFactory {

	public static IGLocation getLocationByIGLocations(IGLocations location) {
		return getLocationByLabel(location.getLabel());
	}
	public static IGLocation getLocationByLabel(String label) {
		try {
			IGLocation location = new IGLocation();
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGLocation.TABLE_NAME);
			query.addWhere("label", label);
			ResultSet results = query.getResults();
			
			if (DatabaseUtils.getNumRows(results) == 0) return null;
			
			while (results.next()) {
				location.assign(results);
			}
			
			return location;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	public static ArrayList<String> getAllLocations(){
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGLocation.TABLE_NAME);
			ResultSet results = query.getResults();	
			ArrayList<String> warps = new ArrayList<String>();
			while(results.next())warps.add(results.getString(2));
			return warps;
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<String>();
		}
		
	}
	
	public static IGLocation getSpawnByPlayerRank(Player player) {
		try {
			if (IGRankNodes.isPlayerInSolitary(player) && !player.isOp()) return getLocationByIGLocations(IGLocations.SOLITARY);
			
			IGRankNodes playerRank = IGRankNodes.getPlayerRank(player);
			IGLocations spawnLocation = IGLocations.getLocationByLabel(playerRank.name());
			
			return getLocationByIGLocations(spawnLocation);
			
		} catch (Exception ex) {
			return null;
		}
	}
}
