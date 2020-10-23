package eu.mcuniverse.system.commands;


import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Discord extends Command{

	public CMD_Discord() {
		super("CMD_Discord", "", new String[] {"discord", "dc"});
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer){
			ProxiedPlayer p = (ProxiedPlayer) sender;

			
			p.sendMessage("§7[]§8§m------------------§7[] §6McUniverse §7[]§8§m------------------§7[]");
            p.sendMessage("");
            p.sendMessage("§7Discord Server: §ehttps://discord.gg/45FSfgX");
            p.sendMessage("");
            p.sendMessage("§7[]§8§m------------------§7[] §6McUniverse §7[]§8§m------------------§7[]");
			
			
		} else {
			sender.sendMessage(new TextComponent("No Player"));
		
		}
		
	}
}
