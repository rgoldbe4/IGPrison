package us.ignitiongaming.factory.player;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import us.ignitiongaming.database.DatabaseUtils;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerSolitary;
import us.ignitiongaming.util.convert.DateConverter;

public class IGPlayerSolitaryFactory {

	public static boolean isIGPlayerInSolitary(IGPlayer igPlayer) {
		boolean isInSolitary = false;
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerSolitary.TABLE_NAME);
			query.addWhere("playerID", igPlayer.getId());
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGPlayerSolitary solitary = new IGPlayerSolitary();
				solitary.assign(results);
				if (!solitary.hasServed() || !solitary.hasLeft()) isInSolitary = true;
			}
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return isInSolitary;
	}
	public static boolean isPlayerInsideOfSolitary(IGPlayer igPlayer) {
		boolean hasLeft = false;
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerSolitary.TABLE_NAME);
			query.addWhere("playerID", igPlayer.getId());
			query.addWhere("hasLeft", 0);
			ResultSet results = query.getResults();
			
			int numRows = DatabaseUtils.getNumRows(results);
			
			return numRows > 0;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return hasLeft;
	}
	
	public static IGPlayerSolitary getIGPlayerInSolitary(IGPlayer igPlayer) {
		try {
			IGPlayerSolitary igPlayerSolitary = new IGPlayerSolitary();
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerSolitary.TABLE_NAME);
			query.addWhere("playerID", igPlayer.getId());
			query.addWhere("hasLeft", 0); //Player must not have left the solitary yet.
			ResultSet results = query.getResults();
			
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
				//Make sure to only show players who haven't served their sentence. Also, check if they have physically left solitary yet.
				if (!player.hasServed() && !player.hasLeft())
					players.add(player);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return players;
	}
	
	
	/**
	 * Add a player to solitary
	 * Also, no need for "start", since it's added.
	 * @param igPlayer
	 * @param end
	 */
	public static void add(IGPlayer igPlayer, Date end, IGPlayer igStaff, String reason) {
		try {
			SQLQuery query = new SQLQuery(QueryType.INSERT, IGPlayerSolitary.TABLE_NAME);
			query.addGrabColumns("playerID", "start", "end", "staffID", "reason");
			query.addValues(igPlayer.getId(), DateConverter.getCurrentTimeString(), DateConverter.convertDateToString(end),
					igStaff.getId(), reason);
			query.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
