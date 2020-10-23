package eu.mcuniverse.bungeeuniverse.commands;

import eu.mcuniverse.bungeeuniverse.data.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Universe extends Command {

	public Universe() {
		super("universe");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!(sender instanceof ProxiedPlayer)) {
			sender.sendMessage(TextComponent.fromLegacyText("No Player"));
			return;
		}
		ProxiedPlayer p = (ProxiedPlayer) sender;

		ServerInfo universe = ProxyServer.getInstance().getServerInfo("Universe");

		if (p.getServer().getInfo().getName().equalsIgnoreCase(universe.getName())) {
			p.sendMessage(new TextComponent(Messages.WARNING + "You are already connected to the Univsere-Server!"));

			return;
		}
		p.connect(universe);

	}

}
