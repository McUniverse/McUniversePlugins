package eu.mcuniverse.audit.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.UUID;

import eu.mcuniverse.audit.Core;
import eu.mcuniverse.audit.manager.AuditReport;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuditStorageManager {

	private String host, database, username, password, url;
	private int port;
	
	@Getter
	private Connection connection;
	
	public boolean isConnected() {
		return getConnection() != null;
	}
	
	@SneakyThrows(SQLException.class)
	public void connect() {
		host = Core.getInstance().getConfig().getString("mysql.host");
		database = Core.getInstance().getConfig().getString("mysql.database");
		username = Core.getInstance().getConfig().getString("mysql.username");
		password = Core.getInstance().getConfig().getString("mysql.password");
		port = Core.getInstance().getConfig().getInt("mysql.port");
		if (!isConnected()) {
			url = "jdbc:mysql://" + host + ":" + port + "/" + database;
			
			// Throws NPE
//			Properties connectionProperties = new Properties();
//			connectionProperties.put("user", username);
//			connectionProperties.put("password", password);
//			connectionProperties.put("autoReconnect", true);
//			connectionProperties.put("verifyServerCertificate", false);
//			connectionProperties.put("useSSL", false);
			
//			connection = DriverManager.getConnection(url, connectionProperties);
			
			connection = DriverManager.getConnection(url + "?autoReconnect=true&verifyServerCertificate=false&useSSL=false", username, password);
			Core.getInstance().getLogger().info("MySQL connected to " + url);
			
			setup();
		}
	}
	
	@SneakyThrows(SQLException.class)
	public void disconnect() {
		if (isConnected()) {
			getConnection().close();
			Core.getInstance().getLogger().info("MySQL disconnected from " + url);
		}
	}

	@SneakyThrows(SQLException.class)
	public void setup() {
		if (!isConnected()) {
			return;
		}
		Statement statement = getConnection().createStatement();
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS `deaths` (\n" + 
				"  `id` INT NOT NULL AUTO_INCREMENT,\n" + 
				"  `time` DATETIME NULL,\n" + 
				"  `UUID` VARCHAR(36) NULL,\n" + 
				"  `name` VARCHAR(16) NULL,\n" + 
				"  `inventory_content` TEXT NULL,\n" + 
				"  `location` TEXT NULL,\n" + 
				"  `death_message` TEXT NULL,\n" + 
				"  `exp` INT NULL,\n" + 
				"  PRIMARY KEY (`id`))");
		statement.close();
	}
	
	@SneakyThrows(SQLException.class)
	public boolean hasAuditReport(UUID uuid) {
		PreparedStatement ps = getConnection().prepareStatement("SELECT COUNT(1) AS result FROM deaths WHERE UUID = ?");
		ps.setString(1, uuid.toString());
		ResultSet rs = ps.executeQuery();
		rs.next();
		boolean ret = rs.getInt("result") > 0;
		rs.close();
		ps.close();
		
		return ret;
	}
	
	@SneakyThrows(SQLException.class)
	public boolean hasAuditReport(String name) {
		PreparedStatement ps = getConnection().prepareStatement("SELECT COUNT(1) AS result FROM deaths WHERE name = ?");
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		rs.next();
		boolean ret = rs.getInt("result") > 0;
		rs.close();
		ps.close();
		
		return ret;
	}

	@SneakyThrows(SQLException.class)
	public boolean doesReportExist(int id) {
		PreparedStatement ps = getConnection().prepareStatement("SELECT COUNT(1) AS result FROM deaths WHERE id = ?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		boolean ret = rs.getInt("result") == 1;
		rs.close();
		ps.close();
		
		return ret;
	}
	
	@SneakyThrows(SQLException.class)
	public void add(AuditReport audit) {
		PreparedStatement ps = getConnection().prepareStatement("INSERT INTO deaths (time, UUID, name, inventory_content, "
				+ "location, death_message, exp) VALUES (?, ?, ?, ?, ?, ?, ?)");
		ps.setTimestamp(1, audit.getTimestamp());
		ps.setString(2, audit.getUUID().toString());
		ps.setString(3, audit.getName());
		ps.setString(4, audit.getInventoryContent());
		ps.setString(5, audit.getLocation());
		ps.setString(6, audit.getDeathMessage());
		ps.setInt(7, audit.getExp());
		ps.execute();
		ps.close();
	}

	@SneakyThrows(SQLException.class)
	public void updateName(UUID uuid, String name) {
		PreparedStatement ps = getConnection().prepareStatement("UPDATE deaths SET name = ? WHERE UUID = ?");
		ps.setString(1, name);
		ps.setString(2, uuid.toString());
		ps.executeUpdate();
		ps.close();
	}
	
	
	@SneakyThrows(SQLException.class)
	public void deleteRecord(int id) {
		PreparedStatement ps = getConnection().prepareStatement("DELETE FROM deaths WHERE id = ?");
		ps.setInt(1, id);
		ps.execute();
		ps.close();
	}
	
	@SneakyThrows(SQLException.class)
	public AuditReport getAudit(int id) {
		PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM deaths WHERE id = ?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		
		AuditReport ao = getAuditObject(rs);
		
		rs.close();
		ps.close();
		
		return ao;
	}
	
	@SneakyThrows(SQLException.class)
	public ObjectArrayList<AuditReport> getAllAudits(String name) {
		PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM deaths WHERE name = ?");
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		
		ObjectArrayList<AuditReport> list = new ObjectArrayList<AuditReport>();
		
		while (rs.next()) {
			list.add(getAuditObject(rs));
		}
		
		ps.close();
		rs.close();
		return list;
	}
	
	@SneakyThrows(SQLException.class)
	public ObjectArrayList<AuditReport> getAllAudits(UUID uuid) {
		PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM deaths WHERE UUID = ?");
		ps.setString(1, uuid.toString());
		ResultSet rs = ps.executeQuery();
		
		ObjectArrayList<AuditReport> list = new ObjectArrayList<AuditReport>();
		
		while (rs.next()) {
			list.add(getAuditObject(rs));
		}
		
		ps.close();
		rs.close();
		return list;
	}
	
	@SneakyThrows(SQLException.class)
	public IntArrayList getAllIDs() {
		IntArrayList result = new IntArrayList();
		
		Statement statement = getConnection().createStatement();
		ResultSet rs = statement.executeQuery("SELECT id FROM deaths");
		while (rs.next()) {
			result.add(rs.getInt("id"));
		}
		
		statement.close();
		rs.close();
		
		return result;
	}
	
	@SneakyThrows(SQLException.class)
	private AuditReport getAuditObject(ResultSet resultSet) {
		int id = resultSet.getInt("id");
		Timestamp timestamp = resultSet.getTimestamp("time");
		UUID uuid = UUID.fromString(resultSet.getString("UUID"));
		String name = resultSet.getString("name");
		String inventoryContent = resultSet.getString("inventory_content");
		String location = resultSet.getString("location");
		String deathMessage = resultSet.getString("death_message");
		int exp = resultSet.getInt("exp");
		
		AuditReport ao = new AuditReport(id, timestamp, uuid, name, inventoryContent, location, deathMessage, exp);
		
		return ao;
	}
	
}
