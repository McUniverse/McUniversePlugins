package eu.mcuniverse.essentials.command.tpa;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
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

public class TpaCommand implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(UniverseAPI.getWarning() + "No Player");
			return true;
		}

		Player p = (Player) sender;
		if (args.length != 1) {
			p.sendMessage(UniverseAPI.getWarning() + "Usage: /tpa <Player>");
			return true;
		}

		if (!p.hasPermission("mcuniverse.tpa.bypasscooldown")) {
			if (TpaManager.tpaCooldown.getOrDefault(p.getUniqueId(), Instant.now()).isAfter(Instant.now())) {
				p.sendMessage(UniverseAPI.getWarning() + "You must wait " + ChatColor.YELLOW
						+ TpaManager.COOLDOWN_TIME.getSeconds() + ChatColor.RED + " seconds in between teleport requests!");
				return true;
			}
		}

		String targetName = args[0];
		Player target = Bukkit.getPlayer(targetName);
		if (target == null) {
			p.sendMessage(UniverseAPI.getWarning() + "You can only send teleport requests to online players!");
			return true;
		}
		if (target.equals(p)) {
			p.sendMessage(UniverseAPI.getWarning() + "You can't send a teleport to yourself!");
			return true;
		}
		if (!p.getWorld().equals(target.getWorld())) {
			p.sendMessage(UniverseAPI.getWarning() + "This player is on another planet!");
			return true;
		}

		TpaManager.sendTpaRequest(p, target);

		if (!p.hasPermission("mcuniverse.tpa.bypasscooldown")) {
			TpaManager.tpaCooldown.put(p.getUniqueId(), Instant.now().plus(TpaManager.COOLDOWN_TIME));
		}
		
		new BukkitRunnable() {

			@Override
			public void run() {
				TpaManager.killTpaRequest(target);
			}
		}.runTaskLater(Core.getInstance(), TpaManager.KEEP_ALIVE.getSeconds() * 20);

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 0) 
			return Lists.newArrayList();
		return Bukkit.getOnlinePlayers().stream().map(p -> p.getName()).filter(name -> name.startsWith(args[0])).collect(Collectors.toList());
	}

}
