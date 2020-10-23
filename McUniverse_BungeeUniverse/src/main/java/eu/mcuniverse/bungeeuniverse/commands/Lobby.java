package eu.mcuniverse.bungeeuniverse.commands;

import eu.mcuniverse.bungeeuniverse.data.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Lobby extends Command {

	public Lobby() {
		super("lobby", "", new String[] {"l", "hub", "orbit"});
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!(sender instanceof ProxiedPlayer)) {
			sender.sendMessage(TextComponent.fromLegacyText("No Player"));
			return;
		}
		
		ProxiedPlayer p = (ProxiedPlayer) sender;
		
		ServerInfo lobby = ProxyServer.getInstance().getServerInfo("Lobby");
		
		if (p.getServer().getInfo().getName().equalsIgnoreCase(lobby.getName())) {
			p.sendMessage(TextComponent.fromLegacyText(Messages.WARNING + "You are already connected to the Lobby!"));
			return;
		}
		p.connect(lobby);
		
	}
	
}
