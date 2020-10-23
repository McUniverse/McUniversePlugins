package eu.mcuniverse.testingbungee.main;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class MessagingListener implements Listener {

	@EventHandler
	public void onMessage(PluginMessageEvent e) {
		if (e.getReceiver() instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) e.getReceiver();
			System.out.println(p.getName());
		}
		ProxyServer.getInstance().getConsole()
				.sendMessage(TextComponent.fromLegacyText(e.getTag() + " --- " + new String(e.getData()), ChatColor.AQUA));
	}
	
}
