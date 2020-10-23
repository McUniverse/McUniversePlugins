package eu.mcuniverse.weapons.listener;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.google.common.collect.Lists;

import eu.mcuniverse.weapons.main.Core;
import eu.mcuniverse.weapons.util.ItemUtil;

public class ZoomListener implements Listener {

	private List<String> zooming = Lists.newArrayList();

	public ZoomListener(final Core plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	// Player#isSneaking = false -> Starts sneaking
	// Player#isSneaking = true -> Stops sneaking
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		if (p.getInventory().getItemInMainHand() == null
				|| p.getInventory().getItemInMainHand().getType() == Material.AIR) {
			return;
		}
		if (p.isFlying())
			return;
		Core.getWeaponManager().getWeapons().forEach(weapon -> {
			if (ItemUtil.isWeapon(p.getInventory().getItemInMainHand(), weapon)) {
				if (!zooming.contains(p.getName()) && !p.isSneaking()) {
					zooming.add(p.getName());
					PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 3, false, false, false);
					p.addPotionEffect(effect);
				} else {
					zooming.remove(p.getName());
					p.removePotionEffect(PotionEffectType.SLOW);
				}
			}
		});
	}

	@EventHandler
	public void onHotBarSwith(PlayerItemHeldEvent e) {
		if (zooming.contains(e.getPlayer().getName())) {
			zooming.remove(e.getPlayer().getName());
			e.getPlayer().removePotionEffect(PotionEffectType.SLOW);
		}
	}

}
