package us.ignitiongaming.factory.user;

import java.sql.ResultSet;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.CombinationType;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.user.IGLink;

public class IGLinkFactory {
	
	public static IGLink getLinkByIGPlayer(IGPlayer igPlayer) {
		IGLink link = new IGLink();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGLink.TABLE_NAME);
			query.addWhere("playerID", igPlayer.getId(), CombinationType.AND);
			query.addWhere("confirm", 0); //Make sure that it hasn't been confirmed yet.
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
