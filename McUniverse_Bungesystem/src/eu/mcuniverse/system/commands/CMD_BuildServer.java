package eu.mcuniverse.system.commands;


import eu.mcuniverse.system.Bungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_BuildServer extends Command{

	public CMD_BuildServer() {
		super("CMD_BuildServer", "", new String[] {"build"});
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer){
			ProxiedPlayer p = (ProxiedPlayer) sender;

			ServerInfo build = ProxyServer.getInstance().getServerInfo("Build");
			
			if(p.getServer().getInfo().getName().equalsIgnoreCase(build.getName())) {
			    p.sendMessage(new TextComponent(Bungee.prefix + "§cYou are already connected to the BuildServer!"));
			    
			    return;
			}
			p.connect(build);
			
			
		} else {
			sender.sendMessage(new TextComponent("No Player"));
		
		}
		
	}
}
