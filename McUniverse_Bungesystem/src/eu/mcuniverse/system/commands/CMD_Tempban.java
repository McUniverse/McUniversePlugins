package eu.mcuniverse.system.commands;

import eu.mcuniverse.system.Bungee;
import eu.mcuniverse.system.mysql.MySQL;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Tempban
  extends Command
{
  public CMD_Tempban()
  {
    super("tempban");
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
        if ((p.hasPermission("mcuniverse.team") | p.hasPermission("cubestorm.architekt") | p.hasPermission("cubestorm.supporter") | p.hasPermission("cubestorm.moderator") | p.hasPermission("cubestorm.administrator") | p.hasPermission("cubestorm.srmoderator") | p.hasPermission("cubestorm.*")))
        {
          if (args.length == 0)
          {
            p.sendMessage(Bungee.prefix + "§c/tempban <Name> <Time> <Zeitangabe> <Reason>");
            return;
          }
          if (args.length == 1)
          {
            p.sendMessage(Bungee.prefix + "§cDu musst eine Zeit eingeben, um den Spieler zu bannen.");
            return;
          }
          if (args.length == 2)
          {
            p.sendMessage(Bungee.prefix + "§cDu musst ein Zeit-Format eingeben, um den Spieler zu bannen. (s, m, h, d, w)");
            return;
          }
          if (args.length == 3)
          {
            p.sendMessage(Bungee.prefix + "§cDu musst einen Grund eingeben, um den Spieler zu bannen.");
            return;
          }
          if (args.length > 3)
          {
            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
            String reason = "";
            for (int i = 3; i < args.length; i++) {
              reason = reason + " " + args[i];
            }
            long time = 0L;
            if (args[2].equalsIgnoreCase("s")) {
              try
              {
                long time2 = Long.valueOf(args[1]).longValue();
                if ((time2 > 0L) && (time2 <= 60L))
                {
                  time = System.currentTimeMillis() + time2 * 1000L;
                }
                else
                {
                  p.sendMessage(Bungee.prefix + "§cDu musst eine Zahl zwischen §e0 §cund §e60 §ceingeben.");
                  return;
                }
              }
              catch (NumberFormatException ex)
              {
                ex.printStackTrace();
                p.sendMessage(Bungee.prefix + "§cDu musst eine Zahl zwischen §e0 §cund §e60 §ceingeben.");
                return;
              }
            }
            if (args[2].equalsIgnoreCase("m")) {
              try
              {
                long time2 = Long.valueOf(args[1]).longValue();
                if ((time2 > 0L) && (time2 <= 60L))
                {
                  time = System.currentTimeMillis() + time2 * 1000L * 60L;
                }
                else
                {
                  p.sendMessage(Bungee.prefix + "§cDu musst eine Zahl zwischen §e0 §cund §e60 §ceingeben.");
                  return;
                }
              }
              catch (NumberFormatException ex)
              {
                ex.printStackTrace();
                p.sendMessage(Bungee.prefix + "§cDu musst eine Zahl zwischen §e0 §cund §e60 §ceingeben.");
                return;
              }
            }
            if (args[2].equalsIgnoreCase("h")) {
              try
              {
                long time2 = Long.valueOf(args[1]).longValue();
                if ((time2 > 0L) && (time2 <= 24L))
                {
                  time = System.currentTimeMillis() + time2 * 1000L * 60L * 60L;
                }
                else
                {
                  p.sendMessage(Bungee.prefix + "§cDu musst eine Zahl zwischen §e0 §cund §e24 §ceingeben.");
                  return;
                }
              }
              catch (NumberFormatException ex)
              {
                ex.printStackTrace();
                p.sendMessage(Bungee.prefix + "§cDu musst eine Zahl zwischen §e0 §cund §e24 §ceingeben.");
                return;
              }
            }
            if (args[2].equalsIgnoreCase("d")) {
              try
              {
                long time2 = Long.valueOf(args[1]).longValue();
                if ((time2 > 0L) && (time2 <= 364L))
                {
                  time = System.currentTimeMillis() + time2 * 1000L * 60L * 60L * 24L;
                }
                else
                {
                  p.sendMessage(Bungee.prefix + "§cDu musst eine Zahl zwischen §e0 §cund §e364 §ceingeben.");
                  return;
                }
              }
              catch (NumberFormatException ex)
              {
                ex.printStackTrace();
                p.sendMessage(Bungee.prefix + "§cDu musst eine Zahl zwischen §e0 §cund §e364 §ceingeben.");
                return;
              }
            }
            if (args[2].equalsIgnoreCase("w")) {
              try
              {
                long time2 = Long.valueOf(args[1]).longValue();
                if ((time2 > 0L) && (time2 <= 52L))
                {
                  time = System.currentTimeMillis() + time2 * 1000L * 60L * 60L * 24L * 7L;
                }
                else
                {
                  p.sendMessage(Bungee.prefix + "§cDu musst eine Zahl zwischen §e0 §cund §e52 §ceingeben.");
                  return;
                }
              }
              catch (NumberFormatException ex)
              {
                ex.printStackTrace();
                p.sendMessage(Bungee.prefix + "§cDu musst eine Zahl zwischen §e0 §cund §e52 §ceingeben.");
                return;
              }
            }
            if ((!args[2].equalsIgnoreCase("s")) && (!args[2].equalsIgnoreCase("m")) && (!args[2].equalsIgnoreCase("h")) && (!args[2].equalsIgnoreCase("d")) && (!args[2].equalsIgnoreCase("w")))
            {
              p.sendMessage(Bungee.prefix + "§cDu musst ein g§ltiges Zeit-Format eingeben, um den Spieler zu bannen. (s, m, h, d, w)");
              return;
            }
            if ((p.hasPermission("cubestorm.architekt") | p.hasPermission("cubestorm.supporter") | p.hasPermission("cubestorm.moderator") | p.hasPermission("cubestorm.srmoderator")))
            {
              if (args[2].equalsIgnoreCase("d")) {
                try
                {
                  long time2 = Long.valueOf(args[1]).longValue();
                  if (time2 > 30L)
                  {
                    p.sendMessage(Bungee.prefix + "§cDu darfst maximal f§r §e30 §cTage bannen.");
                    return;
                  }
                }
                catch (NumberFormatException ex)
                {
                  ex.printStackTrace();
                  p.sendMessage(Bungee.prefix + "§cDu musst eine Zahl zwischen §e0 §cund §e364 §ceingeben.");
                  return;
                }
              }
              if (args[2].equalsIgnoreCase("w")) {
                try
                {
                  long time2 = Long.valueOf(args[1]).longValue();
                  if (time2 > 4L)
                  {
                    p.sendMessage(Bungee.prefix + "§cDu darfst maximal fÃ¼r §e4 §cWochen bannen.");
                    return;
                  }
                }
                catch (NumberFormatException ex)
                {
                  ex.printStackTrace();
                  p.sendMessage(Bungee.prefix + "§cDu musst eine Zahl zwischen §e0 §cund §e52 §ceingeben.");
                  return;
                }
              }
            }
            if (target == p)
            {
              p.sendMessage(Bungee.prefix + "§cDu darfst dich nicht selbst bannen.");
              return;
            }
            if (target != null)
            {
              if ((!target.hasPermission("cubestorm.moderator")) && (!target.hasPermission("cubestorm.developer")) && (!target.hasPermission("cubestorm.srmoderator")) && (!target.hasPermission("cubestorm.architekt")) && (!target.hasPermission("cubestorm.supporter")) && (!target.hasPermission("cubestorm.*"))) {
                if ((!target.hasPermission("cubestorm.administrator") | p.hasPermission("cubestorm.administrator")))
                {
                  target.disconnect("§cDu wurdest für §e" + MySQL.getTempBanLength(time) + "§cvom Netzwerk gebannt.\n\n§3Grund §8»§e" + reason + "\n§3Gebannt von §8» §e" + p.getName());
                  MySQL.registerPlayer(target);
                  MySQL.setBanned(target, reason, p, time);
                  for (ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
                    if ((players.hasPermission("cubestorm.architekt") | players.hasPermission("cubestorm.supporter") | players.hasPermission("cubestorm.moderator") | players.hasPermission("cubestorm.srmoderator") | players.hasPermission("cubestorm.developer") | players.hasPermission("cubestorm.administrator") | players.hasPermission("cubestorm.*")))
                    {
                      players.sendMessage("§8┃ §cBan §8┃ §8§m---------------------§r§8┃ §cBan §8§m|---------------------");
                      players.sendMessage("§8┃ §cBan §8┃ §3Spieler §8» §e" + target.getName());
                      players.sendMessage("§8┃ §cBan §8┃ §3Grund §8»§e" + reason);
                      players.sendMessage("§8┃ §cBan §8┃ §3Gebannt von §8» §e" + p.getName());
                      players.sendMessage("§8┃ §cBan §8┃ §3Gebannte Zeit §8» §e" + MySQL.getTempBanLength(time));
                      players.sendMessage("§8┃ §cBan §8┃ §8§m-----------------------------------------------");
                    }
                  }
                  return;
                }
              }
              p.sendMessage(Bungee.prefix + "§cDu darfst keine §eTeam-Mitglieder §cbannen.");
              return;
            }
            if (MySQL.isUserExisting(args[0]))
            {
              p.sendMessage(Bungee.prefix + "§cDer Spieler §e" + MySQL.getPlayer(args[0]) + " §cist schon gebannt.");
              return;
            }
            if (MySQL.getName(args[0]) != null)
            {
              p.sendMessage(Bungee.prefix + "§cDer Spieler §e" + MySQL.getName(args[0]) + " §cwurde gebannt.");
              MySQL.registerPlayer(MySQL.getName(args[0]));
              MySQL.setBanned(MySQL.getName(args[0]), reason, p, time);
              for (ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
                if ((players.hasPermission("cubestorm.architekt") | players.hasPermission("cubestorm.supporter") | players.hasPermission("cubestorm.moderator") | players.hasPermission("cubestorm.srmoderator") | players.hasPermission("cubestorm.developer") | players.hasPermission("cubestorm.administrator") | players.hasPermission("cubestorm.*")))
                {
                  players.sendMessage("§8┃ §cBan §8┃ §8§m---------------------§r§8┃ §cBan §8§m|---------------------");
                  players.sendMessage("§8┃ §cBan §8┃ §3Spieler §8» §e" + MySQL.getName(args[0]));
                  players.sendMessage("§8┃ §cBan §8┃ §3Grund §8»§e" + reason);
                  players.sendMessage("§8┃ §cBan §8┃ §3Gebannt von §8» §e" + p.getName());
                  players.sendMessage("§8┃ §cBan §8┃ §3Gebannte Zeit §8» §e" + MySQL.getTempBanLength(time));
                  players.sendMessage("§8┃ §cBan §8┃ §8§m-----------------------------------------------");
                }
              }
              return;
            }
            p.sendMessage(Bungee.prefix + "§cDer Spieler §e" + args[0] + " §cexistiert nicht.");
          }
        }
        else
        {
          p.sendMessage(Bungee.perms);
        }
      }
    });
  }
}
