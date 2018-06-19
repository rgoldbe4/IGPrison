package us.ignitiongaming.factory.player;

import us.ignitiongaming.config.ServerDefaults;
import us.ignitiongaming.database.DatabaseUtils;
import us.ignitiongaming.database.QueryType;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerDonator;
import us.ignitiongaming.entity.player.IGPlayerStats;

public class IGPlayerDonatorFactory {

	public static boolean isIGPlayerDonator(IGPlayer igPlayer) {
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerDonator.TABLE_NAME);
			query.addWhere("playerID", igPlayer.getId());
			//Donator = 1, Non-Donator = 0
			return DatabaseUtils.getNumRows(query.getResults()) == 1;
		} catch (Exception ex) {
			return false;
		}
	}
	
	public static void add(IGPlayer igPlayer) {
		try {
			// -- Add IGPlayerDonator --
			SQLQuery query = new SQLQuery(QueryType.INSERT, IGPlayerDonator.TABLE_NAME);
			query.addGrabColumn("playerID");
			query.addValue(igPlayer.getId());
			query.execute();
			
			// -- Add donator points to IGPlayerStats --
			IGPlayerStats stats = IGPlayerStatsFactory.getIGPlayerStatsByIGPlayer(igPlayer);
			stats.addDonatorPoints(ServerDefaults.DEFAULT_DONATOR_PERK_POINTS);
			stats.save();
		} catch (Exception ex) {
			
		}
	}
	
	public static void remove(IGPlayer igPlayer) {
		try {
			SQLQuery query = new SQLQuery(QueryType.DELETE, IGPlayerDonator.TABLE_NAME);
			query.addWhere("playerID", igPlayer.getId());
			query.execute();
		} catch (Exception ex) {
			
		}
	}
}
