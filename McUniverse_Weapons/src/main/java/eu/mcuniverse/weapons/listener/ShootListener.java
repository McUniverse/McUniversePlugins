package eu.mcuniverse.weapons.listener;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.mcuniverse.weapons.data.Data;
import eu.mcuniverse.weapons.data.GunData;
import eu.mcuniverse.weapons.data.Messages;
import eu.mcuniverse.weapons.graphics.Skin;
import eu.mcuniverse.weapons.main.Core;
import eu.mcuniverse.weapons.runnables.ReloadTimer;
import eu.mcuniverse.weapons.util.ItemUtil;
import eu.mcuniverse.weapons.weapon.util.AmmunitionManager;
import eu.mcuniverse.weapons.weapon.util.AmmunitionType;
import eu.mcuniverse.weapons.weapon.util.Weapon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class ShootListener implements Listener {

	public ShootListener(Core main) {
		main.getServer().getPluginManager().registerEvents(this, main);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getItem() == null || e.getMaterial() == null || e.getAction() == Action.PHYSICAL) {
			return;
		}
		Player p = e.getPlayer();
		if (e.getAction().toString().contains("RIGHT")) {
			Core.getWeaponManager().getWeapons().forEach(weapon -> {
				if (ItemUtil.isWeapon(e.getItem(), weapon)) {
					if (Data.cooldown.containsKey(p.getUniqueId()) && Data.cooldown.get(p.getUniqueId()) > System.currentTimeMillis()) {
						return;
					}
					
					ItemStack item = e.getItem();
					String name = item.getItemMeta().getDisplayName();
					if (GunData.AMMO_PATTERN.matcher(name).matches()) {
						if (AmmunitionManager.isReloading(item)) {
							p.playSound(p.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, SoundCategory.MASTER, 1f, 2f);
							return;
						}
						int ammo = AmmunitionManager.getAmmo(item);
						if (ammo <= 0) {
							p.playSound(p.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, SoundCategory.MASTER, 1f, 2f);
							return;
						} else {
//							AmmunitionManager.removeAmmunition(p, item, weapon);
						}
					}
					if (weapon.shoot(p)) {
						AmmunitionManager.removeAmmunition(p, item, weapon);
					}
					
					double speed = weapon.getShootingSpeed();
					speed /= 20;
					speed *= 1000;
					
					Data.cooldown.put(p.getUniqueId(), (long) (System.currentTimeMillis() + speed));
					
				}
			});
		} else if (e.getAction().toString().contains("LEFT")) {
			if (Data.reloading.containsKey(p.getUniqueId())) {
				p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Already reloading..."));
				p.playSound(p.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, SoundCategory.MASTER, 1f, 2f);
				return;
			}
			Weapon w = null;
			// Cast weapon
			for (Weapon weapon : Core.getWeaponManager().getWeapons()) {
				if (ItemUtil.isWeapon(e.getItem(), weapon)) {
					w = weapon;
					break;
				}
			}
			if (w == null) {
				return;
			}

			// Test if weapon is already full
			if (AmmunitionManager.getAmmo(e.getItem()) == w.getMagazineSize()) {
				return;
			}

			// Get AmmunitionType
			AmmunitionType type;
			try {
				type = AmmunitionType.valueOf(ItemUtil.NBT.getValue(e.getItem(), GunData.AMMO_TYPE_KEY));
			} catch (Exception exe) {
				return;
			}

			// Get amount of ammunition
			ItemStack[] content = p.getInventory().getContents();
			boolean found = false;
			int count = 0;
			for (int i = 0; i < content.length; i++) {
				ItemStack stack = content[i];
				ItemMeta meta;
				try {
					meta = stack.getItemMeta();
				} catch (Exception exe) {
					continue;
				}
				if (stack.getType() == Skin.baseMaterial) {
					if (meta.getCustomModelData() == type.getSkin().getCustomModelData()) {
						count += stack.getAmount();
						found = true;
					}
				}
			}
			if (!found || count == 0) {
				p.sendMessage(Messages.WARNING + "You're out of ammo");
				return;
			} else {
				p.getInventory().setItemInMainHand(AmmunitionManager.addReloadTag(e.getItem()));

				p.playSound(p.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, SoundCategory.MASTER, 1f, 2f);

				p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Reloading"));
				
				Data.reloading.put(p.getUniqueId(), new ReloadTimer(p, e.getItem(), count, w, type));
			}
		}
	}

}
