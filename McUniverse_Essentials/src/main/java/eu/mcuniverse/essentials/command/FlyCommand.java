package eu.mcuniverse.essentials.command;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import eu.mcuniverse.universeapi.api.UniverseAPI;
import net.md_5.bungee.api.ChatColor;

public class FlyCommand implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("No Player");
			return true;
		}
		
		Player p = (Player) sender;
		
		if (!p.hasPermission("mcuniverse.fly")) {
			p.sendMessage(UniverseAPI.getNoPermissions());
			return true;
		}
		
		if (args.length == 0) {
			if (p.getAllowFlight()) {
				p.setAllowFlight(false);
				p.setFlying(false);
				p.sendMessage(UniverseAPI.getPrefix() + "The ability to fly was removed");
			} else {
				p.setAllowFlight(true);
				p.sendMessage(UniverseAPI.getPrefix() + "You have the ability to fly now");
			}
		} else if (args.length == 1) {
			if (!p.hasPermission("mcuniverse.fly.other")) {
				p.sendMessage(UniverseAPI.getNoPermissions());
				return true;
			}
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				p.sendMessage(UniverseAPI.getWarning() + "The player " + ChatColor.YELLOW + args[0] + ChatColor.RED + " is not online");
				return true;
			}
			if (target.getAllowFlight()) {
				p.sendMessage(UniverseAPI.getPrefix() + "You revoked the ability to fly from " + ChatColor.YELLOW + args[0]);
				p.sendMessage(UniverseAPI.getPrefix() + "The ability to fly was removed");
				target.setAllowFlight(false);
				target.setFlying(false);
			} else { 
				p.sendMessage(UniverseAPI.getPrefix() + "You granted the ability to fly to " + ChatColor.YELLOW + args[0]);
				p.sendMessage(UniverseAPI.getPrefix() + "You have the ability to fly now");
				target.setAllowFlight(true);
			}
		} else {
			p.sendMessage(UniverseAPI.getWarning() + "/" + label + " [player]");
		}
		
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 1) {
			return Bukkit.getOnlinePlayers().stream().map(p -> p.getName()).collect(Collectors.toCollection(ArrayList::new));
		}
		return new ArrayList<String>();
	}



}
