package us.ignitiongaming.factory.player;

import java.sql.ResultSet;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import us.ignitiongaming.util.convert.DateConverter;
import us.ignitiongaming.database.DatabaseUtils;
import us.ignitiongaming.database.QueryType;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerBanned;

public class IGPlayerBannedFactory {
	
	public static boolean isBanned(IGPlayer igPlayer){
		try {
		SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerBanned.TABLE_NAME);
		query.addWhere("playerID", igPlayer.getId());
		ResultSet bans = query.getResults();
		if(DatabaseUtils.getNumRows(bans) == 0) return false;
		Date latestDate = DateUtils.parseDate(bans.getString(2));
		while(bans.next()){
			latestDate = (DateUtils.parseDate(bans.getString(2)).after(latestDate)) ? DateUtils.parseDate(bans.getString(2)) : latestDate;
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
}
