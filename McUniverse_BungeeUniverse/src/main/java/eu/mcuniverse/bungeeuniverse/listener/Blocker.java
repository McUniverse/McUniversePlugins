package eu.mcuniverse.bungeeuniverse.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Blocker implements Listener {

	@EventHandler
	public void onComplete(TabCompleteEvent e) {
		if (e.getReceiver() instanceof ProxiedPlayer) {
			if (!((ProxiedPlayer) e.getReceiver()).hasPermission("mcuniverse.tab")) {
				e.setCancelled(true);
			}
		}
	}
}
