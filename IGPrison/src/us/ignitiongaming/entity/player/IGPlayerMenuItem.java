package us.ignitiongaming.entity.player;

import java.sql.ResultSet;
import java.util.Date;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.HasID;
import us.ignitiongaming.entity.menu.IGMenuItem;
import us.ignitiongaming.factory.menu.IGMenuItemFactory;
import us.ignitiongaming.util.convert.DateConverter;

public class IGPlayerMenuItem extends HasID {

	public static final String TABLE_NAME = "player_menu_item";
	
	private int playerId, menuItemId;
	private String stamp;
	
	public int getPlayerId() { return playerId; }
	public void setPlayerId(int playerId) { this.playerId = playerId; }
	
	public int getMenuItemId() { return menuItemId; }
	public IGMenuItem getMenuItem() { return IGMenuItemFactory.getItemById(menuItemId); }
	public void setMenuItemId(int menuItemId) { this.menuItemId = menuItemId; }
	
	public String getStamp() { return stamp; }
	public Date getPurchased() { return DateConverter.convertStringDateTimeToDate(stamp); }
	public void setStamp(String stamp) { this.stamp = stamp; }
	
	public void assign(ResultSet results) {
		try {
			setId(results);
			setPlayerId(results.getInt("playerID"));
			setMenuItemId(results.getInt("menuItemID"));
			setStamp(results.getString("stamp"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void save() {
		SQLQuery query = new SQLQuery(QueryType.UPDATE, TABLE_NAME);
		query.addSet("playerID", playerId);
		query.addSet("menuItemID", menuItemId);
		//Do not update the timestamp because... it shouldn't be updated...
		query.addId(getId());
		query.execute();
	}
}
