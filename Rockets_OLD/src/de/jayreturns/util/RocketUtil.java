package de.jayreturns.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import de.jayreturns.main.Main;
import de.jayreturns.planets.Planet;
import de.jayreturns.rocket.Rocket;

public class RocketUtil {

	private static HashMap<Integer, Integer> sizes = new HashMap<Integer, Integer>(); // Map<Tier, InvSize>

	static {
		sizes.put(1, 3);
		sizes.put(2, 5);
		sizes.put(3, 7);
		sizes.put(4, 9);
		sizes.put(5, 16);
		sizes.put(6, 21); // TODO: Move to function getInvSize
	}

	public static List<Integer> getSlotIndecies(int tier) {
		switch (tier) {
		case 1:
			return Arrays.asList(12, 13, 14);
		case 2:
			return Arrays.asList(11, 12, 13, 14, 15);
		case 3:
			return Arrays.asList(10, 11, 12, 13, 14, 15, 16);
		case 4:
			return Arrays.asList(3, 4, 5, 12, 13, 14, 21, 22, 23);
		case 5:
			return Arrays.asList(2, 3, 4, 5, 6, 11, 12, 13, 14, 15, 20, 21, 22, 23, 24);
		case 6:
			return Arrays.asList(1, 2, 3, 4, 5, 6, 7, 10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25);
		default:
			throw new IllegalArgumentException("0 < Tier < 7");
		}
	}

	public static int getFuelInvSize(int tier) {
		switch (tier) {
		case 1:
			return 9;
		case 2:
			return 9;
		case 3:
			return 18;
		case 4:
			return 18;
		case 5:
			return 27;
		case 6:
			return 27;
		default:
			throw new IllegalArgumentException("0 < Tier < 7");
		}
	}

	public static int getMaxFuel(int tier) {
		return getFuelInvSize(tier);
	}

	/**
	 * 
	 * @param The player p
	 * @return Returns if the player can open the inventory
	 */
	public static boolean testDistanceToRocket(Player p) {
		Rocket r = Rocket.getRocket(p);
		try {
			return !p.getLocation().getWorld().getName().equalsIgnoreCase(r.getLocation().getWorld().getName())
					|| p.getLocation().distance(r.getLocation()) > Main.configManager.getConfig()
							.getDouble("distanceForInventory");
		} catch (NullPointerException e) {
			p.sendMessage(Messages.error + "An error occured! " + e.getMessage());
			return false;
		}
	}

	public static int getInvSize(int tier) {
		if (tier < 1 || tier > 6)
			throw new IllegalArgumentException("Tier not in range. 0 < tier < 7");
		return sizes.get(tier);
	}

	public static boolean isFloorFlat(Location loc, int tier) {
		int size = Main.configManager.getConfig().getInt("RocketTier" + tier + ".size");
		for (int x = -size; x <= size; x++) {
			for (int z = -size; z <= size; z++) {
				Material mat = loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY(), loc.getBlockZ() + z).getType();
				if (!mat.isSolid()) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean canRocketBePlaced(Location loc, int tier) {
		int size = Main.configManager.getConfig().getInt("RocketTier" + tier + ".size");
		for (int x = -size; x <= size; x++) {
			for (int y = 1; y < 20; y++) {
				for (int z = -size; z <= size; z++) {
					if (loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z)
							.getType() != Material.AIR) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public static List<Planet> accesiblePlanets(int tier) {
		switch (tier) {
		case 1:
			return Arrays.asList(Planet.EARTH, Planet.MOON);
		case 2:
			return union(accesiblePlanets(tier -1), Arrays.asList(Planet.MARS));
		case 3:
			return union(accesiblePlanets(tier -1), Arrays.asList(Planet.JUPITER));
		case 4:
			return union(accesiblePlanets(tier -1), Arrays.asList(Planet.SATURN));
		case 5:
			return union(accesiblePlanets(tier - 1), Arrays.asList(Planet.VENUS, Planet.URANUS));
		case 6:
			return union(accesiblePlanets(tier -1), Arrays.asList(Planet.MERCURY, Planet.NEPTUNE));
		default:
			return null;
		}
	}
	
	public static <T> List<T> union(List<T> list1, List<T> list2) {
		List<T> newList = new ArrayList<T>(list1);
		newList.addAll(list2);
		return newList;
	}
	
}
