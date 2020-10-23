package de.jayreturns.items;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItem {

	private Material type;
	private List<String> lore;
	private ItemStack is;
	private ItemMeta im;
	private String displayName;
	private Map<Enchantment, Integer> enchantements;

	public CustomItem(Material mat, String name, boolean hideEnchants, Map<Enchantment, Integer> enchants,
			String... lore_) {
		type = mat;
		if (lore_ != null)
			lore = Arrays.asList(lore_);
		displayName = name;
		enchantements = enchants;

		is = new ItemStack(mat, 1);
		im = is.getItemMeta();
		im.setDisplayName(displayName);
		im.setLore(lore);
		if (hideEnchants)
			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		if (enchantements.size() > 0) {
			enchantements.forEach((e, l) -> {
				im.addEnchant(e, l, true);
			});
		}
		is.setItemMeta(im);
	}

	public Material getType() {
		return type;
	}

	public List<String> getLore() {
		return lore;
	}

	public ItemStack getItemStack() {
		return is;
	}

	public String getDisplayName() {
		return displayName;
	}

	public static boolean isSameItem(CustomItem ci, ItemStack i) {
		try {
			if (i.hasItemMeta() == false) return false;
			boolean b1 = ci.getDisplayName().equalsIgnoreCase(i.getItemMeta().getDisplayName());
			boolean b2 = ci.getType() == i.getType();
			boolean b3 = ci.getLore() == i.getItemMeta().getLore();
			return b1 && b2 && b3;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
