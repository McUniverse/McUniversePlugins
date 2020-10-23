package eu.mcuniverse.rocket.commands.rocketadmin.subcommand;

import org.bukkit.entity.Player;

import eu.mcuniverse.rocket.data.Messages;
import eu.mcuniverse.rocket.items.Items;
import eu.mcuniverse.universeapi.command.SubCommand;

public class GetItemCommand extends SubCommand {

	@Override
	public String getName() {
		return "getitem";
	}

	@Override
	public String getDescription() {
		return "Get the rocket base item";
	}

	@Override
	public String getSyntax() {
		return "/rocketadmin getitem";
	}

	@Override
	public void perform(Player player, String[] args) {
		player.getInventory().addItem(Items.getBaseRocketItem());
		player.sendMessage(Messages.PREFIX + "You got the rocket base item!");
	}

}
