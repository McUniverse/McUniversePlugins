package eu.mcuniverse.rocket.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import eu.mcuniverse.rocket.main.Core;

public class ExplosionListener implements Listener {

	public ExplosionListener(final Core plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void oEntiytExplode(EntityExplodeEvent e) {
//		Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "EntityExplodeEvent");
//		Bukkit.broadcastMessage(ChatColor.AQUA + " - Entity: " + e.getEntity());
//		Bukkit.broadcastMessage(ChatColor.AQUA + " - EntityType: " + e.getEntityType());
//		Bukkit.broadcastMessage(ChatColor.AQUA + " - Yield: " + e.getYield());
//		Bukkit.broadcastMessage(ChatColor.AQUA + " - Location: " + e.getLocation());
//		Bukkit.broadcastMessage(ChatColor.AQUA + " - Blocklist: " + e.blockList().stream().map(b -> b.getType().toString()).collect(Collectors.toList()));

		Location loc = e.getLocation();
		float unalteredRadius = 2.5f;
		int radius = (int) Math.ceil(unalteredRadius);

		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
				for (int z = -radius; z <= radius; z++) {
					Location target = loc.clone().add(x, y, z);
					if (target.getBlock().getType() == Material.OBSIDIAN) {
						if (Math.random() > 0.1 && loc.distance(target) <= unalteredRadius) {
							e.blockList().add(target.getBlock());
						}
					}
				}
			}
		}
	}
}
