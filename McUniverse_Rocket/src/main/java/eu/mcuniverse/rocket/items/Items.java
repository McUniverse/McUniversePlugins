package eu.mcuniverse.rocket.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import eu.mcuniverse.rocket.rocket.RocketPart;
import eu.mcuniverse.rocket.util.ItemUtil;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class Items {

	public ItemStack getOil() {
		ItemStack item = ItemUtil.createItem(Material.POTION, "§8§oOil", "§7Use this to make rocketfuel");
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		meta.setColor(Color.fromBGR(0, 0, 0));
		item.setItemMeta(meta);
		return item;
	}

	public ItemStack getRocketFuel() {
		ItemStack item = ItemUtil.createItem(Material.POTION, "§e§oRocketfuel", "§7Use this to fill your rocket");
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		meta.setColor(Color.fromRGB(16443433));
		item.setItemMeta(meta);
		return item;
	}

	public ItemStack getWaterBottle() {
		ItemStack item = new ItemStack(Material.POTION);
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		meta.clearCustomEffects();
		meta.setBasePotionData(new PotionData(PotionType.WATER));
		item.setItemMeta(meta);
		return item;
	}

	public ItemStack getBaseRocketItem() {
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.AQUA + "Air tank: " + ChatColor.YELLOW + RocketPart.AirTank.NONE.toString());
		lore.add(ChatColor.AQUA + "Water tank: " + ChatColor.YELLOW + RocketPart.WaterTank.NONE.toString());
		lore.add(ChatColor.AQUA + "Hull armor: " + ChatColor.YELLOW + RocketPart.Hullarmor.NONE.toString());
		lore.add(ChatColor.AQUA + "Heat shield: " + ChatColor.YELLOW + RocketPart.Heatshield.NONE.toString());
		lore.add(ChatColor.AQUA + "Frost shield: " + ChatColor.YELLOW + RocketPart.Frostshield.NONE.toString());
//		lore.add(ChatColor.AQUA + "Storage: " + ChatColor.YELLOW + RocketPart.Storage.NONE.toString());
		lore.add(ChatColor.AQUA + "Storage: " + ChatColor.YELLOW + RocketPart.Storage.NONE.toString());
		lore.add(ChatColor.AQUA + "Boardcomputer: " + ChatColor.YELLOW + RocketPart.Boardcomputer.NONE.toString());
		lore.add(ChatColor.AQUA + "Fuel tank: " + ChatColor.YELLOW + RocketPart.FuelTank.NONE.toString());
//		lore.add(ChatColor.AQUA + "Thruster: " + ChatColor.YELLOW + RocketPart.Thruster.NONE.toString());
//		lore.add(ChatColor.AQUA + "Solar panel: " + ChatColor.YELLOW + RocketPart.Solarpanel.NONE.toString());
		ItemStack item = ItemUtil.createEnchantedItem(Material.FIREWORK_ROCKET, ChatColor.GREEN + "Rocket", lore.toArray(new String[0]));
		return item;
	}
	
	

}