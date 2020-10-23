package eu.mcuniverse.rocket.commands.rocketadmin.subcommand;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import eu.mcuniverse.rocket.data.Messages;
import eu.mcuniverse.rocket.oil.RefineryManager;
import eu.mcuniverse.rocket.oil.RefineryStorageManager;
import eu.mcuniverse.universeapi.command.SubCommand;
import net.md_5.bungee.api.ChatColor;

public class DeleteRefineryCommand extends SubCommand {

	@Override
	public String getName() {
		return "deleterefinery";
	}

	@Override
	public String getDescription() {
		return "Force delete the refinery of a player";
	}

	@Override
	public String getSyntax() {
		return "/rocketadmin deleterefinery <player>";
	}

	@SuppressWarnings("deprecation")
	@Override
	public void perform(Player player, String[] args) {
		String name = args[1];
		OfflinePlayer target = Bukkit.getOfflinePlayer(name);
		if (target == null) {
			player.sendMessage(Messages.WARNING + "This player is not online!");
			return;
		}
		
		if (!RefineryStorageManager.hasRefinery(target.getUniqueId())) {
			player.sendMessage(Messages.WARNING + "This player has no refinery!");
			return;
		}
		
		RefineryManager.destroyRefinery(target);
		player.sendMessage(Messages.PREFIX + "The refinery of " + ChatColor.GOLD + name + ChatColor.GRAY +  " was removed");
	}
	
}
