package us.ignitiongaming.factory.user;

import java.sql.ResultSet;

import us.ignitiongaming.database.QueryType;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.entity.user.IGUser;

public class IGUserFactory {

	public static void setPlayerIDByUserID(int userID, int playerID) {
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGUser.TABLE_NAME);
			query.addWhere("userID", userID);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGUser igUser = new IGUser();
				igUser.assign(results);
				igUser.setPlayerID(playerID);
				igUser.save();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
