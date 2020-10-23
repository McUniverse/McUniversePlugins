package eu.mcuniverse.weapons.commands.weaponadmin;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import eu.mcuniverse.weapons.commands.Subcommand;
import eu.mcuniverse.weapons.commands.weaponadmin.subcommand.GetItemCommand;
import eu.mcuniverse.weapons.commands.weaponadmin.subcommand.GetStatsCommand;
import eu.mcuniverse.weapons.commands.weaponadmin.subcommand.SendResourcepackCommand;
import eu.mcuniverse.weapons.data.Messages;
import eu.mcuniverse.weapons.weapon.util.WeaponType;
import lombok.Getter;

public class WeaponAdminCommandManager implements TabExecutor {

	@Getter
	private List<Subcommand> subcommands = Lists.newArrayList();
	
	public WeaponAdminCommandManager() { 
		subcommands.add(new GetItemCommand());
		subcommands.add(new GetStatsCommand());
		subcommands.add(new SendResourcepackCommand());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Messages.NO_PLAYER);
			return true;
		}
		
		if (!sender.hasPermission("mcuniverse.weapon.admin")) {
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
					}
				}
			});
		} else {
			getSubcommands().forEach(cmd -> {
				p.sendMessage(Messages.PREFIX + cmd.getSyntax() + " - " + cmd.getDescription());
			});
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
			if (args[0].equalsIgnoreCase("getstats")) {
				List<String> tab = Lists.newArrayList();
				for (WeaponType type : WeaponType.values()) {
					tab.add(type.name());
				}
				return tab;
			}
		}
		return null;
	}


}
