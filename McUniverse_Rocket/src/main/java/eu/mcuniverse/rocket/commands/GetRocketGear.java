package eu.mcuniverse.rocket.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import eu.mcuniverse.universeapi.item.CustomItem;

public class GetRocketGear implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			((Player) sender).getInventory().addItem(CustomItem.ghastt);
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return null;
	}

}
