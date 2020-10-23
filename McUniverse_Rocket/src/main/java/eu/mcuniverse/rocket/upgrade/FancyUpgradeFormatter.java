package eu.mcuniverse.rocket.upgrade;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.mcuniverse.rocket.rocket.Rocket;
import eu.mcuniverse.rocket.rocket.RocketPart.AirTank;
import eu.mcuniverse.rocket.rocket.RocketPart.Boardcomputer;
import eu.mcuniverse.rocket.rocket.RocketPart.Frostshield;
import eu.mcuniverse.rocket.rocket.RocketPart.FuelTank;
import eu.mcuniverse.rocket.rocket.RocketPart.Heatshield;
import eu.mcuniverse.rocket.rocket.RocketPart.Hullarmor;
import eu.mcuniverse.rocket.rocket.RocketPart.Part;
import eu.mcuniverse.rocket.rocket.RocketPart.Storage;
import eu.mcuniverse.rocket.rocket.RocketPart.WaterTank;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class FancyUpgradeFormatter {

	// \u00D7 = multiplication sign

	public ObjectArrayList<String> getLore(@NonNull Object2IntMap<ItemStack> map) {
		ObjectArrayList<String> ret = new ObjectArrayList<String>();
		try {
			map.forEach((item, amount) -> {
				if (isCustomItem(item)) {
					ItemMeta meta = item.getItemMeta();
					ret.add(format(amount, ChatColor.GOLD + meta.getDisplayName()));
//				ret.add(ChatColor.GRAY + " - " + ChatColor.YELLOW + amount + ChatColor.GRAY + "\u00D7 " + ChatColor.YELLOW + meta.getDisplayName());
				} else {
					ret.add(format(amount, item.getType()));
				}
			});
		} finally {

		}
		return ret;
	}

	public ObjectArrayList<String> getPlanetLore(@NonNull ObjectOpenHashSet<Part> parts) {
		ObjectArrayList<String> result = new ObjectArrayList<String>();
		parts.stream()
				.map(p -> ChatColor.YELLOW + "" + p.getName())
				.forEach(result::add);
		return result;
	}

	public ObjectArrayList<String> getPlanetLoreColor(@NonNull ObjectCollection<Part> parts, Rocket rocket) {
		ObjectArrayList<String> result = new ObjectArrayList<>();
		for (Part part : parts) {
			String name = WordUtils.capitalize(part.getClass().toString().split("\\$")[1].toLowerCase()) + " ";
			if (part instanceof AirTank) {
				result.add((rocket.getAirTank().isAtLeast(part) ? ChatColor.GREEN : ChatColor.RED) + name + part.getName());
			} else if (part instanceof WaterTank) {
				result.add((rocket.getWaterTank().isAtLeast(part) ? ChatColor.GREEN : ChatColor.RED) + name + part.getName());
			} else if (part instanceof Hullarmor) {
				result.add((rocket.getHullarmor().isAtLeast(part) ? ChatColor.GREEN : ChatColor.RED) + name + part.getName());
			} else if (part instanceof Frostshield) {
				result.add((rocket.getFrostshield().isAtLeast(part) ? ChatColor.GREEN : ChatColor.RED) + name + part.getName());
			} else if (part instanceof Heatshield) {
				result.add((rocket.getHeatshield().isAtLeast(part) ? ChatColor.GREEN : ChatColor.RED) + name + part.getName());
			} else if (part instanceof Storage) {
				result.add((rocket.getStorage().isAtLeast(part) ? ChatColor.GREEN : ChatColor.RED) + name + part.getName());
			} else if (part instanceof FuelTank) {
				result.add((rocket.getFuelTank().isAtLeast(part) ? ChatColor.GREEN : ChatColor.RED) + name + part.getName());
			} else if (part instanceof Boardcomputer) {
				result
						.add((rocket.getBoardcomputer().isAtLeast(part) ? ChatColor.GREEN : ChatColor.RED) + name + part.getName());
			}
		}
		return result;
	}

	private boolean isCustomItem(ItemStack item) {
		return item.hasItemMeta();
	}

	private String format(int amount, String name) {
		return ChatColor.GRAY + " - " + ChatColor.YELLOW + amount + ChatColor.GRAY + "\u00D7 " + ChatColor.YELLOW + name;
	}

	private String format(int amount, Material material) {
		String name = material.name().toLowerCase();
		name = name.replace('_', ' ');
		return format(amount, WordUtils.capitalize(name));
	}

}
