package eu.mcuniverse.system.commands;


import eu.mcuniverse.system.Bungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Lobby extends Command{

	public CMD_Lobby() {
		super("CMD_Lobby", "", new String[] {"lobby", "hub", "l", "orbit"});
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer){
			ProxiedPlayer p = (ProxiedPlayer) sender;

			ServerInfo lobby = ProxyServer.getInstance().getServerInfo("Lobby");
			
			if(p.getServer().getInfo().getName().equalsIgnoreCase(lobby.getName())) {
			    p.sendMessage(new TextComponent(Bungee.prefix + "§cYou are already connected to the Lobby!"));
			    
			    return;
			}
			p.connect(lobby);
			
			
		} else {
			sender.sendMessage(new TextComponent("No Player"));
		
		}
		
	}
}
