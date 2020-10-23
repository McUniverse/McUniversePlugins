package eu.mcuniverse.rocket.util;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.mcuniverse.universeapi.serialization.JsonItemStack;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MiscUtil {

	public ObjectArrayList<ItemStack> convertStringToItemStack(List<String> strings) {
		ObjectArrayList<ItemStack> result = new ObjectArrayList<ItemStack>();
		for (String string : strings) {
			if (string.isBlank() || string.isEmpty()) {
				continue;
			}
			try {
				result.add(JsonItemStack.fromJson(string));
			} catch (Exception e) {
				continue;
			}
		}
		return result;
	}

	public enum Hand {
		MAIN, OFF, NONE;
	}

	public static Hand getHand(Player p, ItemStack item) {
		if (p.getInventory().getItemInMainHand().isSimilar(item))
			return Hand.MAIN;
		else if (p.getInventory().getItemInOffHand().isSimilar(item))
			return Hand.OFF;
		return Hand.NONE;
	}
	
}
