package us.ignitiongaming.factory.player;

import java.sql.ResultSet;
import java.util.Date;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerBanned;
import us.ignitiongaming.util.convert.BooleanConverter;
import us.ignitiongaming.util.convert.DateConverter;

public class IGPlayerBannedFactory {
	
	public static boolean isBanned(IGPlayer igPlayer){
		boolean isPlayerBanned = false;
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerBanned.TABLE_NAME);
			query.addWhere("playerID", igPlayer.getId());
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGPlayerBanned player = new IGPlayerBanned();
				player.assign(results);	
				//If any of the bans found are applicable, mark them as "yes".
				if (player.isBanned()) isPlayerBanned = true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return isPlayerBanned;
	}
	
	public static IGPlayerBanned getPlayerBan(IGPlayer igPlayer) {
		IGPlayerBanned igPlayerBanned = new IGPlayerBanned();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerBanned.TABLE_NAME);
			query.addWhere("playerID", igPlayer.getId());
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGPlayerBanned player = new IGPlayerBanned();
				player.assign(results);
				if (player.isBanned()) igPlayerBanned = player;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return igPlayerBanned;
	}
	
	public static void add(IGPlayer igStaff, IGPlayer igTarget, Date end, String reason) {
		try {
			SQLQuery query = new SQLQuery(QueryType.INSERT, IGPlayerBanned.TABLE_NAME);
			query.addGrabColumns("playerID", "staffID", "banStart", "banEnd", "Reason", "permanent");
			query.addValues(
					igTarget.getId(),
					igStaff.getId(),
					DateConverter.getCurrentTimeString(),
					DateConverter.convertDateToString(end),
					reason,
					BooleanConverter.getIntegerFromBoolean(false)
			);
			
			query.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static void addPermanent(IGPlayer igStaff, IGPlayer igTarget, String reason) {
		try {
			SQLQuery query = new SQLQuery(QueryType.INSERT, IGPlayerBanned.TABLE_NAME);
			query.addGrabColumns("playerID", "staffID", "banStart", "Reason", "permanent");
			query.addValues(
					igTarget.getId(),
					igStaff.getId(),
					DateConverter.getCurrentTimeString(),
					reason,
					BooleanConverter.getIntegerFromBoolean(true)
			);
			
			query.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
