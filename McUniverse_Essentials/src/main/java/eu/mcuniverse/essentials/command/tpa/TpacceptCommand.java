package eu.mcuniverse.essentials.command.tpa;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import eu.mcuniverse.essentials.Core;
import eu.mcuniverse.essentials.data.TpaManager;
import eu.mcuniverse.universeapi.api.UniverseAPI;
import moss.factions.shade.com.google.common.collect.Lists;
import net.md_5.bungee.api.ChatColor;

public class TpacceptCommand implements TabExecutor {

	private final String TP_MESSAGE = UniverseAPI.getPrefix() + "Teleporting in " + ChatColor.YELLOW + "%d "
			+ ChatColor.GRAY + "%s";

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(UniverseAPI.getWarning() + "No player");
			return true;
		}

		Player p = (Player) sender;

		if (!TpaManager.hasTpaRequest(p)) {
			p.sendMessage(UniverseAPI.getPrefix() + "You don't have any current tp request");
			return true;
		}
		OfflinePlayer target = Bukkit.getOfflinePlayer(TpaManager.currentTpa.get(p.getUniqueId()));
		if (!target.isOnline()) {
			p.sendMessage(
					UniverseAPI.getPrefix() + ChatColor.YELLOW + target.getName() + ChatColor.RED + " is not online anymore!");
			TpaManager.currentTpa.remove(p.getUniqueId());
			return true;
		}
		Player onlineTarget = (Player) target;
		p.sendMessage(UniverseAPI.getPrefix() + "You accepted the teleport request form " + ChatColor.YELLOW
				+ onlineTarget.getName());
		onlineTarget.sendMessage(
				UniverseAPI.getPrefix() + ChatColor.YELLOW + p.getName() + ChatColor.GRAY + " accepted your teleport request.");
//		onlineTarget.sendMessage(UniverseAPI.getPrefix() + "Teleporting in ");
		Location loc = onlineTarget.getLocation().getBlock().getLocation();

		new BukkitRunnable() {
			int count = (int) TpaManager.TELEPORT_TIME.getSeconds();

			@Override
			public void run() {
				if (count < 0) {
					onlineTarget.teleport(p);
					onlineTarget
							.sendMessage(UniverseAPI.getPrefix() + "You teleported yourself to " + ChatColor.YELLOW + p.getName());
					TpaManager.currentTpa.remove(p.getUniqueId());
					this.cancel();
					return;
				}
				Location current = onlineTarget.getLocation().getBlock().getLocation();
				if (!loc.equals(current)) {
					onlineTarget.sendMessage(UniverseAPI.getWarning() + "You moved and canceled your teleport request!");
					TpaManager.currentTpa.remove(p.getUniqueId());
					this.cancel();
					return;
				}
				if (count != 0) {
					onlineTarget.sendMessage(String.format(TP_MESSAGE, count, count == 1 ? "second" : "seconds"));
				}
				count--;

			}
		}.runTaskTimer(Core.getInstance(), 0L, 1 * 20);

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return Lists.newArrayList();
	}

}
