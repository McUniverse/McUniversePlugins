package eu.mcuniverse.universeapi.factions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;

import com.google.gson.Gson;

import eu.mcuniverse.universeapi.rockets.Planet;
import eu.mcuniverse.universeapi.sql.MySQL;
import lombok.NonNull;
import lombok.SneakyThrows;

public class FactionManager extends MySQL {
	
	private static FactionManager instance;
	
	@Override
	public String getTableName() {
		return "factions";
	}
	
	@Override
	public String getDatabaseName() {
		return "factions";
	}
	
	public static FactionManager getInstance() {
		if (instance == null) {
			instance = new FactionManager();
		}
		return instance;
	}
	
	/**
	 * Get the Faction Tag of a given player
	 * @param uuid The UUID of the player
	 * @return The faction tag of the given uuid
	 */
	@SneakyThrows(SQLException.class)
	public String getFactionTag(@NonNull UUID uuid) {
		PreparedStatement ps = getConnection().prepareStatement("SELECT name FROM " + getQueryTable() + " WHERE UUID = ?");
		ps.setString(1, uuid.toString());
		ResultSet rs = ps.executeQuery();
		String res = "null";
		if (rs.next()) {
			res = rs.getString("name");
		}
		rs.close();
		ps.close();
		return res;
	}
	
	/**
	 * Check if a player has a faction
	 * @param uuid The UUID of the palyer
	 * @return True if <code>uuid</code> has a faction
	 */
	@SneakyThrows(SQLException.class)
	public boolean hasFaction(@NonNull UUID uuid) {
		PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM " + getQueryTable() + " WHERE UUID = ?");
		ps.setString(1, uuid.toString());
		ResultSet rs = ps.executeQuery();
		boolean res = rs.next();
		ps.close();
		rs.close();
		return res;
	}
	
	/**
	 * Check if a faction has a rocketlanding location
	 * @param tag The Tag of the faction (<code>getFactionTag(UUID uuid)</code>)
	 * @return True if <code>tag</code> has a rocketlandinglocation
	 */
	@SneakyThrows(SQLException.class)
	public boolean hasLandingLocation(String tag, Planet planet) {
		PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM " + getDatabaseName() + ".rocketlanding WHERE tag = ? AND planet = ?");
		ps.setString(1, tag);
		ps.setString(2, planet.name());
		ResultSet rs = ps.executeQuery();
		boolean res = rs.next();
		ps.close();
		rs.close();
		return res;
	}

	/**
	 * Get the rocketlanding Location of a faction
	 * @param tag The Tag of the faction (<code>getFactionTag(UUID uuid)</code>) 
	 * @return The rocketlanding location of <code>tag</code> or null if none exists
	 */
	@SuppressWarnings("unchecked")
	@SneakyThrows(SQLException.class)
	public Location getLandingLocation(String tag, Planet planet) {
		if (!hasLandingLocation(tag, planet)) {
			return null;
		}
		PreparedStatement ps = getConnection().prepareStatement("SELECT location FROM " + getDatabaseName() + ".rocketlanding WHERE tag = ? AND planet = ?");
		ps.setString(1, tag);
		ps.setString(2, planet.name());
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
	
}
