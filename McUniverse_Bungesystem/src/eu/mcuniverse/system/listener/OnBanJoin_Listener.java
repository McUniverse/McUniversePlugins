package eu.mcuniverse.system.listener;

import java.util.UUID;

import eu.mcuniverse.system.Bungee;
import eu.mcuniverse.system.mysql.MySQL;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class OnBanJoin_Listener
  implements Listener
{
  @EventHandler
  public void onLogin(final LoginEvent e)
  {
    final UUID uuid = e.getConnection().getUniqueId();
    final String name = e.getConnection().getName();
    
    e.registerIntent(Bungee.plugin);
    
    ProxyServer.getInstance().getScheduler().runAsync(Bungee.plugin, new Runnable()
    {
      @SuppressWarnings("deprecation")
	public void run()
      {
        if (MySQL.isUserExisting(uuid))
        {
          MySQL.setPlayerName(uuid, name);
          if (MySQL.getUntil(uuid) != -1L)
          {
            if (System.currentTimeMillis() - MySQL.getUntil(uuid) < 0L)
            {
              e.setCancelled(true);
              e.setCancelReason("§cDu wurdest §cTEMPORER §cvom Netzwerk gebannt. \n §cGrund §8» §e" + MySQL.getReason(uuid) + "\n §cGebannt von §8» §e" + MySQL.getBy(uuid) + "\n\n§aVerbleibende Zeit §8» §e" + MySQL.getTempBanLength(MySQL.getUntil(uuid)) + "\n \n §aDu kannst auf unserem §eTeamspeak³ §aeinen §eEntbannungsantrag §astellen.");
            }
            else
            {
              MySQL.setUnBanned(uuid);
            }
          }
          else
          {
            e.setCancelled(true);
            e.setCancelReason("§cDu wurdest §4PERMANENT §cvom Netzwerk gebannt. \n §cGrund §8» §e" + MySQL.getReason(uuid) + "\n §cGebannt von §8» §e" + MySQL.getBy(uuid) + "\n \n §aDu kannst auf unserem §eTeamspeak³ §aeinen §eEntbannungsantrag §astellen.");
          }
        }
        e.completeIntent(Bungee.plugin);
      }
    });
  }
}
