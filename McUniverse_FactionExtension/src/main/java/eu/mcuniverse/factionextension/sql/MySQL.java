package eu.mcuniverse.factionextension.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;

import com.google.gson.Gson;

import eu.mcuniverse.factionextension.data.Messages;
import eu.mcuniverse.factionextension.main.Core;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
@Deprecated
public class MySQL {

	private String host = Core.getConfigManager().getSettings().getString("mysql.host");
	private String database = Core.getConfigManager().getSettings().getString("mysql.database");
	private String username = Core.getConfigManager().getSettings().getString("mysql.username");
	private String password = Core.getConfigManager().getSettings().getString("mysql.password");
	private int port = Core.getConfigManager().getSettings().getInt("mysql.port");

	@Getter
	private Connection connection;

	public boolean isConnected() {
		return getConnection() != null;
	}

	@SneakyThrows(SQLException.class)
	public void connect() {
		if (!isConnected()) {
			connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database
					+ "?autoReconnect=true&useSSL=false&verifyServerCertificate=false", username, password);
			System.out.println(Messages.CONSOLE_PREFIX + "MySQL successfully connected");
		}
	}

	@SneakyThrows(SQLException.class)
	public void disconnect() {
		if (isConnected()) {
			getConnection().close();
			System.out.println(Messages.CONSOLE_PREFIX + "MySQL successfully disconnected!");
		}
	}

	@SneakyThrows(SQLException.class)
	public void setup() {
		if (isConnected()) {
			PreparedStatement ps = getConnection()
					.prepareStatement("CREATE TABLE IF NOT EXISTS factions (UUID VARCHAR(36), tag VARCHAR(10))");
			ps.executeUpdate();
			ps.close();
		}
	}

	@SneakyThrows(SQLException.class)
	public boolean hasFaction(@NonNull UUID uuid) {
		PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM factions WHERE UUID = ?");
		ps.setString(1, uuid.toString());
		ResultSet result = ps.executeQuery();
		boolean res = result.next();
		result.close();
		ps.close();
		return res;
	}

	@SneakyThrows(SQLException.class)
	public String getFactionTag(@NonNull UUID uuid) {
		if (!hasFaction(uuid)) {
			return null;
		}
		PreparedStatement ps = getConnection().prepareStatement("SELECT tag FROM factions WHERE UUID = ?");
		ps.setString(1, uuid.toString());
		ResultSet rs = ps.executeQuery();
		String ret = rs.getString("name");
		ps.close();
		rs.close();
		return ret;
	}

	@SneakyThrows(SQLException.class)
	public void updatePlayer(@NonNull UUID uuid, @NonNull String tag) {
		if (!hasFaction(uuid)) {
			return;
		}

		PreparedStatement ps = getConnection().prepareStatement("UPDATE factions SET tag = ? WHERE UUID = ?");
		ps.setString(1, tag);
		ps.setString(2, uuid.toString());
		ps.executeUpdate();
		ps.close();

	}

	@SneakyThrows(SQLException.class)
	public void registerPlayer(@NonNull UUID uuid, @NonNull String name) {
		if (hasFaction(uuid)) {
			return;
		}

		PreparedStatement ps = getConnection().prepareStatement("INSERT INTO factions (UUID, tag) VALUES (?, ?)");
		ps.setString(1, uuid.toString());
		ps.setString(2, name);
		ps.execute();
		ps.close();
	}

	@SneakyThrows(SQLException.class)
	public void deletePlayer(@NonNull UUID uuid) {
		if (!hasFaction(uuid)) {
			return;
		}
		PreparedStatement ps = getConnection().prepareStatement("DELETE FROM factions WHERE UUID = ?");
		ps.setString(1, uuid.toString());
		ps.execute();
		ps.close();
	}

	@UtilityClass
	public class Home {

		@SneakyThrows(SQLException.class)
		public void setup() {
			if (isConnected()) {
				PreparedStatement ps = getConnection()
						.prepareStatement("CREATE TABLE IF NOT EXISTS homes (UUID VARCHAR(36), location VARCHAR(300))");
				ps.executeUpdate();
				ps.close();
			}
		}

		@SneakyThrows(SQLException.class)
		public boolean hasHomeLocation(@NonNull UUID uuid) {
			PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM homes WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet result = ps.executeQuery();
			boolean res = result.next();
			result.close();
			ps.close();
			return res;
		}

		@SneakyThrows(SQLException.class)
		public void insertUser(UUID uuid, Location location) {
			if (hasHomeLocation(uuid)) {
				return;
			}
			PreparedStatement ps = getConnection().prepareStatement("INSERT INTO homes (UUID, location) VALUES (?, ?)");
			ps.setString(1, uuid.toString());
			ps.setString(2, new Gson().toJson(location.serialize()));
			ps.execute();
			ps.close();
		}

		@SuppressWarnings("unchecked")
		@SneakyThrows(SQLException.class)
		public Location getLocation(UUID uuid) {
			if (!hasHomeLocation(uuid)) {
				return null;
			}
			PreparedStatement ps = getConnection().prepareStatement("SELECT location FROM homes WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String loc = rs.getString("location");
				rs.close();
				ps.close();
				return Location.deserialize(new Gson().fromJson(loc, Map.class));
			} else {
				return null;
			}
		}

		@SneakyThrows(SQLException.class)
		public void updateLocation(UUID uuid, Location location) {
			if (!hasHomeLocation(uuid)) {
				insertUser(uuid, location);
				return;
			}
			PreparedStatement ps = getConnection().prepareStatement("UPDATE homes SET location = ? WHERE UUID = ?");
			ps.setString(1, new Gson().toJson(location.serialize()));
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
			ps.close();
		}

		@SneakyThrows(SQLException.class)
		public void deleteLocation(UUID uuid) {
			PreparedStatement ps = getConnection().prepareStatement("DELETE FROM homes WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ps.execute();
			ps.close();
		}

		@SuppressWarnings("unchecked")
		@SneakyThrows(SQLException.class)
		public Object2ObjectOpenHashMap<UUID, Location> getAllHomesWithUUID() {
			PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM homes");
			ResultSet rs = ps.executeQuery();
			Object2ObjectOpenHashMap<UUID, Location> locs = new Object2ObjectOpenHashMap<UUID, Location>();
			while (rs.next()) {
				locs.put(UUID.fromString(rs.getString("UUID")),
						Location.deserialize(new Gson().fromJson(rs.getString("location"), Map.class)));
			}
			ps.close();
			rs.close();
			return locs;
		}

	}

	@UtilityClass
	public class Rocketlanding {

		@SneakyThrows(SQLException.class)
		public void setup() {
			if (isConnected()) {
				PreparedStatement ps = getConnection()
						.prepareStatement("CREATE TABLE IF NOT EXISTS rocketlanding (tag VARCHAR(10), location VARCHAR(300))");
				ps.executeUpdate();
				ps.close();
			}
		}

		@SneakyThrows(SQLException.class)
		public boolean hasLocation(String tag) {
			PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM rocketlanding WHERE tag = ?");
			ps.setString(1, tag);
			ResultSet rs = ps.executeQuery();
			boolean res = rs.next();
			ps.close();
			rs.close();
			return res;
		}

		@SneakyThrows(SQLException.class)
		public void insertLocation(String tag, Location location) {
			PreparedStatement ps = getConnection()
					.prepareStatement("INSERT INTO rocketlanding (tag, location) VALUES (?, ?)");
			ps.setString(1, tag);
			ps.setString(2, new Gson().toJson(location.serialize()));
			ps.execute();
			ps.close();
		}

		@SneakyThrows(SQLException.class)
		public void updateLocation(String tag, Location location) {
			if (!hasLocation(tag)) {
				insertLocation(tag, location);
				return;
			}
			PreparedStatement ps = getConnection().prepareStatement("UPDATE rocketlanding SET location = ? WHERE tag = ?");
			ps.setString(1, new Gson().toJson(location.serialize()));
			ps.setString(2, tag);
			ps.executeUpdate();
			ps.close();
		}

		@SuppressWarnings("unchecked")
		@SneakyThrows(SQLException.class)
		public Location getLocation(String tag) {
			if (!hasLocation(tag)) {
				return null;
			}
			PreparedStatement ps = getConnection().prepareStatement("SELECT location FROM rocketlanding WHERE tag = ?");
			ps.setString(1, tag);
			ResultSet rs = ps.executeQuery();
			String loc = null;
			if (rs.next()) {
				loc = rs.getString("location");
			}
			ps.close();
			rs.close();
			if (loc != null) {
				return Location.deserialize(new Gson().fromJson(loc, Map.class));
			} else {
				return null;
			}
		}

		@SneakyThrows(SQLException.class)
		public void deleteLocation(String tag) {
			if (!hasLocation(tag)) {
				return;
			}
			PreparedStatement ps = getConnection().prepareStatement("DELETE FROM rocketlanding WHERE tag = ?");
			ps.setString(1, tag);
			ps.execute();
			ps.close();
		}

	}

}
