package eu.mcuniverse.system.listener;

import java.util.concurrent.TimeUnit;

import eu.mcuniverse.system.Bungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyJoinListener implements Listener {
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onServerConnect(ServerConnectEvent e) {
		final ProxiedPlayer p = e.getPlayer();
		if (p.getServer() == null && Bungee.maintenance
				&& !Bungee.config.getStringList("Maintenance.Bypass").contains(p.getName())) {
			e.setCancelled(true);

			p.disconnect(
					"§cThe network is currently in maintenance work! \n \n §cTeamspeak  §8» §eMcUniverse.eu");
		}
		if (Bungee.arabs.contains(p.getName())) {
			e.setTarget(ProxyServer.getInstance().getServerInfo("RPG"));
		}
		ProxyServer.getInstance().getScheduler().schedule(Bungee.plugin, new Runnable() {
			public void run() {
				if (p.getServer() != null) {
					p.setTabHeader(
							new TextComponent("§6McUniverse.eu §8» §e" + p.getServer().getInfo().getName()
									+ "\n§7Online§7: §a" + ProxyServer.getInstance().getPlayers().size() + "§7/§a"
									+ Bungee.MaxPlayers),
							new TextComponent("§7Teamspeak: §e/teamspeak \n §7Application Phase §8» §a/apply"));
				}
			}
		}, 1L, 1L, TimeUnit.SECONDS);
	}
}