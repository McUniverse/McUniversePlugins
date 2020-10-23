package eu.mcuniverse.rocket.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import eu.mcuniverse.rocket.main.Core;
import eu.mcuniverse.universeapi.api.UniverseAPI;
import eu.mcuniverse.universeapi.rockets.Planet;

public class RespawnListener implements Listener {

	public RespawnListener(final Core plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onRespawn(PlayerRespawnEvent e) {
		Planet planet = Planet.getPlanet(e.getPlayer());
		new BukkitRunnable() {

			@Override
			public void run() {
				try {
					e.getPlayer().getInventory().addItem(new ItemStack(Material.STONE_SWORD),
							new ItemStack(Material.BAKED_POTATO, 16));

					Location loc = planet.getRandomLocation().add(0, 2, 0);
					while (loc.getBlock().isLiquid()) {
						loc = planet.getRandomLocation().add(0, 2, 0);
					}
					e.getPlayer().teleport(loc.getWorld().getHighestBlockAt(loc).getLocation());
					e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10, 100));
					e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 10, 100));
				} catch (Exception exe) {
					e.getPlayer()
							.sendMessage(UniverseAPI.getError()
									+ "An internal erroc occured. Please provide a staff member with the following: "
									+ exe.getLocalizedMessage());
				}
			}
		}.runTaskLater(Core.getInstance(), 20L);
	}

}
