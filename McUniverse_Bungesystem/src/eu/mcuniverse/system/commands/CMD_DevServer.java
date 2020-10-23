package eu.mcuniverse.system.commands;


import eu.mcuniverse.system.Bungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_DevServer extends Command{

	public CMD_DevServer() {
		super("CMD_DevServer", "mcuniverse.devserver", new String[] {"dev"});
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer){
			ProxiedPlayer p = (ProxiedPlayer) sender;

			ServerInfo dev = ProxyServer.getInstance().getServerInfo("Dev");
			
			if(p.getServer().getInfo().getName().equalsIgnoreCase(dev.getName())) {
			    p.sendMessage(new TextComponent(Bungee.prefix + "§cYou are already connected to the DevServer!"));
			    
			    return;
			}
			p.connect(dev);
			
			
		} else {
			sender.sendMessage(new TextComponent("No Player"));
		
		}
		
	}
}
