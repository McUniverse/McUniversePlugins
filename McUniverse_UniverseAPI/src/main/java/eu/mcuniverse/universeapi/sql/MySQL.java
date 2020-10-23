package eu.mcuniverse.universeapi.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import eu.mcuniverse.universeapi.api.UniverseAPI;
import eu.mcuniverse.universeapi.main.APIMain;
import lombok.Getter;
import lombok.SneakyThrows;

public abstract class MySQL {

	private static String host;
	private static String username;
	private static String password;
	private static int port;
	
	@Getter
	private static Connection connection;

	public static boolean isConnected() {
		return getConnection() != null;
	}

	/**
	 * Disconnect from the database
	 */
	@SneakyThrows(SQLException.class)
	public static void disconnect() {
		if (isConnected()) {
			getConnection().close();
			System.out.println(String.format("[%s] MySQL successfully disconnected",
					APIMain.getInstance().getDescription().getName()));
		}
	}

	/**
	 * Connect to the Database
	 */
	@SneakyThrows(SQLException.class)
	public static void connect() {
		
		host = UniverseAPI.getConfigManager().getConfig().getString("mysql.host");
		username = UniverseAPI.getConfigManager().getConfig().getString("mysql.username");
		password = UniverseAPI.getConfigManager().getConfig().getString("mysql.password");
		port = UniverseAPI.getConfigManager().getConfig().getInt("mysql.port");
		
		if (!isConnected()) {
			connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port
								+ "?autoReconnect=true&verifyServerCertificate=false&useSSL=false", username, password);
			System.out.println(String.format("[%s] MySQL successfully connected", APIMain.getInstance().getDescription().getName()));
		}
	}

	/**
	 * Check if the table for this class exists
	 * 
	 * @return True if the table exists
	 */
	@SneakyThrows(SQLException.class)
	public boolean tableExists() {
		DatabaseMetaData dbm = getConnection().getMetaData();
		ResultSet tables = dbm.getTables(null, null, getTableName(), null);
		return tables.next();
	}

	/**
	 * Get the table name for this class
	 * 
	 * @return The table name
	 */
	public abstract String getTableName();
	
	/**
	 * Get the database name for this class
	 * 
	 * @return The database name
	 */
	public abstract String getDatabaseName();

	/**
	 * Get the complete query table name </br>
	 * Example: Table name: orders, Database name: production </br>
	 * Will return production.orders
	 * @return The full query name
	 */
	public String getQueryTable() {
		return getDatabaseName() + "." + getTableName();
	}
	
}
