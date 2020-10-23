package eu.mcuniverse.bungeeuniverse.listener;

import eu.mcuniverse.bungeeuniverse.ranks.LuckPermsUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinListener implements Listener {

	// TODO: Permission check. How could I forget that?!
	
	@EventHandler
	public void onJoin(PostLoginEvent e) {
		
		LuckPermsUtil lpb = LuckPermsUtil.getInstance();
		ProxiedPlayer p = e.getPlayer();
		String prefix = ChatColor.translateAlternateColorCodes('&', lpb.getPrefix(p.getUniqueId()));
		String message = prefix + p.getName() + ChatColor.GRAY + " is now" + ChatColor.GREEN + " Online";
		ProxyServer.getInstance().broadcast(TextComponent.fromLegacyText(message));
	}
	
	@EventHandler
	public void onLeave(PlayerDisconnectEvent e) {
		LuckPermsUtil lpb = LuckPermsUtil.getInstance();
		ProxiedPlayer p = e.getPlayer();
		String prefix = ChatColor.translateAlternateColorCodes('&', lpb.getPrefix(p.getUniqueId()));
		String message = prefix + p.getName() + ChatColor.GRAY + " is now" + ChatColor.RED + " Offline";
		ProxyServer.getInstance().broadcast(TextComponent.fromLegacyText(message));
	}
	
}
