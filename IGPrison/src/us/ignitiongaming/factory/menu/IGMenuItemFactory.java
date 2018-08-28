package us.ignitiongaming.factory.menu;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.menu.IGMenuItem;
import us.ignitiongaming.enums.IGMenuItems;

public class IGMenuItemFactory {

	public static List<IGMenuItem> getItems() {
		List<IGMenuItem> menuItems = new ArrayList<>();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGMenuItem.TABLE_NAME);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGMenuItem item = new IGMenuItem();
				item.assign(results);
				menuItems.add(item);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return menuItems;
	}
	
	public static IGMenuItem getItemByEnum(IGMenuItems item) {
		return getItemById(item.getId()); // This is better because ID doesn't lie (unless you mess up the table -.-)
	}
	
	public static IGMenuItem getItemById(int id) {
		IGMenuItem menuItem = new IGMenuItem();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGMenuItem.TABLE_NAME);
			query.addWhere("id", id);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				menuItem.assign(results);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return menuItem;
	}
}
