package eu.mcuniverse.system.commands;

import eu.mcuniverse.system.Bungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Broadcast extends Command {
  public CMD_Broadcast() {
    super("CMD_Broadcast", "mcuniverse.broadcast", new String[] {"broadcast", "bc", "rundruf", "alert"});
  }
  
  @SuppressWarnings("deprecation")
public void execute(CommandSender sender, String[] args) {
    ProxiedPlayer p = (ProxiedPlayer)sender;
    if (args.length > 0) {
      String message = "";
      for (int i = 0; i < args.length; i++)
        message = message + args[i] + " "; 
      message = ChatColor.translateAlternateColorCodes('&', message);
      ProxyServer.getInstance().broadcast("§c§lImportant §8● §a"+ message);
    } else {
      p.sendMessage(Bungee.prefix + "§cUse: /bc <Nachricht>");
    } 
  }
}
