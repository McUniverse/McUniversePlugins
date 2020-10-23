package eu.mcuniverse.system.commands;

import java.util.List;

import eu.mcuniverse.system.Bungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Wartung extends Command {
	public CMD_Wartung() {
		super("CMD_Wartung", "mcuniverse.wartung", new String[] {"wartung", "wartungen", "maintenance", "wartungsarbeiten"});
	}

	// /wartung [add|remove|list] <Player>

	@SuppressWarnings("deprecation")
	public void execute(CommandSender sender, String[] args) {
		if (!(sender instanceof ProxiedPlayer)) {
			sender.sendMessage(Bungee.prefix + "�cNo Player");
			return;
		}
		ProxiedPlayer p = (ProxiedPlayer) sender;
		
		if (args.length == 0) {
			if (!Bungee.maintenance) {
				Bungee.maintenance = true;
				for (ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
					if (players.hasPermission("mcuniverse.team") || Bungee.config.getStringList("Maintenance.Bypass").contains(p.getName())) {
//						players.disconnect(
//								ChatColor.RED + "You were kicked from the McUniverse Network! \n �cReason �8� �cMaintance \n \n �cDiscord  �8� �ehttps://discord.gg/tAumCdV");
					}
				}
				for (ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
					if (players.hasPermission("mcuniverse.team")) {
						players.sendMessage(Bungee.prefix + ChatColor.RED + "Wartungsarbeiten wurden aktiviert!");
					}
				}
				return;
			}
			Bungee.maintenance = false;
			for (ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
				if (players.hasPermission("mcuniverse.team")) {
					players.sendMessage(Bungee.prefix + ChatColor.RED + "Wartungsarbeiten wurden deaktiviert!");
				}
			}
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("list")) {
				if (!checkConfig()) {
					p.sendMessage(TextComponent.fromLegacyText(Bungee.prefix + ChatColor.RED + "No list set in config.yml"));
					return;
				}
				List<String> bypass = Bungee.config.getStringList("Maintenance.Bypass");
				String s = "";
				for (String user : bypass) {
					s += ChatColor.YELLOW + user + ChatColor.GRAY + ", ";
				}
				p.sendMessage(TextComponent.fromLegacyText(Bungee.prefix + "All players allowd to bypass maintenance mode:"));
				p.sendMessage(TextComponent.fromLegacyText(s.substring(0, s.length() - 2)));
			} else {
				p.sendMessage(TextComponent.fromLegacyText(ChatColor.RED + "Usage: /wartung [add|remove|list] <Player>"));
			}
		} else if (args.length == 2) {
			if (!checkConfig()) {
				p.sendMessage(TextComponent.fromLegacyText(Bungee.prefix + ChatColor.RED + "No list set in config.yml"));
				return;
			}
			String user = args[1];
			List<String> bypass = Bungee.config.getStringList("Maintenance.Bypass");
			if (args[0].equalsIgnoreCase("add")) {
				if (bypass.contains(user)) {
					p.sendMessage(TextComponent.fromLegacyText(Bungee.prefix + ChatColor.GRAY + "The user " + ChatColor.YELLOW
							+ user + ChatColor.GRAY + " is already allowed to bypass the maintenance mode!"));
					return;
				}
				bypass.add(user);
				p.sendMessage(TextComponent.fromLegacyText(Bungee.prefix + ChatColor.GRAY + "The user " + ChatColor.YELLOW
						+ ChatColor.GRAY + " is now allowed to bypass the maintenance mode!"));
			} else if (args[0].equalsIgnoreCase("remove")) {
				if (!bypass.contains(user)) {
					p.sendMessage(TextComponent.fromLegacyText(Bungee.prefix + ChatColor.GRAY + "The user " + ChatColor.YELLOW
							+ user + ChatColor.GRAY + " has no permission to bypass maintenance mode!"));
					return;
				}
				bypass.remove(user);
				p.sendMessage(TextComponent.fromLegacyText(Bungee.prefix + ChatColor.GRAY + "The user " + ChatColor.YELLOW + user
						+ ChatColor.GRAY + " is now deneyed to bypass the maintenance mode!"));
			}
			Bungee.config.set("Maintenance.Bypass", bypass);
			Bungee.saveConfig();
		} else {
			p.sendMessage(TextComponent.fromLegacyText(ChatColor.RED + "Usage: /wartung [add|remove|list] <Player>"));
		}
	}

	private boolean checkConfig() {
		return Bungee.config.contains("Maintenance.Bypass");
	}

}
