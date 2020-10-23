package eu.mcuniverse.supplyrockets.listener;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import eu.mcuniverse.supplyrockets.Core;
import eu.mcuniverse.supplyrockets.rockets.SRocketTier1;
import eu.mcuniverse.supplyrockets.rockets.SRocketTier2;
import eu.mcuniverse.universeapi.api.UniverseAPI;
import eu.mcuniverse.universeapi.rockets.Planet;

public class RocketClickListener implements Listener {

	public RocketClickListener(final Core plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onArmorstandClick(PlayerInteractAtEntityEvent e) {
		if (e.isCancelled() || e.getRightClicked() == null) {
			return;
		}
		if (e.getRightClicked().getType() != EntityType.ARMOR_STAND) {
			return;
		}
		Planet planet = Planet.MOON;
		try {
			planet = Planet.getPlanet(e.getPlayer());
		} catch (Exception exe) {
			e.setCancelled(true);
			return;
		}
		if (e.getRightClicked().getUniqueId().equals(Core.getCurrentTier1().get(planet).getArmorStand().getUniqueId())) {
			e.setCancelled(true);

			Player p = e.getPlayer();
			p.openInventory(Core.getCurrentTier1().get(planet).getInventory());
			return;
		}
		if (e.getRightClicked().getUniqueId().equals(Core.getCurrentTier2().get(planet).getArmorStand().getUniqueId())) {
			e.setCancelled(true);
			e.getPlayer().openInventory(Core.getCurrentTier2().get(planet).getInventory());
			return;
		}
		if (e.getRightClicked().getUniqueId().equals(Core.getCurrentMeteorite().get(planet).getArmorStand().getUniqueId())) {
			e.setCancelled(true);
			if (Core.getCurrentMeteorite().get(planet).isLootAccessible()) {
				e.getPlayer().openInventory(Core.getCurrentMeteorite().get(planet).getInventory());
			} else {
				e.getPlayer().sendMessage(UniverseAPI.getWarning() + "You have to defeat the bosses first in order to get the loot!");
			}
			return;
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (e.getInventory().getHolder() instanceof SRocketTier1) {
			Inventory inv = e.getInventory();
			if (isInventoryEmtpty(inv)) {
				Player p = (Player) e.getPlayer();
				Planet planet = Planet.getPlanet(p);
				SRocketTier1 rocket = Core.getCurrentTier1().get(planet);
				final Entity armorStand = Bukkit.getEntity(rocket.getArmorStand().getUniqueId());

				Core.getCurrentTier1().remove(planet);

				launch(armorStand);
			}

		} else if (e.getInventory().getHolder() instanceof SRocketTier2) {
			Inventory inv = e.getInventory();
			if (isInventoryEmtpty(inv)) {
				Player p = (Player) e.getPlayer();
				Planet planet = Planet.getPlanet(p);
				SRocketTier2 rocket = Core.getCurrentTier2().get(planet);
				final Entity armorStand = Bukkit.getEntity(rocket.getArmorStand().getUniqueId());

				Core.getCurrentTier2().remove(planet);
				launch(armorStand);

			}
		}
	}

	private void launch(Entity armorStand) {
		
		new BukkitRunnable() {

			@Override
			public void run() {
				if (armorStand.getLocation().getBlockY() > 240) {
					armorStand.remove();
					this.cancel();
				} else {
					armorStand.getWorld().spawnParticle(Particle.FLAME, armorStand.getLocation().add(0, 0, 0.7), 10, 0.1, 1, 0.1,
							0);
					armorStand.setVelocity(new Vector(0, 1, 0));
				}
			}
		}.runTaskTimer(Core.getInstance(), 0L, 1L);
	}

	private boolean isInventoryEmtpty(Inventory inv) {
		for (ItemStack stack : inv.getContents()) {
			if (stack != null)
				return false;
		}
		return true;
	}

}
