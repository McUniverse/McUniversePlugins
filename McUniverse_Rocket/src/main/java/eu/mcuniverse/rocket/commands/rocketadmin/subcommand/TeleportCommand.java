package eu.mcuniverse.rocket.commands.rocketadmin.subcommand;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import eu.mcuniverse.rocket.data.Messages;
import eu.mcuniverse.rocket.rocket.Rocket;
import eu.mcuniverse.rocket.rocket.RocketManager;
import eu.mcuniverse.universeapi.command.SubCommand;
import net.md_5.bungee.api.ChatColor;

public class TeleportCommand extends SubCommand {

	@Override
	public String getName() {
		return "tp";
	}

	@Override
	public String getDescription() {
		return "Teleport to the rocket";
	}

	@Override
	public String getSyntax() {
		return "/rocketadmin tp <Player>";
	}

	@Override
	public void perform(Player player, String[] args) {
		String name = args[1];
		if (!RocketManager.hasRocket(name)) {
			player.sendMessage(Messages.WARNING + "This player doesn't have a rocket!");
			return;
		}
		Rocket r = RocketManager.getRocket(name);
		Location l = r.getLocation();
		if (player.teleport(l)) {
			player.sendMessage(Messages.PREFIX + "You teleported yourself to the rocket of " + ChatColor.YELLOW + name);
		} else {
			player.sendMessage(Messages.ERROR + "An error occured during teleportation to the rocket of the player "
					+ ChatColor.YELLOW + name);
		}
	}

}
