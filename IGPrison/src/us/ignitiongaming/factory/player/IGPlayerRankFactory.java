package us.ignitiongaming.factory.player;

import java.sql.ResultSet;

import us.ignitiongaming.database.DatabaseUtils;
import us.ignitiongaming.database.QueryType;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerRank;
import us.ignitiongaming.entity.rank.IGRank;

public class IGPlayerRankFactory {

	
	public static IGRank getIGPlayerRank(IGPlayer igPlayer) {
		try {
			IGRank rank = new IGRank();
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerRank.TABLE_NAME);
			query.addWhere("playerID", igPlayer.getId());
			ResultSet results = query.getResults();
			
			//Player does not have a rank, which shouldn't happen.
			if (DatabaseUtils.getNumRows(results) == 0)  return null;
			
			while (results.next()) {
				rank.assign(results);
			}
			
			return rank;			
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * Add a new IGPlayerRank
	 * @param igPlayer
	 * @param igRank
	 */
	public static void add(IGPlayer igPlayer, IGRank igRank) {
		try {
			SQLQuery query = new SQLQuery(QueryType.INSERT, IGPlayerRank.TABLE_NAME);
			query.addGrabColumns("playerID", "rankID");
			query.addValues(igPlayer.getId(), igRank.getId());
			query.execute();			
		} catch (Exception ex) {
			
		}
	}
}
