package eu.mcuniverse.rocket.commands.rocketadmin.subcommand;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import eu.mcuniverse.rocket.data.Messages;
import eu.mcuniverse.rocket.sql.MySQL;
import eu.mcuniverse.universeapi.command.SubCommand;

public class SQLCommand extends SubCommand {

	@Override
	public String getName() {
		return "sql";
	}

	@Override
	public String getDescription() {
		return "Perform a SQL-Statement";
	}

	@Override
	public String getSyntax() {
		return "/rocketadmin sql <SQL>";
	}

	@Override
	public void perform(Player player, String[] args) {
		if (!player.getName().equalsIgnoreCase("JayReturns")) {
			player.sendMessage(Messages.WARNING + "You're not JayReturns!");
			return;
		}
		String sql = "";
		for (int i = 1; i < args.length; i++) {
			sql += args[i] + " ";
		}
		sql = sql.substring(0, sql.length() - 1);
		
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			player.sendMessage(Messages.PREFIX + "Querying " + sql);
			int cols = rsmd.getColumnCount();
			while (rs.next()) {
				String s = "";
				for (int i = 1; i <= cols; i++) {
					if (i > 1) {
						s += ", ";
					}
					String val = rs.getString(i);
					s += val + " " + rsmd.getColumnName(i);
				}
				player.sendMessage(s);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			player.sendMessage(Messages.ERROR + e.getLocalizedMessage());
			return;
		}
	}

}
