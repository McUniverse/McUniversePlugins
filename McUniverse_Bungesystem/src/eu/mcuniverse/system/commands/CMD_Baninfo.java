package eu.mcuniverse.system.commands;

import eu.mcuniverse.system.Bungee;
import eu.mcuniverse.system.mysql.MySQL;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Baninfo
  extends Command
{
  public CMD_Baninfo()
  {
    super("baninfo");
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
        if ((p.hasPermission("mcuniverse.team") | p.hasPermission("mcuniverse.*")))
        {
          if (args.length == 1)
          {
            if ((MySQL.isUserExisting(args[0])) && 
              (MySQL.getUntil(args[0]) != -1L) && (System.currentTimeMillis() - MySQL.getUntil(args[0]) > 0L)) {
              MySQL.setUnBanned(args[0]);
            }
            p.sendMessage("§8┃ §cBanInfo §8┃§8§m-----------------§r§8┃§cBan §8§m┃------------------");
            if (MySQL.isUserExisting(args[0]))
            {
              p.sendMessage("§8┃ §cBan §8┃§7Player §8» §e" + MySQL.getPlayer(args[0]));
              p.sendMessage("§8┃ §cBan §8┃§7Banned §8» §cYES");
              p.sendMessage("§8┃ §cBan §8┃§7Reason §8» §e" + MySQL.getReason(args[0]));
              p.sendMessage("§8┃ §cBan §8┃§7Banned from §8» §e" + MySQL.getBy(args[0]));
              if (MySQL.getUntil(args[0]) == -1L) {
                p.sendMessage("§8┃§cBan §8┃§7Banned time §8» §ePermanent");
              } else {
                p.sendMessage("§8┃§cBan §8┃§7Banned time §8» §e" + MySQL.getTempBanLength(MySQL.getUntil(args[0])));
              }
            }
            else
            {
              p.sendMessage("§8┃ §cBan §8┃§3Player §8» §e" + args[0]);
              p.sendMessage("§8┃ §cBan §8┃§3Banned §8» §aNO");
            }
            p.sendMessage("§8┃ §cBan §8┃§8§m-----------------§r§8┃§cBan §8§m┃------------------");
            return;
          }
          p.sendMessage(Bungee.prefix + "§cYou need to enter a player to get ban info about him.");
          return;
        }
        p.sendMessage(Bungee.perms);
      }
    });
  }
}
