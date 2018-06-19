package us.ignitiongaming.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.bukkit.Bukkit;

public class DatabaseUtils {
	/**
	 * Determine if a connection is acceptable.
	 * @param connection
	 * @return
	 */
	public static boolean isValidConnection(Connection connection) {
		return (connection != null);
	}
	/**
	 * Find out the number of rows in a ResultSet.
	 * @param set
	 * @return
	 */
	public static int getNumRows(ResultSet set) {
		try {
			if (set == null) return 0;
			int rowCount = 0;
			set.beforeFirst();
			while (set.next()) {
				rowCount++;
				Bukkit.getLogger().log(Level.FINE, "getNumRows: " + rowCount);
			}
			if (!set.isLast()) set.last();
			int count = set.getRow();
			if (count != rowCount) count = rowCount;
			set = resetResultSet(set);
			return count;
		} catch (Exception ex) {
			return -1;
		}
	}
	
	public static ResultSet resetResultSet(ResultSet set) {
		try {
			set.beforeFirst();
			return set;
		} catch (Exception ex) {
			return null;
		}
	}
}
