package us.ignitiongaming.factory.player;

import java.sql.ResultSet;

import us.ignitiongaming.database.DatabaseUtils;
import us.ignitiongaming.database.QueryType;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerStats;
import us.ignitiongaming.util.convert.DateConverter;

public class IGPlayerStatsFactory {

	public static IGPlayerStats getIGPlayerStatsByIGPlayer(IGPlayer igPlayer) {
		try {
			IGPlayerStats stats = new IGPlayerStats();
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerStats.TABLE_NAME);
			query.addWhere("playerID", igPlayer.getId());
			ResultSet results = query.getResults();
			
			if (DatabaseUtils.getNumRows(results) == 0) return null;
			
			while (results.next()) {
				stats.assign(results);
			}
			
			return stats;
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static void add(IGPlayer igPlayer) {
		try {
			SQLQuery query = new SQLQuery(QueryType.INSERT, IGPlayerStats.TABLE_NAME);
			query.addGrabColumns("playerID", "joined", "lastlogin");
			String currentDateTime = DateConverter.getCurrentTimeString();
			query.addValues(igPlayer.getId(), currentDateTime, currentDateTime);
			query.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
