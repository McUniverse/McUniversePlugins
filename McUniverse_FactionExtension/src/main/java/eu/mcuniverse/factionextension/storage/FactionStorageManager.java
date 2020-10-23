package eu.mcuniverse.factionextension.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import eu.mcuniverse.factionextension.main.Core;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FactionStorageManager {

	@Getter(value = AccessLevel.PRIVATE)
	@Setter
	private Connection connection = Core.getMysqlManager().getConnection();

	@SneakyThrows(SQLException.class)
	public void setup() {
		if (Core.getMysqlManager().isConnected()) {
			Statement statement = getConnection().createStatement();
			statement.execute(
					"CREATE TABLE IF NOT EXISTS factions (" + "UUID VARCHAR(36) NOT NULL," + "tag VARCHAR(10) NOT NULL)");
			statement.close();
		}
	}

	@SneakyThrows(SQLException.class)
	public boolean hasFaction(UUID uuid) {
		PreparedStatement ps = getConnection().prepareStatement("SELECT COUNT(1) AS result FROM factions WHERE UUID = ?");
		ps.setString(1, uuid.toString());
		ResultSet rs = ps.executeQuery();
		rs.next();
		boolean ret = rs.getInt("result") > 0;
		rs.close();
		ps.close();
		return ret;
	}

	@SneakyThrows(SQLException.class)
	public String getFactionTag(UUID uuid) {
		if (!hasFaction(uuid)) {
			return null;
		}

		PreparedStatement ps = getConnection().prepareStatement("SELECT tag FROM FACTIONS WHERE UUID = ?");
		ps.setString(1, uuid.toString());
		ResultSet rs = ps.executeQuery();
		rs.next();
		String ret = rs.getString("tag");
		ps.close();
		rs.close();
		return ret;
	}

	@SneakyThrows(SQLException.class)
	public void updatePlayer(UUID uuid, String tag) {
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
	public void registerPlayer(UUID uuid, String tag) {
		if (hasFaction(uuid)) {
			return;
		}

		PreparedStatement ps = getConnection().prepareStatement("INSERT INTO factions (UUID, tag) VaLUES (?, ?)");
		ps.setString(1, uuid.toString());
		ps.setString(2, tag);
		ps.execute();
		ps.close();
	}

	@SneakyThrows(SQLException.class)
	public void deletePlayer(UUID uuid) {
		if (!hasFaction(uuid)) {
			return;
		}

		PreparedStatement ps = getConnection().prepareStatement("DELETE FROM factions WHERE UUID = ?");
		ps.setString(1, uuid.toString());
		ps.execute();
		ps.close();

	}

}
