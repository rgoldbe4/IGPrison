package us.ignitiongaming.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {
	
	public static Connection connection = null;
	
	public static List<Statement> activeStatements = new ArrayList<>();
	
	/**
	 * Access specifically the Development database.
	 * @return
	 */
	public static void ConnectToServer() {
		try {
			if (connection != null) {
				if (!connection.isClosed()) connection.close();
			}
			connection = DriverManager.getConnection("jdbc:mysql://ignitiongaming.us:3306/igminecraft?verifyServerCertificate=false&maxReconnects=10&useSSL=true&retainStatementAfterResultSetClose=true",
					"igminecraft", "7xXTvmgXyrUGvsh6");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static Statement GetStatement() {
		try {
			if (connection == null) {
				ConnectToServer();
			}
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			activeStatements.add(statement);
			
			return statement;
		} catch (Exception ex) {
			return null;
		}
	}

}
