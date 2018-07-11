package us.ignitiongaming.factory.gang;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import us.ignitiongaming.database.DatabaseUtils;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.OrderBy;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.gang.IGGang;
import us.ignitiongaming.entity.gang.IGPlayerGang;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.enums.IGGangRank;

public class IGPlayerGangFactory {

	public static boolean isPlayerInGang(IGPlayer igPlayer) {
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerGang.TABLE_NAME);
			query.addWhere("playerID", igPlayer.getId());
			ResultSet results = query.getResults();
			
			return DatabaseUtils.getNumRows(results) == 1; //This should either be 1 or 0.
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public static List<IGPlayerGang> getPlayersInGang(int gangID) {
		List<IGPlayerGang> igPlayers = new ArrayList<>();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerGang.TABLE_NAME);
			query.addWhere("gangID", gangID);
			query.addOrderBy("gangRankID", OrderBy.DESC); //We want leaders to show up first.
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGPlayerGang igPlayerGang = new IGPlayerGang();
				igPlayerGang.assign(results);
				igPlayers.add(igPlayerGang);
			}
			
			return igPlayers;
		} catch (Exception ex) {
			ex.printStackTrace();
			return igPlayers;
		}
	}
	
	public static IGPlayerGang getPlayerGangFromPlayer(IGPlayer igPlayer) {
		try {
			IGPlayerGang igPlayerGang = new IGPlayerGang();
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerGang.TABLE_NAME);
			query.addWhere("playerID", igPlayer.getId());
			ResultSet results = query.getResults();
			
			while (results.next()) {
				igPlayerGang.assign(results);
			}
			
			return igPlayerGang;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static void add(IGPlayer igPlayer, IGGang igGang, IGGangRank igGangRank) {
		SQLQuery query = new SQLQuery(QueryType.INSERT, IGPlayerGang.TABLE_NAME);
		query.addGrabColumns("playerID", "gangID", "gangRankID");
		query.addValues(igPlayer.getId(), igGang.getId(), igGangRank.getId());
		query.execute();
	}
}
