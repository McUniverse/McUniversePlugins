package eu.mcuniverse.weapons.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.mcuniverse.weapons.graphics.Skin;
import eu.mcuniverse.weapons.util.ItemUtil;
import eu.mcuniverse.weapons.weapon.util.AmmunitionType;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class Items {

	public ItemStack getAmmunition(AmmunitionType type) {
		return getAmmunition(type, 1);
	}

	public ItemStack getAmmunition(AmmunitionType type, int amount) {
		ItemStack item = new ItemStack(Skin.baseMaterial, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(type.getSkin().getDisplayName());
		meta.setCustomModelData(type.getSkin().getCustomModelData());
		item.setItemMeta(meta);
		return item;
	}

	@UtilityClass
	public class Explosives {

		public ItemStack getGrenade() {
			return ItemUtil.createSkinItem(Skin.baseMaterial, ChatColor.DARK_RED + "Grenade", Skin.GRENADE.getCustomModelData());
//			return ItemUtil.createFireworkSkin(ChatColor.DARK_RED + "Grenade", Color.LIME, Skin.GRENADE.getCustomModelData());
//			return ItemUtil.createFireworkStar(ChatColor.DARK_RED + "Grenade", Color.LIME);
		}

		public ItemStack getC4() {
			return ItemUtil.createSkinItem(Material.JUNGLE_BUTTON, ChatColor.DARK_RED + "C4", 10000001);
//			return ItemUtil.createItem(Material.JUNGLE_BUTTON, ChatColor.DARK_RED + "C4");
		}

		public ItemStack getIgniter() {
			return ItemUtil.createItem(Material.REDSTONE_TORCH, ChatColor.GRAY + "Igniter");
		}

		public ItemStack getSmokeGrenade() {
			return ItemUtil.createSkinItem(Skin.baseMaterial, ChatColor.GRAY + "" + ChatColor.BOLD + "Smoke Grenade",
					Skin.SMOKE_GRENADE.getCustomModelData());
		}

	}

}
