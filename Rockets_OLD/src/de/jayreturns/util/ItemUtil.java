package de.jayreturns.util;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {

	public static ItemStack createItem(Material material, String name, String... lore) {
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		ArrayList<String> metaLore = new ArrayList<String>();

		for (String loreComments : lore) {
			metaLore.add(loreComments);
		}

		meta.setLore(metaLore);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack createEnchantedItem(Material material, String name, String... lore) {
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(Enchantment.DURABILITY, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.setDisplayName(name);
		ArrayList<String> metaLore = new ArrayList<String>();

		for (String loreComments : lore) {
			metaLore.add(loreComments);
		}

		meta.setLore(metaLore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getFiller() {
		return createItem(Material.BLACK_STAINED_GLASS_PANE, " ");
	}
	
}