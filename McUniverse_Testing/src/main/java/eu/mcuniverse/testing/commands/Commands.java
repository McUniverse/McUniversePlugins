package eu.mcuniverse.testing.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public class Commands extends BukkitCommand {

	protected Commands(String name, String description, String usageMessage, List<String> aliases) {
		super(name, description, usageMessage, aliases);
	}

	@Override
	public boolean execute(CommandSender arg0, String arg1, String[] arg2) {
		return false;
	}

}
