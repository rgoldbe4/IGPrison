package us.ignitiongaming.factory.player;

import java.util.Date;

import us.ignitiongaming.database.QueryType;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerKicked;

public class IGPlayerKickedFactory {
	public static void add(IGPlayer igPlayer, String reason, IGPlayer staff) {
		try {
			SQLQuery query = new SQLQuery(QueryType.INSERT, IGPlayerKicked.TABLE_NAME);
			query.addGrabColumns("playerID", "staffID", "Reason");
			query.addValues(igPlayer.getId(), staff, reason);
			query.execute();			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
