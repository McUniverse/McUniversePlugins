package eu.mcuniverse.rocket.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import eu.mcuniverse.rocket.main.Core;

public class RocketInventoryPreventListener implements Listener {

	public RocketInventoryPreventListener(final Core plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	// Cancel switch from rocket to offhand
	@EventHandler(priority = EventPriority.HIGH)
	public void onSwithc(PlayerSwapHandItemsEvent e) {
		if (e.getOffHandItem().getType() == Material.FIREWORK_ROCKET) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPreventRocketFromPuttinginOffHand(InventoryClickEvent e) {
		if (!(e.getClickedInventory() instanceof PlayerInventory)) {
			return;
		}
		Player p = (Player) e.getWhoClicked();
		if (e.getCursor() == null) {
			return;
		}
		if (e.getCursor().getType() != Material.FIREWORK_ROCKET) {
			return;
		}
		if (e.getRawSlot() == 45 || e.getSlot() == 40) { // OFFHAND-Slots
			new BukkitRunnable() {

				@Override
				public void run() {
					p.getInventory().addItem(e.getClickedInventory().getItem(40));
					e.getClickedInventory().setItem(40, null);
				}
			}.runTaskLater(Core.getInstance(), 1L);
		}
	}

	@EventHandler
	public void onjPreventHotkey(InventoryClickEvent e) {
		if (!(e.getClickedInventory() instanceof PlayerInventory)) {
			return;
		}
		if (e.getClick() != ClickType.NUMBER_KEY) {
			return;
		}
		Player p = (Player) e.getWhoClicked();
		if (e.getRawSlot() == 45 || e.getSlot() == 40) {
			new BukkitRunnable() {

				@Override
				public void run() {
					p.getInventory().addItem(e.getClickedInventory().getItem(40));
					e.getClickedInventory().setItem(40, null);
				}
			}.runTaskLater(Core.getInstance(), 1L);
		}
	}

}
