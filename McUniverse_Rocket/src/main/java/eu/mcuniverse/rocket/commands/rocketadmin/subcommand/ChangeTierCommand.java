package eu.mcuniverse.rocket.commands.rocketadmin.subcommand;

import org.bukkit.entity.Player;

import eu.mcuniverse.rocket.data.Messages;
import eu.mcuniverse.rocket.rocket.Rocket;
import eu.mcuniverse.rocket.rocket.RocketManager;
import eu.mcuniverse.universeapi.command.SubCommand;
import net.md_5.bungee.api.ChatColor;

public class ChangeTierCommand extends SubCommand {

	@Override
	public String getName() {
		return "changetier";
	}

	@Override
	public String getDescription() {
		return "Change the tier of a rocket";
	}

	@Override
	public String getSyntax() {
		return "/rocketadmin changetier [Player] [tier]";
	}

	@Override
	public void perform(Player player, String[] args) {
		String target = args[1];
		if (!RocketManager.hasRocket(target)) {
			player.sendMessage(Messages.WARNING + "This player has no rocket!");
			return;
		}
		try {
			int newTier = Integer.parseInt(args[2]);
			if (newTier > 6 || newTier < 1) {
				player.sendMessage(Messages.WARNING + "The tier has to be between 1 and 6");
				return;
			}
			Rocket r = RocketManager.getRocket(target);
			r.setTier(newTier);
			r.saveRocket();
			player.sendMessage(Messages.PREFIX + "Successfully changed the rocket tier of " + ChatColor.YELLOW + target
					+ Messages.TEXT_COLOR + " to " + ChatColor.YELLOW + newTier);
		} catch (NumberFormatException e) {
			player.sendMessage(Messages.WARNING + "This is not a valid rocket tier: " + ChatColor.YELLOW + args[2]);
		}
	}

}
