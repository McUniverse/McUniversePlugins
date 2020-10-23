package eu.mcuniverse.rocket.listener;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

import eu.mcuniverse.rocket.data.Variables;
import eu.mcuniverse.rocket.inventories.DeleteGUI;
import eu.mcuniverse.rocket.inventories.HullGUI;
import eu.mcuniverse.rocket.inventories.RocketFuelGUI;
import eu.mcuniverse.rocket.inventories.RocketGUI;
import eu.mcuniverse.rocket.inventories.RocketLaunchGUI;
import eu.mcuniverse.rocket.inventories.RocketStorageGUI;
import eu.mcuniverse.rocket.inventories.SkinGUI;
import eu.mcuniverse.rocket.inventories.UpgradeGUI;
import eu.mcuniverse.rocket.inventories.WaterTankGUI;
import eu.mcuniverse.rocket.items.Items;
import eu.mcuniverse.rocket.main.Core;
import eu.mcuniverse.rocket.rocket.InRocketManager;
import eu.mcuniverse.rocket.rocket.Rocket;
import eu.mcuniverse.rocket.rocket.RocketManager;
import eu.mcuniverse.rocket.sql.MySQL;
import eu.mcuniverse.rocket.util.ItemUtil;
import eu.mcuniverse.universeapi.inventory.IGUI;
import eu.mcuniverse.universeapi.item.CustomItem;

public class RocketMenus implements Listener {

	private Core plugin;

	public RocketMenus(Core main) {
		plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onMenuClick(PlayerInteractAtEntityEvent e) {
		if (e.getRightClicked() == null || e.getRightClicked().getType() != EntityType.ARMOR_STAND
				|| e.getRightClicked().getCustomName() == null) {
			return;
		}
		if (InRocketManager.isInRocket(e.getPlayer())) {
			return;
		}
		if (e.getRightClicked().getCustomName().contains(Variables.ARMOR_STAND_NAME)) {
			e.setCancelled(true);
		}

		Player p = e.getPlayer();
		if (RocketManager.hasRocket(p)) {
			Rocket r = RocketManager.getRocket(p);
			if (e.getRightClicked().equals(Bukkit.getEntity(r.getArmorStandUUID()))) {
				RocketGUI gui = new RocketGUI(p);
				p.openInventory(gui.getInventory());
			}
		}
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (e.getInventory().getHolder() instanceof IGUI) {
			if (e.getCurrentItem() != null) {
				if (e.getCurrentItem().isSimilar(ItemUtil.getFiller())) {
					e.setCancelled(true);
				}
			}
		}
		if (e.getInventory().getHolder() instanceof RocketGUI) {
			e.setCancelled(true);
			IGUI gui = (IGUI) e.getInventory().getHolder();
			gui.onGUIClick((Player) e.getWhoClicked(), e.getRawSlot(), e.getCurrentItem(), e.getCursor());
		} else if (e.getInventory().getHolder() instanceof RocketFuelGUI) {
			if (e.getCursor() == null || e.getCurrentItem() == null) {
				return;
			}
			if (e.getCursor().isSimilar(Items.getRocketFuel()) && e.getCurrentItem().getType() == Material.BLAZE_ROD) {
				e.setCancelled(false); // Test if Fule is put into fuel slot
			} else if (e.getCurrentItem().isSimilar(CustomItem.Rocket.getRocketFuel())) {
				e.setCancelled(false); // Test if fuel is picked up
			} else if (e.getCurrentItem().getType() == null && e.getCursor().isSimilar(Items.getRocketFuel())) {
				e.setCancelled(false); // Test if fuel is layed down
			} else {
				e.setCancelled(true);
			}

			IGUI gui = (IGUI) e.getInventory().getHolder();
			gui.onGUIClick((Player) e.getWhoClicked(), e.getRawSlot(), e.getCurrentItem(), e.getCursor());
		} else if (e.getInventory().getHolder() instanceof DeleteGUI) {
			e.setCancelled(true);
			IGUI gui = (IGUI) e.getInventory().getHolder();
			gui.onGUIClick((Player) e.getWhoClicked(), e.getRawSlot(), e.getCurrentItem(), e.getCursor());
		} else if (e.getInventory().getHolder() instanceof RocketStorageGUI) {
			if (!RocketStorageGUI.canItemBeAdded((Player) e.getWhoClicked(), e.getCurrentItem())) {
				e.setCancelled(true);
			}
			IGUI gui = (IGUI) e.getInventory().getHolder();
			gui.onGUIClick((Player) e.getWhoClicked(), e.getRawSlot(), e.getCurrentItem(), e.getCursor());
		} else if (e.getInventory().getHolder() instanceof RocketLaunchGUI) {
			e.setCancelled(true);
			IGUI gui = (IGUI) e.getInventory().getHolder();
			gui.onGUIClick((Player) e.getWhoClicked(), e.getRawSlot(), e.getCurrentItem(), e.getCursor());
		} else if (e.getInventory().getHolder() instanceof UpgradeGUI) {
			e.setCancelled(true);
			IGUI gui = (IGUI) e.getInventory().getHolder();
			gui.onGUIClick((Player) e.getWhoClicked(), e.getRawSlot(), e.getCurrentItem(), e.getCursor());
		} else if (e.getInventory().getHolder() instanceof SkinGUI) {
			e.setCancelled(true);
			IGUI gui = (IGUI) e.getInventory().getHolder();
			gui.onGUIClick((Player) e.getWhoClicked(), e.getRawSlot(), e.getCurrentItem(), e.getCursor());
		} else if (e.getInventory().getHolder() instanceof WaterTankGUI) {
			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || e.getCurrentItem().getType() == Material.WATER_BUCKET) {
				e.setCancelled(false);
			} else {
				e.setCancelled(true);
			}
			((IGUI) e.getInventory().getHolder()).onGUIClick((Player) e.getWhoClicked(), e.getRawSlot(), e.getCurrentItem(), e.getCursor());
		} else if (e.getInventory().getHolder() instanceof HullGUI) {
			e.setCancelled(true);
			((IGUI) e.getInventory().getHolder()).onGUIClick((Player) e.getWhoClicked(), e.getRawSlot(), e.getCurrentItem(), e.getCursor());
		}
	}

