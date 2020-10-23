package eu.mcuniverse.system.listener;

import eu.mcuniverse.system.Bungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPingListener implements Listener {
	@EventHandler
	public void onProxyPing(ProxyPingEvent e) {
		ServerPing ping = e.getResponse();

		ServerPing.Players players = ping.getPlayers();

//		players.setMax(players.getOnline() + 5);
//		Bungee.MaxPlayers = players.getMax();

		ping.setPlayers(players);
		if (Bungee.maintenance) {
			ping.setVersion(new ServerPing.Protocol("§8» §cMaintenance", 32767));
		}
		
		String motd = Bungee.config.getString("motd");
		motd = ChatColor.translateAlternateColorCodes('&', motd);
		motd = motd.replace("\\n", "\n");
		motd = motd.replace("%STATUS%", Bungee.config.getInt("status") + "");
		ping.setDescriptionComponent(new TextComponent(motd));
		
		e.setResponse(ping);
	}
}