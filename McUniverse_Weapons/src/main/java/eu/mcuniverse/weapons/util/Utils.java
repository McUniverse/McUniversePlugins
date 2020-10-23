package eu.mcuniverse.weapons.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Utils {

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
