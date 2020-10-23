package eu.mcuniverse.essentials.command;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import eu.mcuniverse.universeapi.api.UniverseAPI;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moss.factions.shade.com.google.common.collect.Lists;
import net.md_5.bungee.api.ChatColor;

public class TpCommand implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("No Player");
			return true;
		}

		Player p = (Player) sender;

		if (!p.hasPermission("mcuniverse.teleport")) {
			p.sendMessage(UniverseAPI.getNoPermissions());
			return true;
		}

		if (args.length == 0) {
			p.sendMessage(UniverseAPI.getWarning() + "Usage: /tp <Player> [Player]");
			p.sendMessage(UniverseAPI.getWarning() + "Usage: /tp <x> <y> <z> [yaw] [pitch]");
		} else if (args.length == 1) {
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				p.sendMessage(UniverseAPI.getWarning() + "This player is not online: " + ChatColor.YELLOW + args[0]);
				return true;
			}
			if (p.teleport(target)) {
				p.sendMessage(UniverseAPI.getPrefix() + "You teleported yourself to " + ChatColor.YELLOW + target.getName());
			} else {
				p.sendMessage(UniverseAPI.getWarning() + "Could not teleport to " + ChatColor.YELLOW + args[0]);
			}
		} else if (args.length == 2) {
			Player player1 = Bukkit.getPlayer(args[0]);
			Player player2 = Bukkit.getPlayer(args[1]);
			if (player1 == null) {
				p.sendMessage(UniverseAPI.getWarning() + "This player is not online: " + ChatColor.YELLOW + args[0]);
				return true;
			}
			if (player2 == null) {
				p.sendMessage(UniverseAPI.getWarning() + "This player is not online: " + ChatColor.YELLOW + args[1]);
				return true;
			}
			if (player1.teleport(player2)) {
				player1.sendMessage(UniverseAPI.getPrefix() + "Teleported to " + ChatColor.YELLOW + player2.getName());
			} else {
				p.sendMessage(UniverseAPI.getWarning() + "Could not teleport!");
			}
		} else if (args.length == 3 || args.length == 5) {
			try {
				double x, y, z;
				if (args[0].equalsIgnoreCase("~") ) {
					x = p.getLocation().getX();
				} else {
					x = Double.parseDouble(args[0]);
				}
				
				if (args[1].equalsIgnoreCase("~")) {
					y = p.getLocation().getY();
				} else {
					y = Double.parseDouble(args[1]);
				}
				
				if (args[2].equalsIgnoreCase("~")) {
					z = p.getLocation().getZ();
				} else {
					z = Double.parseDouble(args[2]);
				}

				Location loc = new Location(p.getWorld(), x, y, z);

				if (args.length == 5) {
					float yaw = Float.parseFloat(args[3]);
					float pitch = Float.parseFloat(args[4]);
					loc.setYaw(yaw);
					loc.setPitch(pitch);
				}

				if (p.teleport(loc)) {
					p.sendMessage(UniverseAPI.getPrefix() + "Teleported to " + ChatColor.YELLOW + x + ChatColor.GRAY + ", "
							+ ChatColor.YELLOW + y + ChatColor.DARK_GRAY + ", " + ChatColor.YELLOW + z);
				} else {
					p.sendMessage(UniverseAPI.getWarning() + "Could not teleport!");
				}

			} catch (NumberFormatException e) {
				p.sendMessage(UniverseAPI.getWarning() + "Invalid coordinates!");
			}
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		
		if (args.length == 1 || args.length == 2) {
			return Bukkit.getOnlinePlayers().stream()
					.map(p -> p.getName())
					.filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
					.collect(Collectors.toCollection(ObjectArrayList::new));
		}
		return Lists.newArrayList();
		
//		if (args.length == 1) {
//			Player p = (Player) sender;
//			ArrayList<String> list = new ArrayList<String>();
//			Bukkit.getOnlinePlayers().stream().map(players -> players.getName()).forEach(list::add);
//			
//			p.sendMessage(args[0]);
//			
//			Location loc = null;
//			try {
//				loc = p.getTargetBlockExact(10, FluidCollisionMode.SOURCE_ONLY).getLocation();
//			} catch (Exception e) {
//				System.err.println(e.getLocalizedMessage());
//			}
//			
//			if (loc != null) {
//				DecimalFormat df = new DecimalFormat("#.##");
//				list.add(df.format(loc.getX()));
//				list.add(df.format(loc.getX()) + " " + df.format(loc.getY()));
//				list.add(df.format(loc.getX()) + " " + df.format(loc.getY()) + " " + df.format(loc.getZ()));
//			}
//			
//			return list;
//		}
//		return new ArrayList<String>();
	}

}
