
package eu.mcuniverse.rocket.oil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.ArmorStand;

import eu.mcuniverse.rocket.main.Core;
import eu.mcuniverse.rocket.sql.MySQL;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RefineryStorageManager {

	@Getter
	private Connection connection = MySQL.getConnection();
	@Getter
	private final String DB_PATH = Core.getInstance().getDataFolder() + "/refinery.db";

//	static {
//		try {
//			Class.forName("org.sqlite.JDBC");
//		} catch (ClassNotFoundException e) {
//			System.err.println("An error occured during JDBC-Driver loading");
//			e.printStackTrace();
//		}
//	}
//
//	@SneakyThrows(SQLException.class)
//	public void connect() {
//		if (connection != null) {
//			return;
//		}
//		System.out.println("[Weapons] Creating connection to database...");
//		connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
//		if (!connection.isClosed()) {
//			System.out.println("[Weapons] ... Connection establisht");
//		}
//	}
//
//	@SneakyThrows(SQLException.class)
//	public void disconnect() {
//		if (connection != null && !connection.isClosed()) {
//			connection.close();
//			if (connection.isClosed()) {
//				System.out.println("[Weapons] Connection to database closed");
//			}
//		}
//	}

	@SneakyThrows(SQLException.class)
	public void setup() {
		Statement statement = getConnection().createStatement();
//		statement.executeUpdate(
//				"CREATE TABLE IF NOT EXISTS refinery (UUID VARCHAR(36), name VARCHAR(16), location VARCHAR(100), time )");
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS refinery" + " (UUID VARCHAR(36) UNIQUE,"
				+ " name VARCHAR(16)," + " location VARCHAR(100)," + " time INTEGER," + " armorstand VARCHAR(100),"
				+ " oil_amount INTEGER," + " PRIMARY KEY(UUID));");
		statement.close();

	}

	@SneakyThrows(SQLException.class)
	public Location getLocation(UUID uuid) {
		PreparedStatement ps = getConnection().prepareStatement("SELECT location FROM refinery WHERE UUID = ?");
		ps.setString(1, uuid.toString());
		ResultSet rs = ps.executeQuery();
		Location loc = null;
		if (rs.next()) {
			loc = RefineryLocationSerializer.deserialize(rs.getString("location"));
		}
		ps.close();
		rs.close();
		return loc;
	}

	@SneakyThrows(SQLException.class)
	public boolean hasRefinery(UUID uuid) {
		PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM refinery WHERE UUID = ?");
		ps.setString(1, uuid.toString());
		ResultSet rs = ps.executeQuery();
		boolean temp = rs.next();
		ps.close();
		rs.close();
		return temp;
	}

	@SneakyThrows(SQLException.class)
	public void updateName(UUID uuid, String newName) {
		PreparedStatement ps = getConnection().prepareStatement("UPDATE refinery SET name = ? WHERE UUID = ?");
		ps.setString(1, newName);
		ps.setString(2, uuid.toString());
		ps.executeUpdate();
		ps.close();
	}

	@SneakyThrows(SQLException.class)
	public void insert(OfflinePlayer player, Location location, ArmorStand armorStand) {
		PreparedStatement ps = getConnection()
				.prepareStatement("INSERT INTO refinery (UUID, name, location, time, armorstand, oil_amount) VALUES (?, ?, ?, ?, ?, ?)");
		ps.setString(1, player.getUniqueId().toString());
		ps.setString(2, player.getName());
		ps.setString(3, RefineryLocationSerializer.serialize(location));
		ps.setInt(4, RefineryManager.TIME);
		ps.setString(5, armorStand.getUniqueId().toString());
		ps.setInt(6, 0);
		ps.execute();
		ps.close();
	}

	@SneakyThrows(SQLException.class)
	public void resetTime(OfflinePlayer player) {
		PreparedStatement ps = getConnection().prepareStatement("UPDATE refinery SET time = ? WHERE UUID = ?");
		ps.setInt(1, RefineryManager.TIME);
		ps.setString(2, player.getUniqueId().toString());
		ps.executeUpdate();
		ps.close();
	}
	
	@SneakyThrows(SQLException.class)
	public int getTime(OfflinePlayer player) {
		PreparedStatement ps = getConnection().prepareStatement("SELECT time FROM refinery WHERE UUID = ?");
		ps.setString(1, player.getUniqueId().toString());
		ResultSet rs = ps.executeQuery();
		int time = -1;
		if (rs.next()) {
			time = rs.getInt("time");
		}
		rs.close();
		ps.close();
		return time;
	}

	@SneakyThrows(SQLException.class)
	public void removeTime(OfflinePlayer player, int amount) {
		int oldTime = getTime(player);
		PreparedStatement ps = getConnection().prepareStatement("UPDATE refinery SET time = ? WHERE UUID = ?");
		ps.setInt(1, oldTime - amount);
		ps.setString(2, player.getUniqueId().toString());
		ps.executeUpdate();
		ps.close();
	}

	@SneakyThrows(SQLException.class)
	public void delete(UUID uuid) {
		PreparedStatement ps = getConnection().prepareStatement("DELETE FROM refinery WHERE UUID = ?");
		ps.setString(1, uuid.toString());
		ps.executeUpdate();
		ps.close();
	}

	@SneakyThrows(SQLException.class)
	public int getOilAmount(OfflinePlayer player) {
		PreparedStatement ps = getConnection().prepareStatement("SELECT oil_amount FROM refinery WHERE UUID = ?");
		ps.setString(1, player.getUniqueId().toString());
		ResultSet rs = ps.executeQuery();
		int amount = -1;
		if (rs.next()) {
			amount = rs.getInt("oil_amount");
		}
		rs.close();
		ps.close();
		return amount;
	}

	@SneakyThrows(SQLException.class)
	public void updateOilAmount(OfflinePlayer player, int amount) {
		PreparedStatement ps = getConnection().prepareStatement("UPDATE refinery SET oil_amount = ? WHERE UUID = ?");
		ps.setInt(1, amount);
		ps.setString(2, player.getUniqueId().toString());
		ps.executeUpdate();
		ps.close();
	}

	@SneakyThrows(SQLException.class)
	public UUID getArmorstandUUID(OfflinePlayer player) {
		PreparedStatement ps = getConnection().prepareStatement("SELECT armorstand FROM refinery WHERE UUID = ?");
		ps.setString(1, player.getUniqueId().toString());
		ResultSet rs = ps.executeQuery();
		UUID uuid = null;
		if (rs.next()) {
			uuid = UUID.fromString(rs.getString("armorstand"));
		}
		rs.close();
		ps.close();
		return uuid;
	}

	@SneakyThrows(SQLException.class)
	public ObjectOpenHashSet<Location> getLocations() {
		ObjectOpenHashSet<Location> result = new ObjectOpenHashSet<Location>();
		PreparedStatement ps = getConnection().prepareStatement("SELECT location FROM refinery");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			try {
				result.add(RefineryLocationSerializer.deserialize(rs.getString("location")));
			} catch (IllegalArgumentException e) {
				Core.getInstance().getLogger().log(Level.SEVERE, "RefineryStorageManager.getLocations(): " + e.getLocalizedMessage());
			}
		}
		ps.close();
		rs.close();
		return result;
	}

	@SneakyThrows(SQLException.class)
	public ObjectOpenHashSet<UUID> getUUIDs() {
		ObjectOpenHashSet<UUID> result = new ObjectOpenHashSet<UUID>();
		Statement statement = getConnection().createStatement();
		ResultSet rs = statement.executeQuery("SELECT UUID FROM refinery");
		while (rs.next()) {
			result.add(UUID.fromString(rs.getString("UUID")));
		}
		rs.close();
		statement.close();
		return result;
	}
	
}
