package us.ignitiongaming.factory.user;

import java.sql.ResultSet;

import us.ignitiongaming.database.DatabaseUtils;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.user.IGUser;

public class IGUserFactory {

	
	public static IGUser getUserById(int id) {
		IGUser user = new IGUser();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGUser.TABLE_NAME);
			query.addId(id);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				user.assign(results);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return user;
	}
	
	public static boolean doesUserHavePlayerId(int playerId) {
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGUser.TABLE_NAME);
			query.addWhere("playerID", playerId);
			
			//If the database finds ANY player's ID , then they already did this.
			return DatabaseUtils.getNumRows(query.getResults()) > 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
