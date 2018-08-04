package us.ignitiongaming.factory.lockdown;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.CombinationType;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.lockdown.IGLockdown;
import us.ignitiongaming.entity.other.IGCell;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.enums.IGCells;
import us.ignitiongaming.util.convert.DateConverter;

public class IGLockdownFactory {

	public static List<IGLockdown> getCurrentLockdowns() {
		List<IGLockdown> lockdowns = new ArrayList<>();
		try {
			//Step 1: Grab all lockdowns where ended is null.
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGLockdown.TABLE_NAME);
			query.addWhere("ended", null);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGLockdown lockdown = new IGLockdown();
				lockdown.assign(results);
				lockdowns.add(lockdown);
			}
			
			return lockdowns;
		} catch (Exception ex) {
			ex.printStackTrace();
			return lockdowns;
		}
	}
	
	public static List<IGLockdown> getLockdownsFromPlayer(IGPlayer igPlayer) {
		List<IGLockdown> lockdowns = new ArrayList<>();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGLockdown.TABLE_NAME);
			//startID = <ID> OR endID = <ID>
			query.addWhere("startID", igPlayer.getId(), CombinationType.OR);
			query.addWhere("endID", igPlayer.getId());
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGLockdown lockdown = new IGLockdown();
				lockdown.assign(results);
				lockdowns.add(lockdown);
			}
			
			return lockdowns;
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return lockdowns;
		}
	}
	
	public static boolean isCellInLockdown(IGCells igCells) {
		try {
			List<IGLockdown> lockdowns = getCurrentLockdowns();
			for (IGLockdown lockdown : lockdowns) {
				if (lockdown.getCell().getLabel().equalsIgnoreCase(igCells.getLabel()))
					return true;
			}
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public static List<IGLockdown> getAllLockdowns() {
		List<IGLockdown> lockdowns = new ArrayList<>();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGLockdown.TABLE_NAME);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGLockdown lockdown = new IGLockdown();
				lockdown.assign(results);
				lockdowns.add(lockdown);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lockdowns;
	}
	
	public static void add(IGCell cell, IGPlayer igPlayer) {
		try {
			Date started = DateConverter.getCurrentTime();
			SQLQuery query = new SQLQuery(QueryType.INSERT, IGLockdown.TABLE_NAME);
			query.addGrabColumns("cellID", "startID", "started");
			query.addValues(cell.getId(), igPlayer.getId(), DateConverter.convertDateToString(started));
			query.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
