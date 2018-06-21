package us.ignitiongaming.factory.player;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import us.ignitiongaming.database.DatabaseUtils;
import us.ignitiongaming.database.QueryType;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerSolitary;
import us.ignitiongaming.util.convert.DateConverter;

public class IGPlayerSolitaryFactory {

	public static boolean isIGPlayerInSolitary(IGPlayer igPlayer) {
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerSolitary.TABLE_NAME);
			query.addWhere("playerID", igPlayer.getId());
			ResultSet results = query.getResults();
			
			return DatabaseUtils.getNumRows(results) == 1;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public static IGPlayerSolitary getIGPlayerInSolitary(IGPlayer igPlayer) {
		try {
			IGPlayerSolitary igPlayerSolitary = new IGPlayerSolitary();
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerSolitary.TABLE_NAME);
			query.addWhere("playerID", igPlayer.getId());
			ResultSet results = query.getResults();
			
			if (DatabaseUtils.getNumRows(results) == 0) return null;
			
			while (results.next()) {
				igPlayerSolitary.assign(results);
			}
			
			return igPlayerSolitary;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static List<IGPlayerSolitary> getAllPlayersInSolitary() {
		List<IGPlayerSolitary> players = new ArrayList<>();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerSolitary.TABLE_NAME);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGPlayerSolitary player = new IGPlayerSolitary();
				player.assign(results);
				players.add(player);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return players;
	}
	
	public static void remove(IGPlayer igPlayer) {
		try {
			SQLQuery query = new SQLQuery(QueryType.DELETE, IGPlayerSolitary.TABLE_NAME);
			query.addWhere("playerID", igPlayer.getId());
			query.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Add a player to solitary
	 * Also, no need for "start", since it's added.
	 * @param igPlayer
	 * @param end
	 */
	public static void add(IGPlayer igPlayer, Date end) {
		try {
			SQLQuery query = new SQLQuery(QueryType.INSERT, IGPlayerSolitary.TABLE_NAME);
			query.addGrabColumns("playerID", "start", "end");
			query.addValues(igPlayer.getId(), DateConverter.getCurrentTimeString(), DateConverter.convertDateToString(end));
			query.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
