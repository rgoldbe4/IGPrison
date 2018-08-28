package us.ignitiongaming.factory.player;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.menu.IGMenuItem;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerMenuItem;

public class IGPlayerMenuItemFactory {

	public static List<IGPlayerMenuItem> getPlayerBoughtItems(IGPlayer igPlayer) {
		List<IGPlayerMenuItem> playerItems = new ArrayList<>();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerMenuItem.TABLE_NAME);
			query.addWhere("playerID", igPlayer.getId());
			ResultSet results = query.getResults();
			
			while (results.next())  {
				IGPlayerMenuItem playerItem = new IGPlayerMenuItem();
				playerItem.assign(results);
				playerItems.add(playerItem);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return playerItems;
	}
	
	public static void add(IGPlayer igPlayer, IGMenuItem menuItem) {
		try {
			SQLQuery query = new SQLQuery(QueryType.INSERT, IGPlayerMenuItem.TABLE_NAME);
			query.addGrabColumns("playerID", "menuItemID");
			query.addValues(igPlayer.getId(), menuItem.getId());
			query.execute();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
