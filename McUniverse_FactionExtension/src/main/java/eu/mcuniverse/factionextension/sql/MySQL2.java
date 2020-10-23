package eu.mcuniverse.factionextension.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.configuration.file.FileConfiguration;

import eu.mcuniverse.factionextension.data.Messages;
import eu.mcuniverse.factionextension.main.Core;
import eu.mcuniverse.universeapi.sql.ConnectionProperties;
import eu.mcuniverse.universeapi.sql.IMySQL;
import lombok.Getter;
import lombok.SneakyThrows;

public class MySQL2 implements IMySQL {
	
	@Getter
	private Connection connection;
	
	@Override
	public ConnectionProperties getConnectionProperties() {
		FileConfiguration cfg = Core.getConfigManager().getSettings();
		return ConnectionProperties.builder()
				.host(cfg.getString("mysql.host"))
				.database(cfg.getString("mysql.database"))
				.username(cfg.getString("mysql.username"))
				.password(cfg.getString("mysql.password"))
				.port(cfg.getInt("mysql.port"))
				.build();
	}

	@Override
	@SneakyThrows(SQLException.class)
	public void connect() {
		if (!isConnected()) {
			connection = DriverManager.getConnection(getConnectionProperties().getURL(), getConnectionProperties().getUsername(), getConnectionProperties().getPassword());
			System.out.println(Messages.CONSOLE_PREFIX + "MySQL successfully connected to " + getConnectionProperties().getDatabase() + ".");
		}
	}

	@Override
	@SneakyThrows(SQLException.class)
	public void disconnect() {
		if (isConnected()) {
			getConnection().close();
			System.out.println(Messages.CONSOLE_PREFIX + "MySQL successfully disconnected.");
		}
	}
	
	@SneakyThrows(SQLException.class)
	public boolean execute(String sql) {
		Statement statement = getConnection().createStatement();
		boolean ret = statement.execute(sql);
		statement.close();
		return ret;
	}

}
