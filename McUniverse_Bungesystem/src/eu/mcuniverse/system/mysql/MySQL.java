package eu.mcuniverse.system.mysql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import eu.mcuniverse.system.Bungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class MySQL {
	
	public static String host = Bungee.config.getString("MySQL.Host");
	public static String port = String.valueOf(Bungee.config.getInt("MySQL.Port"));
	public static String database = Bungee.config.getString("MySQL.Datenbank");
	public static String username = Bungee.config.getString("MySQL.Benutzername");
	public static String password = Bungee.config.getString("MySQL.Passwort");
	public static Connection connection;

	public static Connection getConnection() {
		return connection;
	}

	public static boolean isConnected() {
		return connection != null;
	}

	@SuppressWarnings("deprecation")
	public static void connect() {
		if (!isConnected()) {
			try {
				connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username,
						password);
				ProxyServer.getInstance().getConsole()
						.sendMessage(Bungee.prefix + "§aDie Verbindung zu MySQL wurde erfolgreich hergestellt..");
			} catch (SQLException ex) {
				ex.printStackTrace();
				ProxyServer.getInstance().getConsole()
						.sendMessage(Bungee.prefix + "§cMySQL Verbindung konnte nicht hergestellt werden.");
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static void disconnect() {
		if (isConnected()) {
			try {
				connection.close();
				ProxyServer.getInstance().getConsole()
						.sendMessage(Bungee.prefix + "§aDie Verbindung zu MySQL wurde erfolgreich unterbrochen.");
			} catch (SQLException ex) {
				ex.printStackTrace();
				ProxyServer.getInstance().getConsole()
						.sendMessage(Bungee.prefix + "§cMySQL Verbindung konnte nicht getrennt werden.");
			}
		}
	}

	public static void createTableIfNotExists() {
		if (isConnected()) {
			try {
				PreparedStatement ps = getConnection().prepareStatement(
						"CREATE TABLE IF NOT EXISTS Bans (Spielername VARCHAR(100), UUID VARCHAR(100), Grund VARCHAR(100), Von VARCHAR(100), Bis VARCHAR(100))");
				ps.executeUpdate();
				ps.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static boolean isUserExisting(ProxiedPlayer p) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Bans WHERE UUID = ?");
			ps.setString(1, p.getUniqueId().toString());
			ResultSet result = ps.executeQuery();
			boolean user = result.next();
			result.close();
			ps.close();
			return user;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static boolean isUserExisting(String player) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Bans WHERE Spielername = ?");
			ps.setString(1, player);
			ResultSet result = ps.executeQuery();
			boolean user = result.next();
			result.close();
			ps.close();
			return user;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static boolean isUserExisting(UUID uuid) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Bans WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet result = ps.executeQuery();
			boolean user = result.next();
			result.close();
			ps.close();
			return user;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static void registerPlayer(ProxiedPlayer p) {
		if (isUserExisting(p)) {
			return;
		}
		try {
			PreparedStatement ps = connection
					.prepareStatement("INSERT INTO Bans (Spielername, UUID, Grund, Von, Bis) VALUES (?, ?, ?, ?, ?)");
			ps.setString(1, p.getName());
			ps.setString(2, p.getUniqueId().toString());
			ps.setString(3, "-");
			ps.setString(4, "-");
			ps.setString(5, "-");
			ps.execute();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void registerPlayer(String player) {
		if (isUserExisting(player)) {
			return;
		}
		try {
			PreparedStatement ps = connection
					.prepareStatement("INSERT INTO Bans (Spielername, UUID, Grund, Von, Bis) VALUES (?, ?, ?, ?, ?)");
			ps.setString(1, getName(player));
			ps.setString(2, getUUID(player));
			ps.setString(3, "-");
			ps.setString(4, "-");
			ps.setString(5, "-");
			ps.execute();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void setPlayerName(UUID uuid, String name) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE Bans SET Spielername = ? WHERE UUID = ?");
			ps.setString(1, name);
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String getPlayer(String player) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Bans WHERE Spielername = ?");
			ps.setString(1, player);
			ResultSet result = ps.executeQuery();
			result.next();
			String name = result.getString("Spielername");
			result.close();
			ps.close();
			return name;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "null";
	}

	public static String getReason(ProxiedPlayer p) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Bans WHERE UUID = ?");
			ps.setString(1, p.getUniqueId().toString());
			ResultSet result = ps.executeQuery();
			result.next();
			String name = result.getString("Grund");
			result.close();
			ps.close();
			return name;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "null";
	}

	public static String getReason(String player) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Bans WHERE Spielername = ?");
			ps.setString(1, player);
			ResultSet result = ps.executeQuery();
			result.next();
			String name = result.getString("Grund");
			result.close();
			ps.close();
			return name;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "null";
	}

	public static String getReason(UUID uuid) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Bans WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet result = ps.executeQuery();
			result.next();
			String name = result.getString("Grund");
			result.close();
			ps.close();
			return name;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "null";
	}

	public static String getBy(ProxiedPlayer p) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Bans WHERE UUID = ?");
			ps.setString(1, p.getUniqueId().toString());
			ResultSet result = ps.executeQuery();
			result.next();
			String name = result.getString("Von");
			result.close();
			ps.close();
			return name;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "null";
	}

	public static String getBy(String player) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Bans WHERE Spielername = ?");
			ps.setString(1, player);
			ResultSet result = ps.executeQuery();
			result.next();
			String name = result.getString("Von");
			result.close();
			ps.close();
			return name;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "null";
	}

	public static String getBy(UUID uuid) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Bans WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet result = ps.executeQuery();
			result.next();
			String name = result.getString("Von");
			result.close();
			ps.close();
			return name;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "null";
	}

	public static long getUntil(ProxiedPlayer p) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Bans WHERE UUID = ?");
			ps.setString(1, p.getUniqueId().toString());
			ResultSet result = ps.executeQuery();
			result.next();
			long until = result.getLong("Bis");
			result.close();
			ps.close();
			return until;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return -1L;
	}

	public static long getUntil(String player) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Bans WHERE Spielername = ?");
			ps.setString(1, player);
			ResultSet result = ps.executeQuery();
			result.next();
			long until = result.getLong("Bis");
			result.close();
			ps.close();
			return until;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return -1L;
	}

	public static long getUntil(UUID uuid) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Bans WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet result = ps.executeQuery();
			result.next();
			long until = result.getLong("Bis");
			result.close();
			ps.close();
			return until;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return -1L;
	}

	public static void setBanned(ProxiedPlayer p, String reason, ProxiedPlayer by, long time) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE Bans SET Grund = ? WHERE UUID = ?");
			ps.setString(1, reason);
			ps.setString(2, p.getUniqueId().toString());
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE Bans SET Von = ? WHERE UUID = ?");
			ps.setString(1, by.getName());
			ps.setString(2, p.getUniqueId().toString());
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE Bans SET Bis = ? WHERE UUID = ?");
			ps.setLong(1, time);
			ps.setString(2, p.getUniqueId().toString());
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void setBanned(String player, String reason, ProxiedPlayer by, long time) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE Bans SET Grund = ? WHERE UUID = ?");
			ps.setString(1, reason);
			ps.setString(2, getUUID(player));
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE Bans SET Von = ? WHERE UUID = ?");
			ps.setString(1, by.getName());
			ps.setString(2, getUUID(player));
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE Bans SET Bis = ? WHERE UUID = ?");
			ps.setLong(1, time);
			ps.setString(2, getUUID(player));
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void setUnBanned(ProxiedPlayer p) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM Bans WHERE UUID = ?");
			ps.setString(1, p.getUniqueId().toString());
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void setUnBanned(String player) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM Bans WHERE UUID = ?");
			ps.setString(1, getUUID(player));
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void setUnBanned(UUID uuid) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM Bans WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String getName(String username) {
		HashMap<String, String> names = new HashMap<String, String>();
		try {
			URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
			InputStream stream = url.openStream();
			InputStreamReader inr = new InputStreamReader(stream);
			BufferedReader reader = new BufferedReader(inr);
			String s = null;
			StringBuilder sb = new StringBuilder();
			while ((s = reader.readLine()) != null) {
				sb.append(s);
			}
			String result = sb.toString();
			JsonElement element = new JsonParser().parse(result);
			if (element.isJsonObject()) {
				JsonObject obj = element.getAsJsonObject();
				String name = obj.get("name").getAsString();
				names.put(username, name);
				return (String) names.get(username);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getUUID(String username) {
		HashMap<String, String> uuids = new HashMap<String, String>();
		try {
			URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
			InputStream stream = url.openStream();
			InputStreamReader inr = new InputStreamReader(stream);
			BufferedReader reader = new BufferedReader(inr);
			String s = null;
			StringBuilder sb = new StringBuilder();
			while ((s = reader.readLine()) != null) {
				sb.append(s);
			}
			String result = sb.toString();
			JsonElement element = new JsonParser().parse(result);
			if (element.isJsonObject()) {
				JsonObject obj = element.getAsJsonObject();
				String uuid = obj.get("id").toString();
				uuid = uuid.substring(1);
				uuid = uuid.substring(0, uuid.length() - 1);
				String finaluuid = uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16)
						+ "-" + uuid.substring(16, 20) + "-" + uuid.substring(20, 32);
				uuids.put(username, finaluuid);
				return (String) uuids.get(username);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getTempBanLength(long endOfBan) {
		String message = "";
		long now = System.currentTimeMillis();
		long diff = endOfBan - now;
		long seconds = diff / 1000L;
		if (seconds >= 604800L) {
			long weeks = seconds / 604800L;
			seconds %= 604800L;
			if (weeks != 1L) {
				message = message + weeks + " Wochen ";
			} else {
				message = message + weeks + " Woche ";
			}
		}
		if (seconds >= 86400L) {
			long days = seconds / 86400L;
			seconds %= 86400L;
			if (days != 1L) {
				message = message + days + " Tage ";
			} else {
				message = message + days + " Tag ";
			}
		}
		if (seconds >= 3600L) {
			long hours = seconds / 3600L;
			seconds %= 3600L;
			if (hours != 1L) {
				message = message + hours + " Stunden ";
			} else {
				message = message + hours + " Stunde ";
			}
		}
		if (seconds >= 60L) {
			long minutes = seconds / 60L;
			seconds %= 60L;
			if (minutes != 1L) {
				message = message + minutes + " Minuten ";
			} else {
				message = message + minutes + " Minute ";
			}
		}
		if (seconds > 0L) {
			if (seconds != 1L) {
				message = message + seconds + " Sekunden ";
			} else {
				message = message + seconds + " Sekunde ";
			}
		}
		return message;
	}
}
