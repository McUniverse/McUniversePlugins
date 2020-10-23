package eu.mcuniverse.universeapi.settings;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eu.mcuniverse.universeapi.sql.MySQL;
import lombok.SneakyThrows;

public class SettingsManager extends MySQL {

	private static SettingsManager instance;
	
	private String tableName = "settings";
	
	public static SettingsManager getInstance() {
		if (instance == null) {
			instance = new SettingsManager();
		}
		return instance;
	}
	
	@Override
	public String getTableName() {
		return "settings";
	}
	
	@Override
	public String getDatabaseName() {
		return "settings";
	}
	
	/**
	 * Get the value for a given key
	 * @param key The key
	 * @return The value
	 */
	@SneakyThrows(SQLException.class)
	public String getValue(String key) {
		PreparedStatement ps = getConnection().prepareStatement("SELECT `value` FROM " + getQueryTable() + " WHERE `key` = ?");
		ps.setString(1, key);
		ResultSet rs = ps.executeQuery();
		if (!rs.next()) {
			throw new IllegalArgumentException("Key " + key + " not found in " + getDatabaseName() + "." + getTableName() + "/" + tableName);
		}
		String value = rs.getString("value");
		ps.close();
		rs.close();
		return value;
	}
	
}
