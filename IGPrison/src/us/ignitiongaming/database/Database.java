package us.ignitiongaming.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import us.ignitiongaming.IGPrison;
import us.ignitiongaming.enums.IGEnvironments;

public class Database {
	
	public static Connection connection = null;
	
	public static List<Statement> activeStatements = new ArrayList<>();
	
	/**
	 * Access specifically the Development database.
	 * @return
	 */
	public static void ConnectToMain() {
		try {
			if (connection != null) {
				if (!connection.isClosed()) connection.close();
			}
			connection = DriverManager.getConnection("jdbc:mysql://66.85.144.162:3306/mcph746387?verifyServerCertificate=false&maxReconnects=10&useSSL=true&retainStatementAfterResultSetClose=true&autoReconnect=true",
					"mcph746387", "0a59de0688");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void ConnectToTesting() {
		try {
			if (connection != null) {
				if (!connection.isClosed()) connection.close();
			}
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/igminecraft_testing?verifyServerCertificate=false&maxReconnects=10&useSSL=true&retainStatementAfterResultSetClose=true&autoReconnect=true",
					"igminecraft", "7xXTvmgXyrUGvsh6");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static Statement GetStatement() {
		try {
			if (connection == null) {
				if (IGPrison.environment.equals(IGEnvironments.MAIN)) {
					ConnectToMain();
					Bukkit.getLogger().log(Level.INFO, "Connected to Main Database");
				}
				else {
					ConnectToTesting();
					Bukkit.getLogger().log(Level.INFO, "Connected to Testing Database");
				}
			}

			if (connection.isClosed()) {
				if (IGPrison.environment.equals(IGEnvironments.MAIN)) {
					ConnectToMain();
					Bukkit.getLogger().log(Level.INFO, "Connected to Main Database");
				}
				else {
					ConnectToTesting();
					Bukkit.getLogger().log(Level.INFO, "Connected to Testing Database");
				}
			}
			
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			activeStatements.add(statement);
			
			return statement;
		} catch (Exception ex) {
			return null;
		}
	}

}
