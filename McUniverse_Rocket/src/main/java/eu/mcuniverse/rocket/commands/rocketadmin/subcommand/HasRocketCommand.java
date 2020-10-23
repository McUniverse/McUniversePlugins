package eu.mcuniverse.rocket.commands.rocketadmin.subcommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import eu.mcuniverse.rocket.data.Messages;
import eu.mcuniverse.rocket.sql.MySQL;
import eu.mcuniverse.universeapi.command.SubCommand;
import net.md_5.bungee.api.ChatColor;

public class HasRocketCommand extends SubCommand {

	@Override
	public String getName() {
		return "hasrocket";
	}

	@Override
	public String getDescription() {
		return "Test if the player has a rocket";
	}

	@Override
	public String getSyntax() {
		return "/rocketadmin hasrocket <player>";
	}

	@Override
	public void perform(Player player, String[] args) {
		String name = args[1];
		Player target = Bukkit.getPlayer(name);
		if (target == null) {
			player.sendMessage(Messages.WARNING + "This player is not online!");
			return;
		}
		boolean b = MySQL.hasRocket(Bukkit.getPlayer(name));
		player.sendMessage(Messages.PREFIX + "The player " + ChatColor.GOLD + name + ChatColor.GRAY + " has "
				+ (b ? "a" : "no") + " rocket!");
	}

}
