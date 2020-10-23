package de.jayreturns.items;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import de.jayreturns.util.ItemUtil;

public class Items {

	public static ItemStack getOil() {
		ItemStack item = ItemUtil.createItem(Material.POTION, "§8§oOil", "§7Use this to make rocketfuel");
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		meta.setColor(Color.fromBGR(0, 0, 0));
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getRocketFuel() {
		ItemStack item = ItemUtil.createItem(Material.POTION, "§e§oRocketfuel", "§7Use this to fill your rocket");
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		meta.setColor(Color.fromRGB(16443433));
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getWaterBottle() {
		ItemStack item = new ItemStack(Material.POTION);
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		meta.clearCustomEffects();
		meta.setBasePotionData(new PotionData(PotionType.WATER));
		item.setItemMeta(meta);
		return item;
	}
	
}