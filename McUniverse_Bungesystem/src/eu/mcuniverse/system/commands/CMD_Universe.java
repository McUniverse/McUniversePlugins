package eu.mcuniverse.system.commands;


import eu.mcuniverse.system.Bungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Universe extends Command{

	public CMD_Universe() {
		super("CMD_Universe", "", new String[] {"universe"});
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer){
			ProxiedPlayer p = (ProxiedPlayer) sender;

			ServerInfo universe = ProxyServer.getInstance().getServerInfo("Universe");
			
			if(p.getServer().getInfo().getName().equalsIgnoreCase(universe.getName())) {
			    p.sendMessage(new TextComponent(Bungee.prefix + "§cYou are already connected to the Univsere-Server!"));
			    
			    return;
			}
			p.connect(universe);
			
			
		} else {
			sender.sendMessage(new TextComponent("No Player"));
		
		}
		
	}
}
