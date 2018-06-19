package us.ignitiongaming.database;

import java.sql.ResultSet;
import java.sql.Statement;

public class QueryUtils {

	public static ResultSet Select(String query) {
		try {
			Statement statement = Database.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet results = statement.executeQuery(query);
			return results;
		} catch (Exception ex) {
			return null;
		}
	}
	
	public static boolean Query(String query) {
		try {
			Statement statement = Database.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			return statement.execute(query);
		} catch (Exception ex) {
			return false;
		}
	}

}
