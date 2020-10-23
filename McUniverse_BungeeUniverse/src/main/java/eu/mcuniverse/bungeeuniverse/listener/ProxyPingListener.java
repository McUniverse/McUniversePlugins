package eu.mcuniverse.bungeeuniverse.listener;

import eu.mcuniverse.bungeeuniverse.Core;
import eu.mcuniverse.bungeeuniverse.data.Data;
import eu.mcuniverse.bungeeuniverse.data.Messages;
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
		
		ping.setPlayers(players);
		
		if (Data.maintenance) {
			ping.setVersion(new ServerPing.Protocol(ChatColor.DARK_GRAY + Messages.ARROW + ChatColor.RED + " Maintenance", 32767));
		}
		
		String motd = Core.getConfig().getString("motd");
		motd = ChatColor.translateAlternateColorCodes('&', motd);
		motd = motd.replace("\\n", "\n");
		motd = motd.replace("%STATUS%", Core.getConfig().getInt("status") + "");
		ping.setDescriptionComponent(new TextComponent(motd));
		
		e.setResponse(ping);
		
	}
	
}
