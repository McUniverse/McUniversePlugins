package eu.mcuniverse.rocket.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import eu.mcuniverse.rocket.main.Core;
import eu.mcuniverse.universeapi.item.CustomItem;

public class BlockPlaceListener implements Listener {

	public BlockPlaceListener(final Core plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (e.getItemInHand().isSimilar(CustomItem.enhancedChest)) {
//			e.setCancelled(tru);
			e.setBuild(false);
		}
	}
	
}
