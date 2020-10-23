package eu.mcuniverse.rocket.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import eu.mcuniverse.rocket.main.Core;
import eu.mcuniverse.universeapi.item.CustomItem;

public class ConsumeListener implements Listener {

	public ConsumeListener(final Core plugin) {
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onConsume(PlayerItemConsumeEvent e) {
		if (e.getItem().isSimilar(CustomItem.Rocket.getOil()) || e.getItem().isSimilar(CustomItem.Rocket.getRocketFuel())) {
			e.setCancelled(true);
		}
	}
	
}
