package eu.mcuniverse.essentials.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.TabCompleteEvent;

import eu.mcuniverse.essentials.Core;
import eu.mcuniverse.essentials.data.Data;

public class VanishListener implements Listener {

	public VanishListener(final Core plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Data.vanished.forEach(vanished -> e.getPlayer().hidePlayer(Core.getInstance(), e.getPlayer()));
	}
	
	@EventHandler
	public void onTabComplete(TabCompleteEvent e) {
		
	}
	
}
