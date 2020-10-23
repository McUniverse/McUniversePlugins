package eu.mcuniverse.essentials.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import eu.mcuniverse.essentials.Core;
import eu.mcuniverse.essentials.data.Data;
import eu.mcuniverse.universeapi.api.UniverseAPI;

public class VanishCommand implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("No player!");
			return true;
		}
		
		Player p = (Player) sender;
		if (!p.hasPermission("mcuniverse.vanish")) {
			p.sendMessage(UniverseAPI.getNoPermissions());
			return true;
		}
		
		if (Data.vanished.contains(p.getUniqueId())) {
			p.sendMessage(UniverseAPI.getPrefix() + "You're not vanished anymore");
			Bukkit.getOnlinePlayers().forEach(online -> online.showPlayer(Core.getInstance(), p));
			Data.vanished.remove(p.getUniqueId());
		} else {
			p.sendMessage(UniverseAPI.getPrefix() + "You're vanished now");
			Bukkit.getOnlinePlayers().forEach(online -> online.hidePlayer(Core.getInstance(), p));
			Data.vanished.add(p.getUniqueId());
		}
		
		return true;
	}

	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return new ArrayList<String>();
	}


}
