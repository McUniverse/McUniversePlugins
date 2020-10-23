package eu.mcuniverse.rocket.commands.rocketadmin.subcommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import eu.mcuniverse.rocket.data.Messages;
import eu.mcuniverse.rocket.rocket.RocketManager;
import eu.mcuniverse.universeapi.command.SubCommand;
import net.md_5.bungee.api.ChatColor;

public class DeleteCommand extends SubCommand {

	@Override
	public String getName() {
		return "delete";
	}

	@Override
	public String getDescription() {
		return "Force delete the rocket of a player";
	}

	@Override
	public String getSyntax() {
		return "/rocketadmin delete <player>";
	}

	@Override
	public void perform(Player player, String[] args) {
		String name = args[1];
		Player target = Bukkit.getPlayer(name);
		if (target == null) {
			player.sendMessage(Messages.WARNING + "This player is not online!");
			return;
		}
		
		if (!RocketManager.hasRocket(name)) {
			player.sendMessage(Messages.WARNING + "This player has no rocket!");
			return;
		}
		
		RocketManager.deleteRocket(name);
		player.sendMessage(Messages.PREFIX + "The rocket of " + ChatColor.GOLD + name + ChatColor.GRAY +  " was removed");
	}
	
}
