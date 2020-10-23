package eu.mcuniverse.weapons.weapon.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import eu.mcuniverse.weapons.data.GunData;
import eu.mcuniverse.weapons.graphics.Skin;
import eu.mcuniverse.weapons.item.Items;
import eu.mcuniverse.weapons.weapon.Famas;
import eu.mcuniverse.weapons.weapon.Glock22;
import eu.mcuniverse.weapons.weapon.HKMP5;
import eu.mcuniverse.weapons.weapon.LaserLMG;
import eu.mcuniverse.weapons.weapon.LaserRifle;
import eu.mcuniverse.weapons.weapon.LaserSniper;
import eu.mcuniverse.weapons.weapon.RocketLauncher;
import eu.mcuniverse.weapons.weapon.SCAR;
import eu.mcuniverse.weapons.weapon.Shotgun;
import lombok.Getter;
import lombok.NonNull;
import net.md_5.bungee.api.ChatColor;

public class WeaponManager {

	@Getter
	private Set<Weapon> weapons = Sets.newHashSet();

	@Getter
	private Set<ItemStack> items = new HashSet<ItemStack>();
	
	public WeaponManager() {
		if (weapons.isEmpty()) {
			weapons.add(new Famas());
			weapons.add(new Glock22());
			weapons.add(new HKMP5());
			weapons.add(new LaserLMG());
			weapons.add(new LaserRifle());
			weapons.add(new LaserSniper());
			weapons.add(new RocketLauncher());
			weapons.add(new SCAR());
			weapons.add(new Shotgun());
		}
		weapons.stream().map(w -> getItem(w)).forEach(items::add);
		items.add(Items.Explosives.getC4());
		items.add(Items.Explosives.getGrenade());
		items.add(Items.Explosives.getIgniter());
	}
	
	public Weapon getWeapon(WeaponType weaponType) {
		for (Weapon w : this.weapons) {
			if (w.getWeaponType() == weaponType) {
				return w;
			}
		}
		return null;
	}
	
	public static ItemStack getItem(@NonNull Weapon weapon) {
		ItemStack item = new ItemStack(Skin.baseMaterial);
		ItemMeta meta = item.getItemMeta();
		meta.setCustomModelData(weapon.getWeaponType().getSkin().getCustomModelData());
		String name = String.format(GunData.WEAPON_NAME, weapon.getWeaponType().getSkin().getDisplayName(), weapon.getMagazineSize(), weapon.getMagazineSize());
		meta.setDisplayName(name);
		List<String> lore = Lists.newLinkedList();
		lore.add(ChatColor.YELLOW + "Damage: " + ChatColor.AQUA + weapon.getDamage() + ChatColor.DARK_RED + "\u2764"); //Heart
		lore.add(ChatColor.YELLOW + "Range: " + ChatColor.AQUA + weapon.getRange());
		lore.add(ChatColor.YELLOW + "Magazine size: " + ChatColor.AQUA + weapon.getMagazineSize());
		lore.add(ChatColor.YELLOW + "Reloading time: " + ChatColor.AQUA + (weapon.getReloadingTime() / 20) + "s");
		lore.add(ChatColor.YELLOW + "Ammunitiontype: " + ChatColor.AQUA + weapon.getAmmunitionType());
		meta.setLore(lore);
		item.setItemMeta(meta);
		item = AmmunitionManager.addNBTAmmount(item, weapon.getMagazineSize());
		item = AmmunitionManager.addNBTType(item, weapon.getAmmunitionType());
		item = AmmunitionManager.removeReloadTag(item);
		return item;
	}
	
	public static String getDisplayName(@NonNull ItemStack item, @NonNull Weapon weapon) {
		try {
			int ammo = AmmunitionManager.getAmmo(item);
			return String.format(GunData.WEAPON_NAME, weapon.getWeaponType().getSkin().getDisplayName(), ammo, weapon.getMagazineSize());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
