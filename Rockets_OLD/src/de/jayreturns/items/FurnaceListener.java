package de.jayreturns.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class FurnaceListener implements Listener {

	Inventory inv;
	
	public FurnaceListener() {
		inv = Bukkit.createInventory(null, InventoryType.BREWING, "Refinery");
	}
	
	@EventHandler
	public void onFurnaceClick(PlayerInteractEvent e) {
//		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock() != null) {
				if (e.getClickedBlock().getType() == Material.BLAST_FURNACE) {
					
				}
			}
		}
	}
	
}