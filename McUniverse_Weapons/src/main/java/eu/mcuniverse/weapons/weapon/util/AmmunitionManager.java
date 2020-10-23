package eu.mcuniverse.weapons.weapon.util;

import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import eu.mcuniverse.weapons.data.*;
import eu.mcuniverse.weapons.util.*;
import lombok.experimental.*;

@UtilityClass
public class AmmunitionManager {

	public void removeAmmunition(Player player, ItemStack item, Weapon weapon) {
		int ammo = getAmmo(item);
		ItemStack decresed = ItemUtil.NBT.addNBTTag(item, GunData.AMMO_KEY, ammo - 1 + "");
		ItemMeta meta = decresed.getItemMeta();
		meta.setDisplayName(WeaponManager.getDisplayName(decresed, weapon));
		decresed.setItemMeta(meta);
		player.getInventory().setItemInMainHand(decresed);
	}

	public void reloadAmmunition(Player player, ItemStack item, Weapon weapon) {
		item = addNBTAmmount(item, weapon.getMagazineSize());
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(WeaponManager.getDisplayName(item, weapon));
		item.setItemMeta(meta);
		player.getInventory().setItemInMainHand(item);
	}

	public void reloadAmmunition(Player player, ItemStack item, Weapon weapon, int amount) {
		if (amount > getRequiredToFill(item, weapon)) {
			amount = getRequiredToFill(item, weapon);
		}
		item = addNBTAmmount(item, getAmmo(item) + amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(WeaponManager.getDisplayName(item, weapon));
		item.setItemMeta(meta);
		player.getInventory().setItemInMainHand(item);
	}

	public ItemStack addReloadTag(ItemStack item) {
		return ItemUtil.NBT.addNBTTag(item, GunData.RELOAD_KEY, "true");
	}

	public ItemStack removeReloadTag(ItemStack item) {
		return ItemUtil.NBT.addNBTTag(item, GunData.RELOAD_KEY, "false");
	}

	public ItemStack addNBTAmmount(ItemStack item, int ammo) {
		return ItemUtil.NBT.addNBTTag(item, GunData.AMMO_KEY, ammo + "");
	}

	public ItemStack addNBTType(ItemStack item, AmmunitionType type) {
		return ItemUtil.NBT.addNBTTag(item, GunData.AMMO_TYPE_KEY, type.toString());
	}

	public boolean isReloading(ItemStack item) {
		String s = ItemUtil.NBT.getValue(item, GunData.RELOAD_KEY);
		boolean b = false;
		try {
			b = Boolean.valueOf(s);
		} catch (Exception e) {
			return false;
		}
		return b;
	}

	public AmmunitionType getAmmoType(ItemStack item) {
		return AmmunitionType.valueOf(ItemUtil.NBT.getValue(item, GunData.AMMO_TYPE_KEY));
	}

	public int getAmmo(ItemStack item) {
		try {
			return Integer.valueOf(ItemUtil.NBT.getValue(item, GunData.AMMO_KEY));
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public int getRequiredToFill(ItemStack item, Weapon weapon) {
		int ammo = getAmmo(item);
		int maxAmmo = weapon.getMagazineSize();
		return maxAmmo - ammo;
	}

}
