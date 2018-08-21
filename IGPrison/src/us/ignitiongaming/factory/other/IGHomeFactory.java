package us.ignitiongaming.factory.other;

import java.sql.ResultSet;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.other.IGHome;

public class IGHomeFactory {

	public static IGHome getHomeById(int id) {
		IGHome home = new IGHome();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGHome.TABLE_NAME);
			query.addId(id);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				home.assign(results);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return home;
	}
}
