package eu.mcuniverse.recipes.utils;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {

	public static ItemStack createSkinItem(Material material, String name, int data, String... lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setCustomModelData(data);
		ArrayList<String> metaLore = new ArrayList<String>();

		for (String loreComments : lore) {
			metaLore.add(loreComments);
		}

		meta.setLore(metaLore);
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;

	}
}