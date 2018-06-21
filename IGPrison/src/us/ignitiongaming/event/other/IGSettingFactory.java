package us.ignitiongaming.event.other;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import us.ignitiongaming.database.QueryType;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.entity.other.IGSetting;

public class IGSettingFactory {

	public static List<IGSetting> getSettings() {
		List<IGSetting> settings = new ArrayList<>();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGSetting.TABLE_NAME);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGSetting setting = new IGSetting();
				setting.assign(results);
				settings.add(setting);
			}
			
		} catch (Exception ex) {
			
		}
		return settings;
	}
}
