package eu.mcuniverse.essentials.command;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import eu.mcuniverse.essentials.data.Data;
import eu.mcuniverse.universeapi.api.UniverseAPI;
import net.md_5.bungee.api.ChatColor;

public class MuteCommand implements TabExecutor {

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("mcuniverse.mute")) {
			sender.sendMessage(UniverseAPI.getNoPermissions());
			return true;
		}
		
		if (args.length != 1) {
			sender.sendMessage(UniverseAPI.getWarning() + "/mute <Player>");
			return true;
		}
		
		OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
		
		if (Data.mutedPlayers.contains(target.getUniqueId())) {
			sender.sendMessage(UniverseAPI.getPrefix() + "You unmuted " + ChatColor.GOLD + args[0]);
			Data.mutedPlayers.remove(target.getUniqueId());
		} else {
			sender.sendMessage(UniverseAPI.getPrefix() + "You muted " + ChatColor.GOLD + args[0]);
			Data.mutedPlayers.add(target.getUniqueId());
		}
		
		sender.sendMessage(UniverseAPI.getPrefix() + ChatColor.ITALIC + "Please note that this mute disappreas after a reload! Further improvement needed");
		return true;
	}
	
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 1) {
			return Bukkit.getOnlinePlayers().stream().map(p -> p.getName()).collect(Collectors.toCollection(ArrayList::new));
		}
		return new ArrayList<String>();
	}
	
}
