package eu.mcuniverse.rocket.commands.rocketadmin;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import eu.mcuniverse.rocket.commands.rocketadmin.subcommand.ChangeParamsCommand;
import eu.mcuniverse.rocket.commands.rocketadmin.subcommand.ChangeTierCommand;
import eu.mcuniverse.rocket.commands.rocketadmin.subcommand.CustomItemCommand;
import eu.mcuniverse.rocket.commands.rocketadmin.subcommand.DamageCommand;
import eu.mcuniverse.rocket.commands.rocketadmin.subcommand.DeleteCommand;
import eu.mcuniverse.rocket.commands.rocketadmin.subcommand.DeleteRefineryCommand;
import eu.mcuniverse.rocket.commands.rocketadmin.subcommand.GetItemCommand;
import eu.mcuniverse.rocket.commands.rocketadmin.subcommand.GetLandingLocationCommand;
import eu.mcuniverse.rocket.commands.rocketadmin.subcommand.GetRocketCommand;
import eu.mcuniverse.rocket.commands.rocketadmin.subcommand.GetRocketFuelCommand;
import eu.mcuniverse.rocket.commands.rocketadmin.subcommand.HasRocketCommand;
import eu.mcuniverse.rocket.commands.rocketadmin.subcommand.SQLCommand;
import eu.mcuniverse.rocket.commands.rocketadmin.subcommand.TeleportCommand;
import eu.mcuniverse.rocket.data.Messages;
import eu.mcuniverse.universeapi.api.UniverseAPI;
import eu.mcuniverse.universeapi.command.SubCommand;
import eu.mcuniverse.universeapi.rockets.Planet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Getter;

public class RocketAdminCommandManager implements TabExecutor {

	@Getter
	private ObjectArrayList<SubCommand> subcommands = new ObjectArrayList<>();

	public RocketAdminCommandManager() {
		subcommands.add(new HasRocketCommand());
		subcommands.add(new DeleteCommand());
		subcommands.add(new DeleteRefineryCommand());
		subcommands.add(new GetRocketCommand());
		subcommands.add(new GetRocketFuelCommand());
		subcommands.add(new GetItemCommand());
		subcommands.add(new TeleportCommand());
		subcommands.add(new SQLCommand());
		subcommands.add(new DamageCommand());
		subcommands.add(new ChangeTierCommand());
		subcommands.add(new ChangeParamsCommand());
		subcommands.add(new CustomItemCommand());
		subcommands.add(new GetLandingLocationCommand());
		
		subcommands = subcommands.stream()
				.sorted()
				.collect(Collectors.toCollection(ObjectArrayList::new));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Messages.NO_PLAYER);
			return true;
		}

		if (!sender.hasPermission("mcuniverse.rocket.admin")) {
			sender.sendMessage(Messages.NO_PERMS);
			return true;
		}

		Player p = (Player) sender;

		if (args.length > 0) {
			getSubcommands().forEach(cmd -> {
				if (args[0].equalsIgnoreCase(cmd.getName())) {
					try {
						cmd.perform(p, args);
					} catch (Exception e) {
						e.printStackTrace();
						p.sendMessage(Messages.ERROR + e.getLocalizedMessage());
//						p.sendMessage(UniverseAPI.getWarning() + "Unknown subcommand: " + ChatColor.YELLOW + args[0]);
					}
				}
			});
		} else {
			for (SubCommand cmd : getSubcommands()) {
				p.sendMessage(Messages.PREFIX + cmd.getSyntax() + " - " + cmd.getDescription());
			}
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 1) {
			List<String> tab = Lists.newArrayList();
			getSubcommands().forEach(cmd -> {
				if (cmd.getName().startsWith(args[0]))
					tab.add(cmd.getName());
			});
			return tab;
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("getlandinglocation")) {
				return UniverseAPI.getFactionUtil().getAllFactionTags().stream()
						.filter(tag -> tag.toLowerCase().startsWith(args[1].toLowerCase()))
						.collect(Collectors.toList());
			}
		} else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("getlandinglocation")) {
				return Arrays.asList(Planet.values()).stream()
						.map(planet -> planet.getWorldName())
						.filter(planet -> planet.toLowerCase().startsWith(args[2].toLowerCase()))
						.collect(Collectors.toList());
			}
		}
		return null;
	}

}
