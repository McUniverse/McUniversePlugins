package eu.mcuniverse.bungeeuniverse.commands;

import eu.mcuniverse.bungeeuniverse.data.Data;
import eu.mcuniverse.bungeeuniverse.data.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class Maintenance extends Command {

	public Maintenance() {
		super("maintenance", "mcuniverse.maintenance", new String[] {"wartung", "wartungen", "maintenance"});
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if (args.length == 0) {
			if (!Data.maintenance) {
				Data.maintenance = true;
				ProxyServer.getInstance().getPlayers().stream()
					.filter(players -> !players.hasPermission("mcuniverse.maintenance"))
					.forEach(players -> players.disconnect(TextComponent.fromLegacyText(Messages.MAINTENANCE_KICK)));
				
				ProxyServer.getInstance().getPlayers().stream()
					.filter(players -> players.hasPermission("mcuniverse.maintenance"))
					.forEach(players -> players.sendMessage(TextComponent.fromLegacyText(Messages.WARNING + "Maintenance is now enabled!")));
			} else {
				Data.maintenance = false;
				ProxyServer.getInstance().getPlayers().stream()
				.filter(players -> players.hasPermission("mcuniverse.maintenance"))
				.forEach(players -> players.sendMessage(TextComponent.fromLegacyText(Messages.WARNING + "Maintenance is now disabled!")));
			}
		} else {
			sender.sendMessage(TextComponent.fromLegacyText(Messages.WARNING + "Maintenance bypass switched to LuckPermsBungee! Please use /lpb user <user> permission set mcuniverse.team true|false"));
		}
	}
	
}
