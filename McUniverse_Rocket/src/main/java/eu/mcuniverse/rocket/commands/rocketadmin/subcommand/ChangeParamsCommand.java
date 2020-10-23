package eu.mcuniverse.rocket.commands.rocketadmin.subcommand;

import org.bukkit.entity.Player;

import eu.mcuniverse.rocket.data.Messages;
import eu.mcuniverse.rocket.data.Variables;
import eu.mcuniverse.universeapi.command.SubCommand;

public class ChangeParamsCommand extends SubCommand {

	@Override
	public String getName() {
		return "changeparams";
	}

	@Override
	public String getDescription() {
		return "Change Teleporter Params";
	}

	@Override
	public String getSyntax() {
		return "/rocketadmin changeparams <period> <radius> <delta>";
	}

	@Override
	public void perform(Player player, String[] args) {
		try {
			long l;
			int r;
			double d;
			if (args[1].equalsIgnoreCase("~")) {
				l = Variables.PERIOD;
			} else {
				l = Long.valueOf(args[1]);
			}

			if (args[2].equalsIgnoreCase("~")) {
				r = Variables.RADIUS;
			} else {
				r = Integer.valueOf(args[2]);
			}

			if (args[3].equalsIgnoreCase("~")) {
				d = Variables.DELTA;
			} else {
				d = Double.valueOf(args[3]);
			}

			Variables.PERIOD = l;
			Variables.RADIUS = r;
			Variables.DELTA = d;
			player.sendMessage(Messages.DEBUG + "Successfully changed to " + l + ", " + r + ", " + d);
		} catch (NumberFormatException e) {
			player.sendMessage(Messages.WARNING + "Numbers could not be converted!");
		}
	}

}
