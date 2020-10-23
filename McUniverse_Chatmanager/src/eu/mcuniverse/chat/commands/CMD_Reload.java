package eu.mcuniverse.chat.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

import eu.mcuniverse.chat.main.main;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class CMD_Reload
  implements Listener
{
  public static List<String> blocked = new ArrayList<String>();
  public static Plugin plugin;
  
  public CMD_Reload(Plugin plugin)
  {
    CMD_Reload.plugin = plugin;
  }
  
  @EventHandler
  public void on(PlayerCommandPreprocessEvent e)
  {
    Player player = e.getPlayer();
    PermissionUser user = PermissionsEx.getUser(player);
    String msg = e.getMessage().split(" ")[0].replace("/", "").toLowerCase();
    if ((msg.equalsIgnoreCase("reload")) || (msg.equalsIgnoreCase("rl")))
    {
      if (!player.hasPermission("mcuniverse.admin"))
      {
        e.setCancelled(true);
        return;
      }
      e.setCancelled(true);
      Bukkit.broadcastMessage("");
      Bukkit.broadcastMessage(Main.prefix + "�cThe Server is reloading!");
      Bukkit.broadcastMessage(Main.prefix + "�cPlease �c�ndo not �c move and write!");
      Bukkit.broadcastMessage("");
      Bukkit.reload();
      Bukkit.broadcastMessage(Main.prefix + "�cThe �eServer �cwas successfully reloaded!");
        
      }
    if ((msg.equalsIgnoreCase("pex")) || (msg.equalsIgnoreCase("pex")) || (msg.equalsIgnoreCase("pex help")))
    {
      if (player.hasPermission("mcuniverse.admin")) {
        return;
      }
      e.setCancelled(true);
      for (Player players : Bukkit.getOnlinePlayers()) {
        if (players.hasPermission("mcuniverse.admin")) {
          players.sendMessage(Main.prefix + "�a" + user.getName() + "�cwanted to see the �eRights system.");
        }
      }
    }
    if ((msg.equalsIgnoreCase("pex")) || (msg.equalsIgnoreCase("pex")) || (msg.equalsIgnoreCase("pex help")))
    {
      if (player.hasPermission("mcuniverse.admin")) {
        return;
      }
      e.setCancelled(true);
    }
    if ((msg.equalsIgnoreCase("plugins")) || (msg.equalsIgnoreCase("pl")))
    {
      if (player.hasPermission("mcuniverse.admin")) {
        return;
      }
      e.setCancelled(true);
      for (Player players : Bukkit.getOnlinePlayers()) {
        if (players.hasPermission("mcuniverse.admin")) {
          players.sendMessage(Main.prefix + "�a" + user.getName() + "�cwanted to see the Plugins.");
        }
      }
    }
}
  }
