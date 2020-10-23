package eu.mcuniverse.rocket.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import eu.mcuniverse.rocket.main.Core;
import eu.mcuniverse.rocket.oil.RefineryStorageManager;
import eu.mcuniverse.rocket.sql.MySQL;
import eu.mcuniverse.universeapi.rockets.Planet;

public class JoinListener implements Listener {

	public JoinListener(Core main) {
		main.getServer().getPluginManager().registerEvents(this, main);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (MySQL.hasRocket(e.getPlayer())) {
			MySQL.updateName(e.getPlayer());
		}
		if (RefineryStorageManager.hasRefinery(e.getPlayer().getUniqueId())) {
			RefineryStorageManager.updateName(e.getPlayer().getUniqueId(), e.getPlayer().getName());
		}
		if (!e.getPlayer().hasPlayedBefore()) {
			Location loc = Planet.EARTH.getRandomLocation();
			loc.setY(loc.getWorld().getHighestBlockYAt(loc.getBlockX(), loc.getBlockZ()));
			e.getPlayer().teleport(loc);
			e.getPlayer().getInventory().addItem(new ItemStack(Material.STONE_SWORD), new ItemStack(Material.BAKED_POTATO, 16));
		}
	}
}
