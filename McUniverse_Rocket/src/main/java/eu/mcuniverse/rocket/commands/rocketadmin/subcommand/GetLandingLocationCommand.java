package eu.mcuniverse.rocket.commands.rocketadmin.subcommand;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import eu.mcuniverse.universeapi.api.UniverseAPI;
import eu.mcuniverse.universeapi.command.SubCommand;
import eu.mcuniverse.universeapi.rockets.Planet;
import net.md_5.bungee.api.ChatColor;

public class GetLandingLocationCommand extends SubCommand {

	@Override
	public String getName() {
		return "getlandinglocation";
	}

	@Override
	public String getDescription() {
		return "Get the rocket landing location of a faction";
	}

	@Override
	public String getSyntax() {
		return "/rocketadmin getrocket <FactionTag> <Planet>";
	}

	@Override
	public void perform(Player player, String[] args) {
		if (args.length != 3) {
			player.sendMessage(UniverseAPI.getWarning() + getSyntax());
			return;
		}

		String factionTag = args[1];
		Planet planet;
		try {
			planet = Planet.valueOf(args[2].toUpperCase());
		} catch (IllegalArgumentException e) {
			player.sendMessage(UniverseAPI.getPrefix() + "This planet does not exist!");
			return;
		}

		if (!UniverseAPI.getFactionManager().hasLandingLocation(factionTag, planet)) {
			player.sendMessage(UniverseAPI.getWarning() + "The faction " + ChatColor.YELLOW + factionTag + ChatColor.RED
					+ " has no faction on the planet " + ChatColor.YELLOW + planet.getWorldName());
			return;
		}

		Location l = UniverseAPI.getFactionManager().getLandingLocation(factionTag, planet);
		String prettyLocation = ChatColor.YELLOW + "Planet: " + ChatColor.AQUA + l.getWorld().getName() + ChatColor.YELLOW
				+ ", " + ChatColor.YELLOW + "X: " + ChatColor.AQUA + l.getBlockX() + ChatColor.YELLOW + ", " + ChatColor.YELLOW
				+ "Y: " + ChatColor.AQUA + l.getBlockY() + ChatColor.YELLOW + ", " + ChatColor.YELLOW + "Z: " + ChatColor.AQUA
				+ l.getBlockZ();
		player.sendMessage(UniverseAPI.getPrefix() + "The faction " + ChatColor.YELLOW + factionTag + ChatColor.GRAY
				+ " has the following landing location on planet " + ChatColor.YELLOW + planet.getWorldName());
		player.sendMessage(prettyLocation);
	}

}
