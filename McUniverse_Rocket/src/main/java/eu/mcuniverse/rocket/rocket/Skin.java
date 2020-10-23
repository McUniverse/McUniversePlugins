package eu.mcuniverse.rocket.rocket;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import eu.mcuniverse.rocket.util.ItemUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;

@Getter
@RequiredArgsConstructor
public enum Skin {

	ROCKET(10000008, true),
	SHUTTLE(10000006, false),
	SATURN_V(10000007, false);

	final int customModelData;
	final boolean facingUp;

//	Skin(int data) {
//		this.customModelData = data;
//	}

	public ItemStack getItem() {
		return ItemUtil.createSkinItem(Material.STICK, ChatColor.GOLD + WordUtils.capitalize(super.toString()),
				getCustomModelData());
	}

}
