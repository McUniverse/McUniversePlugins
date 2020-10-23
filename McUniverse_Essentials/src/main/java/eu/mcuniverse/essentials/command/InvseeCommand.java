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

public class InvseeCommand implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("No Player!");
			return true;
		}
		
		if (args.length != 1) {
			sender.sendMessage(UniverseAPI.getWarning() + "/invsee <Player>");
			return true;
		}
		
		Player p = (Player) sender;
		if (!p.hasPermission("mcuniverse.invsee")) {
			p.sendMessage(UniverseAPI.getNoPermissions());
			return true;
		}
		
		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			p.sendMessage(UniverseAPI.getWarning() + "The player " + ChatColor.YELLOW + args[0] + ChatColor.RED + " is not online!");
			return true;
		} 
		
		p.closeInventory();
		p.openInventory(target.getInventory());
		p.updateInventory();
		
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
