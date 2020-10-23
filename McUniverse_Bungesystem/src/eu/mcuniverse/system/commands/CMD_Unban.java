package eu.mcuniverse.system.commands;

import eu.mcuniverse.system.Bungee;
import eu.mcuniverse.system.mysql.MySQL;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Unban
  extends Command
{
  public CMD_Unban()
  {
    super("unban");
  }
  
  @SuppressWarnings("deprecation")
public void execute(CommandSender sender, final String[] args)
  {
    if (!(sender instanceof ProxiedPlayer))
    {
      sender.sendMessage(Bungee.prefix + "§cNo Player.");
      return;
    }
    final ProxiedPlayer p = (ProxiedPlayer)sender;
    
    ProxyServer.getInstance().getScheduler().runAsync(Bungee.plugin, new Runnable()
    {
      public void run()
      {
        if (((p.hasPermission("mcuniverse.*") | p.hasPermission("mcuniverse.unban"))))
        {
          if (args.length == 0)
          {
            p.sendMessage(Bungee.prefix + "§cYou have to enter a player to release him.");
            return;
          }
          if (args.length == 1)
          {
            if (MySQL.isUserExisting(args[0]))
            {
              for (ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
                if ((players.hasPermission("mcuniverse.team") | players.hasPermission("mcuniverse.*")))
                {
                  players.sendMessage("§8┃ §cBan §8┃ §8§m------------------§r§8| §cBan §8§m|--------------------");
                  players.sendMessage("§8┃ §cBan §8┃ §7Player §8» §e" + MySQL.getPlayer(args[0]));
                  players.sendMessage("§8┃ §cBan §8┃ §7Unbanned from §8» §e" + p.getName());
                  players.sendMessage("§8┃ §cBan §8┃ §8§m---------------------------------------------");
                }
              }
              MySQL.setUnBanned(args[0]);
              return;
            }
            p.sendMessage(Bungee.prefix + "§cThe Player §e" + args[0] + " §cis not banned.");
          }
        }
        else
        {
          p.sendMessage(Bungee.perms);
          return;
        }
      }
    });
  }
}