	@EventHandler
	public void onInvClose(InventoryCloseEvent e) {
		if (e.getInventory().getHolder() instanceof RocketStorageGUI) {
			Player p = (Player) e.getPlayer();
			MySQL.clearInventory(p);
			ItemStack[] con = e.getInventory().getContents();
			Rocket r = RocketManager.getRocket(p);
			r.getStorage().getSlotIndices().forEach(index -> {
				if (con[index] != null && con[index].getType() != Material.AIR) {
					r.addItemToInventory(con[index]);
				}
			});
		} else if (e.getInventory().getHolder() instanceof WaterTankGUI) {
//			e.getInventory().getcon
			AtomicInteger count = new AtomicInteger(0);
			Arrays
				.stream(e.getInventory().getContents())
				.filter(i -> i != null)
				.filter(i -> i.getType() == Material.WATER_BUCKET)
				.forEach(i -> count.addAndGet(1));
			Rocket r = RocketManager.getRocket((Player) e.getPlayer());
			r.setWaterLevel(count.get());
			r.saveRocket();
		}
	}

//	@EventHandler
//	public void onInvClose(InventoryCloseEvent e) {
//		if (e.getInventory().getHolder() instanceof RocketStorageGUI) {
//			ItemStack[] con = e.getInventory().getContents();
//			Rocket r = RocketManager.getRocket((Player) e.getPlayer());
//			int tier = r.getTier();
//			List<ItemStack> content = new ArrayList<>();
//			RocketUtil.getSlotIndecies(tier).forEach(index -> {
//				if (con[index] != null) {
//					content.add(con[index]);
//				}
//			});
//			r.saveRocket();
//		}
//	}

}
