package eu.mcuniverse.universeapi.sql;

import java.sql.Connection;

public interface IMySQL {

	public ConnectionProperties getConnectionProperties();
	
	public Connection getConnection();
	
	public void connect();
	
	public void disconnect();
	
	public default boolean isConnected() {
		return getConnection() != null;
	}
	
}
