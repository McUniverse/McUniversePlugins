package eu.mcuniverse.system.commands;

import eu.mcuniverse.system.Bungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Kick
  extends Command
{
  public CMD_Kick()
  {
    super("kick");
  }
  
  @SuppressWarnings("deprecation")
public void execute(CommandSender sender, String[] args)
  {
    if (!(sender instanceof ProxiedPlayer))
    {
      sender.sendMessage(Bungee.prefix + "§cNo Player");
      return;
    }
    ProxiedPlayer p = (ProxiedPlayer)sender;
    if ((p.hasPermission("mcuniverse.team")))
    {
      if (args.length == 0)
      {
        p.sendMessage(Bungee.prefix + "§c/kick <Name> <Reason>");
        return;
      }
      if (args.length == 1)
      {
        p.sendMessage(Bungee.prefix + "§cYou must provide a reason to kick the player.");
        return;
      }
      if (args.length > 1)
      {
        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
        String reason = "";
        for (int i = 1; i < args.length; i++) {
          reason = reason + " " + args[i];
        }
        if (target == p)
        {
          p.sendMessage(Bungee.prefix + "§cYou can't kick yourself.");
          return;
        }
        if (target == null)
        {
          p.sendMessage(Bungee.prefix + "§cThe Player §e" + args[0] + " §cis not online.");
          return;
        }
        if ((!target.hasPermission("mcuniverse.team")) && (!target.hasPermission("mcuniverse.*"))) {
          if ((!target.hasPermission("mcuniverse.*") | p.hasPermission("mcuniverse.*")))
          {
            target.disconnect("§cYou were kicked out of the network.\n§cReason §8» §e" + reason + "\n§cKicked by §8» §e" + p.getName());
            for (ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
              if ((players.hasPermission("mcuniverse.*")))
              {
                players.sendMessage("§8┃ §cKick §8┃§8§m----------------§r§8┃ §cKick §8┃ §8§m----------------");
                players.sendMessage("§8┃ §cKick §8┃ §ePlayer §8» §e" + target.getName());
                players.sendMessage("§8┃ §cKick §8┃ §eReason §8»§e" + reason);
                players.sendMessage("§8┃ §cKick §8┃ §eKicked by §8» §e" + p.getName());
                players.sendMessage("§8┃ §cKick §8┃§8§m----------------§r§8┃ §cKick §8┃ §8§m----------------");
              }
            }
            return;
          }
        }
        p.sendMessage(Bungee.prefix + "§cYou may not kick any §eteam member.");
      }
    }
    else
    {
      p.sendMessage(Bungee.perms);
    }
  }
}
