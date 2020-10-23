package eu.mcuniverse.factionextension.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.bukkit.Location;

import com.google.gson.Gson;

import eu.mcuniverse.factionextension.main.Core;
import eu.mcuniverse.universeapi.rockets.Planet;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FactionRocketManager {

	@Getter(value = AccessLevel.PRIVATE)
	@Setter
	private Connection connection = Core.getMysqlManager().getConnection();
	
	@SneakyThrows(SQLException.class)
	public void setup() {
		if (Core.getMysqlManager().isConnected()) {
			Statement statement = getConnection().createStatement();
			statement.execute("CREATE TABLE IF NOT EXISTS rocketlanding ("
					+ "tag VARCHAR(10) NOT NULL,"
					+ "planet VARCHAR(100) NOT NULL,"
					+ "location VARCHAR(300) NOT NULL,"
					+ "PRIMARY KEY (tag, planet))");
			// PRIMARY KEY
			statement.close();
		}
	}
	
	@SneakyThrows(SQLException.class)
	public boolean hasLandingLocation(String tag, Planet planet) { 
		PreparedStatement ps = getConnection().prepareStatement("SELECT COUNT(1) AS result FROM rocketlanding WHERE tag = ? AND planet = ?");
		ps.setString(1, tag);
		ps.setString(2, planet.name());
		ResultSet rs = ps.executeQuery();
		rs.next();
		boolean ret = rs.getInt("result") > 0;
		ps.close();
		rs.close();
		return ret;
	}
	
	@SneakyThrows(SQLException.class)
	public void setLandingLocation(String tag, Planet planet, Location location) {
		PreparedStatement ps = getConnection().prepareStatement("INSERT INTO rocketlanding (tag, planet, location) VALUES (?, ?, ?)");
		ps.setString(1, tag);
		ps.setString(2, planet.name());
		ps.setString(3, new Gson().toJson(location.serialize()));
		ps.execute();
		ps.close();
	}
	
	@SneakyThrows(SQLException.class)
	public void updateLandingLocation(String tag, Planet planet, Location location) {
		PreparedStatement ps = getConnection().prepareStatement("UPDATE rocketlanding SET location ? WHERE tag = ? AND planet = ?");
		ps.setString(1, new Gson().toJson(location.serialize()));
		ps.setString(2, tag);
		ps.setString(3, planet.name());
		ps.executeUpdate();
		ps.close();
	}
	
	@SuppressWarnings("unchecked")
	@SneakyThrows(SQLException.class)
	public Location getLandingLocation(String tag, Planet planet) { 
		if (!hasLandingLocation(tag, planet)) {
			return null;
		}
		PreparedStatement ps = getConnection().prepareStatement("SELECT location FROM rocketlanding WHERE tag = ? AND planet = ?");
		ps.setString(1, tag);
		ps.setString(2, planet.name());
		ResultSet rs = ps.executeQuery();
		rs.next();
		String serialized = rs.getString("location");
		ps.close();
		rs.close();
		return Location.deserialize(new Gson().fromJson(serialized, Map.class));
	}
	
	@SneakyThrows(SQLException.class)
	public void deleteLandingLocation(String tag, Planet planet) {
		if (!hasLandingLocation(tag, planet)) {
			return;
		}
		PreparedStatement ps = getConnection().prepareStatement("DELETE FROM rocketlanding WHERE tag = ? AND planet = ?");
		ps.setString(1, tag);
		ps.setString(2, planet.name());
		ps.execute();
		ps.close();
	}
	
	/**
	 * CAREFULL!
	 */
	@SneakyThrows(SQLException.class)
	public void deleteAllLandingLocations(String tag) {
		PreparedStatement ps = getConnection().prepareStatement("DELETE FROM rocketlanding WHERE tag = ?");
		ps.setString(1, tag);
		ps.execute();
		ps.close();
	}
	
}
