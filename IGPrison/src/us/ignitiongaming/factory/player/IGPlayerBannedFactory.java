package us.ignitiongaming.factory.player;

import java.sql.ResultSet;
import java.util.Date;

import us.ignitiongaming.database.DatabaseUtils;
import us.ignitiongaming.database.QueryType;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerBanned;
import us.ignitiongaming.util.convert.DateConverter;

public class IGPlayerBannedFactory {
	
	public static boolean isBanned(IGPlayer igPlayer){
		try {
		SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerBanned.TABLE_NAME);
		query.addWhere("playerID", igPlayer.getId());
		ResultSet bans = query.getResults();
		if(DatabaseUtils.getNumRows(bans) == 0) return false;
		Date latestDate = new Date(0);
		while(bans.next()){
			String banDate = bans.getString("banEnd");
			latestDate = (DateConverter.convertStringDateTimeToDate(banDate).after(latestDate))
					? DateConverter.convertStringDateTimeToDate(banDate) : latestDate;
		}	
		return new Date().after(latestDate) ? false : true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void add(IGPlayer igPlayer, Date start, Date end, String reason, IGPlayer staff) {
		try {
			SQLQuery query = new SQLQuery(QueryType.INSERT, IGPlayerBanned.TABLE_NAME);
			query.addGrabColumns("playerID", "staffID", "banStart", "banEnd", "Reason");
			query.addValues(igPlayer.getId(), staff.getId(), DateConverter.convertDateToString(start), DateConverter.convertDateToString(end), reason);
			query.execute();			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String getBanDate(IGPlayer igPlayer) {
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerBanned.TABLE_NAME);
			query.addWhere("playerID", igPlayer.getId());
			ResultSet bans = query.getResults();
			Date latestDate = new Date(0);
			while(bans.next()){
				String banDate = bans.getString("banEnd");
				latestDate = (DateConverter.convertStringDateTimeToDate(banDate).after(latestDate))
						? DateConverter.convertStringDateTimeToDate(banDate) : latestDate;
			}	
			return latestDate.toString();
			}
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

}
