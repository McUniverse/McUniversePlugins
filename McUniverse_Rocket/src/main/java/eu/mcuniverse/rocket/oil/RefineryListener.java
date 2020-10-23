package eu.mcuniverse.rocket.oil;

import java.util.Arrays;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Lockable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import eu.mcuniverse.rocket.main.Core;
import eu.mcuniverse.universeapi.api.UniverseAPI;
import eu.mcuniverse.universeapi.item.CustomItem;
import net.md_5.bungee.api.ChatColor;

public class RefineryListener implements Listener {

	public RefineryListener(final Core plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onScrollClick(PlayerInteractEvent e) {
		if (!e.hasBlock() || !e.hasItem()) {
			return;
		}

		if (e.getItem().isSimilar(CustomItem.Oil.getBlueprint())) {
			Player p = e.getPlayer();
			if (RefineryStorageManager.hasRefinery(p.getUniqueId())) {
				e.setCancelled(true);
				p.sendMessage(UniverseAPI.getWarning() + "You already own a refinery");
				return;
			}
			if (!RefineryManager.canRefineryBePlaced(e.getClickedBlock().getLocation())) {
				e.setCancelled(true);
				p.sendMessage(UniverseAPI.getWarning() + "There is not enough space for your refinery here");
				int length = RefineryManager.SIZE;
				p.sendMessage(UniverseAPI.getWarning() + "Required Space: " + ChatColor.YELLOW + length + "\u00D7" + length
						+ "\u00D7" + RefineryManager.HEIGHT);
				return;
			} else {
				RefineryManager.spawnRefinery(p, e.getClickedBlock().getLocation());
			}
			RefineryManager.canRefineryBePlaced(e.getClickedBlock().getLocation());
		}
//		else if (e.getItem().getType() == Material.GLOBE_BANNER_PATTERN) {
//			e.getPlayer().getInventory().setItemInMainHand(CustomItem.Oil.getBlueprint());
//			return;
//		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (!e.hasBlock()) {
			return;
		}

		if (e.getClickedBlock().getType() == RefineryManager.BASE_BLOCK) {
			Lockable box = (Lockable) e.getClickedBlock().getState();
			if (box.getLock() == null || box.getLock().equalsIgnoreCase("")) {
				return;
			}
			if (RefineryManager.isRefinery(e.getPlayer().getUniqueId(), e.getClickedBlock().getLocation())) {
				int amount = RefineryStorageManager.getOilAmount(e.getPlayer());
				for (int i = 0; i < amount; i++) {
					e.getPlayer().getWorld().dropItemNaturally(e.getPlayer().getLocation().subtract(0.5, 0, 0.5),
							CustomItem.Rocket.getOil());
				}
				if (amount > 0) {
					e.getPlayer().playSound(RefineryStorageManager.getLocation(e.getPlayer().getUniqueId()),
							Sound.ITEM_BOTTLE_FILL, SoundCategory.MASTER, 1, 2);
				}
				RefineryStorageManager.updateOilAmount(e.getPlayer(), 0);
				RefineryManager.updateHologram(e.getPlayer(), RefineryManager.getArmorstand(e.getPlayer()));
			}
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (!Arrays.asList(RefineryManager.MATERIALS_USED).contains(e.getBlock().getType())) {
			return;
		}

		if (RefineryManager.getLocations().contains(e.getBlock().getLocation())) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(UniverseAPI.getWarning() + "You can't break that block!");
		}

		RefineryManager.locations.forEach(location -> {
			if (e.getBlock().getLocation().equals(location)) {
				if (RefineryManager.isRefinery(e.getPlayer().getUniqueId(), e.getBlock().getLocation())) {
					e.setCancelled(false);
					e.getPlayer().sendMessage(UniverseAPI.getPrefix() + "You destroyed your refinery");
					RefineryManager.destroyRefinery(e.getPlayer());
				} else {
					e.setCancelled(true);
					e.getPlayer().sendMessage(UniverseAPI.getWarning() + "You cannot destroy someone else's refinery!");
				}
			}
		});
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (RefineryManager.getLocations().contains(e.getBlock().getLocation())) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(UniverseAPI.getWarning() + "You can't place that block here!");
		}
	}

	@EventHandler
	public void onArmorstandDamge(EntityDamageByEntityEvent e) {
		if (e.getEntityType() != EntityType.ARMOR_STAND) {
			return;
		}
		if (e.getEntity().hasMetadata("refinery_display")) {
			if (e.getEntity().getMetadata("refinery_display").get(0).asBoolean()) {
				e.setCancelled(true);
			}
		}

	}

}
