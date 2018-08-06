package us.ignitiongaming.factory.player;

import java.sql.ResultSet;
import java.util.Date;

import us.ignitiongaming.database.DatabaseUtils;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerBanned;
import us.ignitiongaming.util.convert.BooleanConverter;
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
	
	public static void add(IGPlayer igPlayer, Date start, Date end, String reason, IGPlayer staff, boolean isPermanent) {
		try {
			SQLQuery query = new SQLQuery(QueryType.INSERT, IGPlayerBanned.TABLE_NAME);
			query.addGrabColumns("playerID", "staffID", "banStart", "banEnd", "Reason", "permanent");
			query.addValues(igPlayer.getId(),
					staff.getId(),
					DateConverter.convertDateToString(start),
					DateConverter.convertDateToString(end),
					reason,
					BooleanConverter.getIntegerFromBoolean(isPermanent));
			query.execute();			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
