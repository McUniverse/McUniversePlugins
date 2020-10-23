package eu.mcuniverse.system.commands;

import eu.mcuniverse.system.Bungee;
import eu.mcuniverse.system.mysql.MySQL;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Ban extends Command {
	public CMD_Ban() {
		super("ban/");
	}

	@SuppressWarnings("deprecation")
	public void execute(CommandSender sender, final String[] args) {
		if (!(sender instanceof ProxiedPlayer)) {
			sender.sendMessage(Bungee.prefix + "§cNo Player.");
			return;
		}
		final ProxiedPlayer p = (ProxiedPlayer) sender;

		ProxyServer.getInstance().getScheduler().runAsync(Bungee.plugin, new Runnable() {
			public void run() {
				if ((p.hasPermission("mcuniverse.team"))) {
					if (args.length == 0) {
						p.sendMessage(Bungee.prefix + "§c/ban <Name> <Reason>");
						return;
					}
					if (args.length == 1) {
						p.sendMessage(Bungee.prefix + "§cPlease enter a reason");
						return;
					}
					if (args.length > 1) {
						ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
						String reason = "";
						for (int i = 1; i < args.length; i++) {
							reason = reason + " " + args[i];
						}
						if (target == p) {
							p.sendMessage(Bungee.prefix + "§cYou can`t ban yourself.");
							return;
						}
						if (target == null) {
							if (MySQL.isUserExisting(args[0])) {
								p.sendMessage(Bungee.prefix + "§cThe Player §e" + MySQL.getPlayer(args[0])
										+ " §cis already banned.");
								return;
							}
							if (MySQL.getName(args[0]) != null) {
								p.sendMessage(Bungee.prefix + "§cThe Player §e" + MySQL.getName(args[0])
										+ " §cgot banned.");
								MySQL.registerPlayer(MySQL.getName(args[0]));
								MySQL.setBanned(MySQL.getName(args[0]), reason, p, -1L);
								for (ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
									if ((players.hasPermission("mcuniverse.team"))) {
										players.sendMessage(
												"§8┃§cBan §8┃ §8§m---------------------§r§8┃ §cBanned §8§m┃---------------------");
										players.sendMessage("§8┃§cBan §8┃ §7Player §8» §e" + MySQL.getName(args[0]));
										players.sendMessage("§8┃§cBan §8┃ §7Reason §8»§e" + reason);
										players.sendMessage("§8┃§cBan §8┃ §7Banned by §8» §e" + p.getName());
										players.sendMessage("§8┃§cBan §8┃ §7Banned time §8» §4PERMANENT");
										players.sendMessage(
												"§8┃§cBan §8┃ §8§m---------------------§r§8┃ §cBanned §8§m┃---------------------");
									}
								}
								return;
							}
							p.sendMessage(Bungee.prefix + "§cThe Player §e" + args[0] + " §cdoes not exist.");
							return;
						}
						if ((!target.hasPermission("mcuniverse.team"))) {
							if ((!target.hasPermission("mcuniverse.*")
									| p.hasPermission("mcuniverse.*"))) {
								target.disconnect("§cYou got §4PERMANENT §cbanned from the Network. \n §cReason §8» §e"
										+ reason + "\n §cBanned by §8» §e" + p.getName()
										+ "\n §aYou can §a create a Ticket in our §9Discord Server &ato get unbanned.");
								MySQL.registerPlayer(target);
								MySQL.setBanned(target, reason, p, -1L);
								for (ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
									if ((players.hasPermission("mcuniverse.*")
											| players.hasPermission("mcuniverse.team"))) {
										players.sendMessage(
												"§8┃ §cBan §8┃ §8§m-----------------§r§8┃ §cBanned §8§m┃-----------------");
										players.sendMessage("§8┃ §cBan §8┃ §7Player §8» §e" + target.getName());
										players.sendMessage("§8┃ §cBan §8┃ §7Reason §8»§e" + reason);
										players.sendMessage("§8┃ §cBan §8┃ §7Banned by §8» §e" + p.getName());
										players.sendMessage("§8┃ §cBan §8┃ §7Banned time §8» §4PERMANENT");
										players.sendMessage(
												"§8┃ §cBan §8┃ §8§m------------------§r§8┃ §cBanned §8§m┃-----------------");
									}
								}
								return;
							}
						}
						p.sendMessage(Bungee.prefix + "§cYou may not ban any §eteam member");
					}
				} else {
					p.sendMessage(Bungee.perms);
					return;
				}
			}
		});
	}
}