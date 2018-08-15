package us.ignitiongaming.factory.user;

import java.sql.ResultSet;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.user.IGLink;

public class IGLinkFactory {
	
	public static IGLink getLinkByCode(String code) {
		IGLink link = new IGLink();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGLink.TABLE_NAME);
			query.addWhere("code", code);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				link.assign(results);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return link;
	}
}
