package eu.mcuniverse.rocket.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import eu.mcuniverse.rocket.inventories.RocketLaunchGUI;
import eu.mcuniverse.rocket.main.Core;
import eu.mcuniverse.rocket.rocket.InRocketManager;
import eu.mcuniverse.universeapi.rockets.Planet;

public class InRocketListener implements Listener {

	Core plugin;

	public InRocketListener(Core main) {
		plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if (InRocketManager.isInRocket(e.getPlayer())) {
			InRocketManager.managePlayer(e.getPlayer());
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (InRocketManager.isInRocket(e.getPlayer())) {
			InRocketManager.managePlayer(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (InRocketManager.isInRocket(e.getPlayer())) {
			InRocketManager.managePlayer(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		if (InRocketManager.isInRocket(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onEntityClick(PlayerInteractAtEntityEvent e) {
		if (!InRocketManager.isInRocket(e.getPlayer())) {
			return;
		}
		e.setCancelled(true);
		Player p = e.getPlayer();
		if (p.getInventory().getItemInMainHand().getType() == Material.SHULKER_SHELL) {
			e.setCancelled(true);
			InRocketManager.pause(p);
			p.openInventory(new RocketLaunchGUI(p, Planet.getPlanet(p)).getInventory());
		} else if (p.getInventory().getItemInMainHand().getType() == Material.BARRIER) {
			e.setCancelled(true);
			InRocketManager.managePlayer(p);
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getMaterial() == null) {
			return;
		}
		if (!InRocketManager.isInRocket(e.getPlayer())) {
			return;
		}

		Player p = e.getPlayer();
		if (e.getMaterial() == Material.SHULKER_SHELL) {
			e.setCancelled(true);
			p.openInventory(new RocketLaunchGUI(p, Planet.getPlanet(p)).getInventory());
			InRocketManager.pause(p);
		} else if (e.getMaterial() == Material.BARRIER) {
			e.setCancelled(true);
			InRocketManager.managePlayer(p);
		}
	}

	@EventHandler
	public void onInvClose(InventoryCloseEvent e) {
		if (InRocketManager.isInRocket((Player) e.getPlayer())) {
			InRocketManager.unpause((Player) e.getPlayer());
		}
	}

	@EventHandler
	public void onPickup(EntityPickupItemEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (InRocketManager.isInRocket(p)) {
				e.setCancelled(true);
			}
		}
	}
}
