package eu.mcuniverse.rocket.commands.rocketadmin.subcommand;

import org.bukkit.entity.Player;

import eu.mcuniverse.rocket.rocket.Rocket;
import eu.mcuniverse.rocket.rocket.RocketManager;
import eu.mcuniverse.universeapi.api.UniverseAPI;
import eu.mcuniverse.universeapi.command.SubCommand;
import eu.mcuniverse.universeapi.item.CustomItem;

public class DamageCommand extends SubCommand {

	@Override
	public String getName() {
		return "damage";
	}

	@Override
	public String getDescription() {
		return "Damage the rocket";
	}

	@Override
	public String getSyntax() {
		return "/rocketadmin damage <amount>";
	}

	@Override
	public void perform(Player player, String[] args) {
		
		if (args.length == 1) {
			player.getInventory().addItem(CustomItem.enhancedIron);
			return;
		}
		
		Rocket r = RocketManager.getRocket(player);
		int damage = Integer.parseInt(args[1]);
		r.setHullHits(damage);
		r.saveRocket();
		player.sendMessage(UniverseAPI.getPrefix() + "Changed your rocket damage to " + damage);
		
//		if (!player.getName().equalsIgnoreCase("JayReturns")) {
//			player.sendMessage(Messages.WARNING + "You're not JayReturns!");
//			return;
//		}
//		String sql = "";
//		for (int i = 1; i < args.length; i++) {
//			sql += args[i] + " ";
//		}
//		sql = sql.substring(0, sql.length() - 1);
//		
//		try {
//			PreparedStatement ps = RefineryStorageManager.getConnection().prepareStatement(sql);
//			ResultSet rs = ps.executeQuery();
//			ResultSetMetaData rsmd = rs.getMetaData();
//			player.sendMessage(Messages.PREFIX + "Querying " + sql);
//			int cols = rsmd.getColumnCount();
//			while (rs.next()) {
//				String s = "";
//				for (int i = 1; i <= cols; i++) {
//					if (i > 1) {
//						s += ", ";
//					}
//					String val = rs.getString(i);
//					s += val + " " + rsmd.getColumnName(i);
//				}
//				player.sendMessage(s);
//			}
//			ps.close();
//			rs.close();
//		} catch (SQLException e) {
//			player.sendMessage(Messages.ERROR + e.getLocalizedMessage());
//			return;
//		}
	}

	
	
}
