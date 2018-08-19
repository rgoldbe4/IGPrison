package us.ignitiongaming.factory.other;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.other.IGAnnouncement;
import us.ignitiongaming.util.convert.BooleanConverter;

public class IGAnnouncementFactory {

	public static List<IGAnnouncement> getAnnouncements() {
		List<IGAnnouncement> announcements = new ArrayList<>();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGAnnouncement.TABLE_NAME);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGAnnouncement announcement = new IGAnnouncement();
				announcement.assign(results);
				announcements.add(announcement);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return announcements;
	}
	
	public static List<IGAnnouncement> getStaffAnnouncements() {
		List<IGAnnouncement> announcements = new ArrayList<>();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGAnnouncement.TABLE_NAME);
			query.addWhere("staff", BooleanConverter.getIntegerFromBoolean(true));
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGAnnouncement announcement = new IGAnnouncement();
				announcement.assign(results);
				announcements.add(announcement);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return announcements;
	}
}
