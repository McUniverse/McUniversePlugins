package eu.mcuniverse.bungeeuniverse.listener;

import java.util.concurrent.TimeUnit;

import eu.mcuniverse.bungeeuniverse.Core;
import eu.mcuniverse.bungeeuniverse.data.Data;
import eu.mcuniverse.bungeeuniverse.data.Messages;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyJoinListener implements Listener {

	@EventHandler
	public void onServerConnect(ServerConnectEvent e) {
		final ProxiedPlayer p = e.getPlayer();
		if (p.getServer() == null && Data.maintenance && !p.hasPermission("mcuniverse.maintenance")) {
			e.setCancelled(true);

			p.disconnect(TextComponent.fromLegacyText(Messages.MAINTENANCE_KICK));
		}
		ProxyServer.getInstance().getScheduler().schedule(Core.getInstance(), new Runnable() {

			@Override
			public void run() {
				if (p.getServer() != null) {
					p.setTabHeader(new TextComponent(ChatColor.GOLD + "McUniverse.eu " + ChatColor.DARK_GRAY + Messages.ARROW + " "
							+ ChatColor.YELLOW + p.getServer().getInfo().getName() + "\n"
							+ ChatColor.GRAY + "Online: " + ChatColor.GREEN + ProxyServer.getInstance().getPlayers().size()
							+ ChatColor.GRAY + "/" + ChatColor.GREEN + Data.MAX_PLAYERS),
							new TextComponent(ChatColor.GRAY+ "Join our Discord: " + ChatColor.YELLOW + Data.DISCORD_URL));
				}
			}
			// FIXME Remove preiodical timer!
		}, 1L, 1L, TimeUnit.SECONDS);
	}

}
