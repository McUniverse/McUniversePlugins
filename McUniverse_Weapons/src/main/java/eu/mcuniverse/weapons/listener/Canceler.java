package eu.mcuniverse.weapons.listener;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

import eu.mcuniverse.weapons.data.Data;
import eu.mcuniverse.weapons.main.Core;
import eu.mcuniverse.weapons.util.ItemUtil;

public class Canceler implements Listener {

	public Canceler(final Core plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onItemHeld(PlayerItemHeldEvent e) {
		Player p = e.getPlayer();
		ItemStack item = p.getInventory().getItemInMainHand();
		if (item == null) {
			return;
		}
		if (Data.reloading.containsKey(p.getUniqueId())) {
			Data.reloading.get(p.getUniqueId()).cancel();
			Data.reloading.remove(p.getUniqueId());
			p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, SoundCategory.MASTER, 1, 2);
		}
		
//		if (Data.cooldown.containsKey(p.getUniqueId())) {
//			Data.cooldown.remove(p.getUniqueId());
//		}
	}
	
	@EventHandler
	public void onSwap(PlayerSwapHandItemsEvent e) {
		if (e.getMainHandItem() == null) {
			return;
		}
		Player p = e.getPlayer();
		Core.getWeaponManager().getWeapons().forEach(weapon -> {
			if (ItemUtil.isWeapon(e.getMainHandItem(), weapon)) {
				e.setCancelled(true);
			}
		});
		if (Data.reloading.containsKey(p.getUniqueId())) {
			Data.reloading.get(p.getUniqueId()).cancel();
			Data.reloading.remove(p.getUniqueId());
			p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, SoundCategory.MASTER, 1, 2);
		}
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		
		
		if (!(e.getWhoClicked() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getWhoClicked();
		Core.getWeaponManager().getWeapons().forEach(weapon -> {
			if (ItemUtil.isWeapon(e.getCurrentItem(), weapon)) {
				if (Data.reloading.containsKey(p.getUniqueId())) {
					Data.reloading.get(p.getUniqueId()).cancel();
					Data.reloading.remove(p.getUniqueId());
					p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, SoundCategory.MASTER, 1, 2);
				}
			}
		});
	}

}
