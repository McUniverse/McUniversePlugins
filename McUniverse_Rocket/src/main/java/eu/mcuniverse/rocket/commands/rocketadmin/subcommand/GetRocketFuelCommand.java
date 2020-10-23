package eu.mcuniverse.rocket.commands.rocketadmin.subcommand;

import org.bukkit.entity.Player;

import eu.mcuniverse.universeapi.api.UniverseAPI;
import eu.mcuniverse.universeapi.command.SubCommand;
import eu.mcuniverse.universeapi.item.CustomItem;

public class GetRocketFuelCommand extends SubCommand {

	@Override
	public String getName() {
		return "getrocketfuel";
	}

	@Override
	public String getDescription() {
		return "Get rocket fuel";
	}

	@Override
	public String getSyntax() {
		return "/rocketadmin getrocketfuel [amount]";
	}

	@Override
	public void perform(Player player, String[] args) {
		if (!player.hasPermission("rocket.admin.rocketfuel")) {
			player.sendMessage(UniverseAPI.getNoPermissions());
			return;
		}
		if (args.length == 1) {
			player.getInventory().addItem(CustomItem.Rocket.getRocketFuel());
			player.sendMessage(UniverseAPI.getPrefix() + "You got rocketfuel!");
		} else if (args.length == 2) {
			try {
				int count = Integer.parseInt(args[1]);
				for (int i = 0; i < count; i++) {
					player.getInventory().addItem(CustomItem.Rocket.getRocketFuel());
				}
				player.sendMessage(UniverseAPI.getPrefix() + "You got rocketfuel!");
			} catch (NumberFormatException e) {
				player.sendMessage(UniverseAPI.getWarning() + "Please enter a valid number");
			}
		}
	}

}
