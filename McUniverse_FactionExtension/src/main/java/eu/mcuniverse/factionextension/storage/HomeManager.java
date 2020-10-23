package eu.mcuniverse.factionextension.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;

import com.google.gson.Gson;

import eu.mcuniverse.factionextension.main.Core;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HomeManager {

	@Getter(value = AccessLevel.PRIVATE)
	@Setter
	private Connection connection = Core.getMysqlManager().getConnection();

	@SneakyThrows(SQLException.class)
	public void setup() {
		if (Core.getMysqlManager().isConnected()) {
			Statement statement = getConnection().createStatement();
			statement.execute("CREATE TABLE IF NOT EXISTS homes (" + "UUID VARCHAR(36) NOT NULL" + ", location VARCHAR(500) NOT NULL)");
			statement.close();
		}
	}

	@SneakyThrows(SQLException.class)
	public boolean hasHomeLocation(UUID uuid) {
		PreparedStatement ps = getConnection().prepareStatement("SELECT COUNT(1) AS result FROM homes WHERE UUID = ?");
		ps.setString(1, uuid.toString());
		ResultSet rs = ps.executeQuery();
		rs.next();
		boolean res = rs.getInt("result") > 0; 
		rs.close();
		ps.close();
		return res;
	}

	@SneakyThrows(SQLException.class)
	public void setHome(UUID uuid, Location location) {
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
	public Location getHome(UUID uuid) {
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
	public void updateHome(UUID uuid, Location location) {
		if (!hasHomeLocation(uuid)) {
			setHome(uuid, location);
			return;
		}
		PreparedStatement ps = getConnection().prepareStatement("UPDATE homes SET location = ? WHERE UUID = ?");
		ps.setString(1, new Gson().toJson(location.serialize()));
		ps.setString(2, uuid.toString());
		ps.executeUpdate();
		ps.close();
	}

	@SneakyThrows(SQLException.class)
	public void deleteHome(UUID uuid) {
		PreparedStatement ps = getConnection().prepareStatement("DELETE FROM homes WHERE UUID = ?");
		ps.setString(1, uuid.toString());
		ps.execute();
		ps.close();
	}

	@SuppressWarnings("unchecked")
	@SneakyThrows(SQLException.class)
	public Object2ObjectOpenHashMap<UUID, Location> getallHomesWithUUID() {
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
