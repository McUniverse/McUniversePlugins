package eu.mcuniverse.rocket.commands.rocketadmin.subcommand;

import org.bukkit.entity.Player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eu.mcuniverse.rocket.data.Messages;
import eu.mcuniverse.rocket.rocket.Rocket;
import eu.mcuniverse.rocket.rocket.RocketManager;
import eu.mcuniverse.rocket.sql.MySQL;
import eu.mcuniverse.universeapi.command.SubCommand;
import net.md_5.bungee.api.ChatColor;

public class GetRocketCommand extends SubCommand {

	@Override
	public String getName() {
		return "getrocket";
	}

	@Override
	public String getDescription() {
		return "Get the rocket of a player";
	}

	@Override
	public String getSyntax() {
		return "/rocketadmin getrocket <Player>";
	}

	@Override
	public void perform(Player player, String[] args) {
		String name = args[1];
		if (!RocketManager.hasRocket(name)) {
			player
					.sendMessage(Messages.WARNING + "The player " + ChatColor.YELLOW + name + ChatColor.RED + " has no rocket!");
			return;
		}
		Rocket r = new Gson().fromJson(MySQL.getRocket(name), Rocket.class);
		
		String rocket = new GsonBuilder().setPrettyPrinting().create().toJson(r);
		
		rocket = rocket.replace("{", ChatColor.GRAY + "{" + ChatColor.RESET);
		rocket = rocket.replace("}", ChatColor.GRAY + "}" + ChatColor.RESET);
		rocket = rocket.replace("\"", ChatColor.RED + "\"" + ChatColor.RESET);
		rocket = rocket.replace(":", ChatColor.YELLOW + ":" + ChatColor.RESET);
		rocket = rocket.replace(",", ChatColor.BLUE + "," + ChatColor.RESET);
		player.sendMessage(Messages.PREFIX + "The rocket of " + ChatColor.YELLOW + name);
		player.sendMessage(rocket);
	}

}
