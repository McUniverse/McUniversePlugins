package eu.mcuniverse.rocket.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import com.google.gson.Gson;

import eu.mcuniverse.rocket.data.Messages;
import eu.mcuniverse.rocket.main.Core;
import eu.mcuniverse.rocket.rocket.Rocket;
import eu.mcuniverse.rocket.util.MiscUtil;
import eu.mcuniverse.universeapi.serialization.JsonItemStack;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MySQL {

	private String host = Core.getConfigManager().getSettings().getString("mysql.host");
	private String database = Core.getConfigManager().getSettings().getString("mysql.database");
	private String username = Core.getConfigManager().getSettings().getString("mysql.username");
	private String password = Core.getConfigManager().getSettings().getString("mysql.password");
	private int port = Core.getConfigManager().getSettings().getInt("mysql.port");

	private final String SPLITTER = ",.-.,";

//	private String host = "localhost";
//	private int port = 3306;
//	private String database = "rocket";
//	private String username = "user";
//	private String password = "cSSOMi1U73dtxAXmHiDp";
	@Getter
	private Connection connection;

	public boolean isConnected() {
		return getConnection() != null;
	}

	@SneakyThrows(SQLException.class)
	public void connect() {
		if (!isConnected()) {
			connection = DriverManager.getConnection(
					"jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false&verifyServerCertificate=false", username, password);
			System.out.println(Messages.CONSOLE_PREFIX + "MySQL successfully connected!");
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
			PreparedStatement ps = getConnection().prepareStatement(
					"CREATE TABLE IF NOT EXISTS rockets (name VARCHAR(100), " + "UUID VARCHAR(100), rocket TEXT, storage TEXT)");
			ps.executeUpdate();
			ps.close();
		}
	}

	@SneakyThrows(SQLException.class)
	public boolean hasRocket(@NonNull OfflinePlayer player) {
		PreparedStatement ps = getConnection().prepareStatement("SELECT rocket FROM rockets WHERE UUID = ?");
		ps.setString(1, player.getUniqueId().toString());
		ResultSet result = ps.executeQuery();
		boolean user = result.next();
		result.close();
		ps.close();
		return user;
	}

	@SneakyThrows(SQLException.class)
	public boolean hasRocket(@NonNull UUID uuid) {
		PreparedStatement ps = getConnection().prepareStatement("SELECT rocket FROM rockets WHERE UUID = ?");
		ps.setString(1, uuid.toString());
		ResultSet result = ps.executeQuery();
		boolean user = result.next();
		result.close();
		ps.close();
		return user;
	}

	@SneakyThrows(SQLException.class)
	public boolean hasRocket(@NonNull String name) {
		PreparedStatement ps = getConnection().prepareStatement("SELECT rocket FROM rockets WHERE name = ?");
		ps.setString(1, name);
		ResultSet result = ps.executeQuery();
		boolean user = result.next();
		result.close();
		ps.close();
		return user;
	}

	@SneakyThrows(SQLException.class)
	public void registerPlayer(@NonNull OfflinePlayer p, @NonNull String rocket) {
		if (hasRocket(p)) {
			return;
		}

		PreparedStatement ps = getConnection()
				.prepareStatement("INSERT INTO rockets (name, UUID, rocket, storage) VALUES (?, ?, ?, ?);");
		ps.setString(1, p.getName());
		ps.setString(2, p.getUniqueId().toString());
		ps.setString(3, rocket);
		ps.setString(4, "");
		ps.execute();
		ps.close();
	}

	@SneakyThrows(SQLException.class)
	public String getRocket(@NonNull OfflinePlayer player) {
		if (!hasRocket(player)) {
			return "null";
		}
		PreparedStatement ps = getConnection().prepareStatement("SELECT rocket FROM rockets WHERE UUID = ?");
		ps.setString(1, player.getUniqueId().toString());
		ResultSet rs = ps.executeQuery();
		String result = "null";
		if (rs.next()) {
			result = rs.getString("rocket");
		}
		rs.close();
		ps.close();
		return result;
	}

	@SneakyThrows(SQLException.class)
	public String getRocket(@NonNull String name) {
		if (!hasRocket(name)) {
			return "null";
		}
		PreparedStatement ps = getConnection().prepareStatement("SELECT rocket FROM rockets WHERE name = ?");
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		String result = "null";
		if (rs.next()) {
			result = rs.getString("rocket");
		}
		rs.close();
		ps.close();
		return result;
	}

	@SneakyThrows(SQLException.class)
	public void updateRocket(@NonNull OfflinePlayer player, @NonNull Rocket newRocket) {
		if (hasRocket(player)) {
			PreparedStatement ps = getConnection().prepareStatement("UPDATE rockets SET rocket = ? WHERE UUID = ?");
			Gson gson = new Gson();
			ps.setString(1, gson.toJson(newRocket));
			ps.setString(2, player.getUniqueId().toString());
			ps.executeUpdate();
			ps.close();
		}
	}

	@SneakyThrows(SQLException.class)
	public void updateInventory(@NonNull OfflinePlayer player, @NonNull ObjectArrayList<ItemStack> newStorage) {
		if (hasRocket(player)) {
			String list = "";
			for (ItemStack item : newStorage) {
				String s = JsonItemStack.toJson(item);
				// Don't add splitter on last element
				if (newStorage.indexOf(item) == newStorage.size() - 1) {
					list += s;
				} else {
					list += s + SPLITTER;
				}
			}
			PreparedStatement ps = getConnection().prepareStatement("UPDATE rockets SET `storage` = ? WHERE UUID = ?");
			ps.setString(1, list);
			ps.setString(2, player.getUniqueId().toString());
			ps.executeUpdate();
			ps.close();
		}
	}
	
	@SneakyThrows(SQLException.class)
	public void clearInventory(@NonNull OfflinePlayer player) {
		if (!hasRocket(player)) {
			return;
		}
		PreparedStatement ps = getConnection().prepareStatement("UPDATE rockets SET `storage` = ? WHERE UUID = ?");
		ps.setString(1, "");
		ps.setString(2, player.getUniqueId().toString());
		ps.executeUpdate();
		ps.close();
	}

	@SneakyThrows(SQLException.class)
	public ObjectArrayList<ItemStack> getInventory(@NonNull OfflinePlayer player) {
		if (!hasRocket(player)) {
			return null;
		}
		PreparedStatement ps = getConnection().prepareStatement("SELECT `storage` FROM rockets WHERE UUID = ?");
		ps.setString(1, player.getUniqueId().toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			String storage = rs.getString("storage");
			rs.close();
			ps.close();
			return MiscUtil.convertStringToItemStack(Arrays.asList(storage.split(SPLITTER)));
//			return Arrays.asList(storage.split(SPLITTER));
		} else {
			return null;
		}
	}

	@SneakyThrows(SQLException.class)
	public void updateName(@NonNull OfflinePlayer player) {
		if (hasRocket(player)) {
			PreparedStatement ps = getConnection().prepareStatement("UPDATE rockets SET name = ? WHERE UUID = ?");
			ps.setString(1, player.getName());
			ps.setString(2, player.getUniqueId().toString());
			ps.executeUpdate();
			ps.close();
		}
	}

	@SneakyThrows(SQLException.class)
	public void deleteRocket(@NonNull OfflinePlayer player) {
		if (hasRocket(player)) {
			PreparedStatement ps = getConnection().prepareStatement("DELETE FROM rockets WHERE UUID = ?");
			ps.setString(1, player.getUniqueId().toString());
			ps.executeUpdate();
			ps.close();
		}
	}

	@SneakyThrows(SQLException.class)
	public void deleteRocket(@NonNull String name) {
		if (hasRocket(name)) {
			PreparedStatement ps = getConnection().prepareStatement("DELETE FROM rockets WHERE name = ?");
			ps.setString(1, name);
			ps.executeUpdate();
			ps.close();
		}
	}

}
