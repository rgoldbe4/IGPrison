package us.ignitiongaming.factory.player;

import java.sql.ResultSet;
import java.util.Date;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerSpawn;
import us.ignitiongaming.util.convert.DateConverter;

public class IGPlayerSpawnFactory {

	public static IGPlayerSpawn getSpawnByPlayer(IGPlayer igPlayer) {
		IGPlayerSpawn igPlayerSpawn = new IGPlayerSpawn();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerSpawn.TABLE_NAME);
			query.addWhere("playerID", igPlayer.getId());
			ResultSet results = query.getResults();
			
			while (results.next()) {
				igPlayerSpawn.assign(results);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return igPlayerSpawn;
	}
	
	public static void add(IGPlayer igPlayer) {
		Date hourLater = DateConverter.addTimeFromString(DateConverter.getCurrentTime(), "1h");
		SQLQuery query = new SQLQuery(QueryType.INSERT, IGPlayerSpawn.TABLE_NAME);
		query.addGrabColumns("playerID", "cooldown");
		query.addValues(igPlayer.getId(), DateConverter.convertDateToString(hourLater));
		query.logQuery();
		query.execute();
	}
}
