package eu.mcuniverse.universeapi.rockets;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Planet {

	// PLANET ("WorldName", northSouth, eastWest)

	// northSouth = Size from X to -X
	// eastWest = Size from Z to -Z
	
	MERCURY(1, "Mercury", 100, 100, "eyJ0ZXh0dX"
			+ "JlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3Rl"
			+ "eHR1cmUvOTdmYjI3YTFhMTFiYTQ1NmMwYTEzZjkwOTAxMzY0Y2VkOWVjYWQ4ZmU5Yzk5YjY"
			+ "4MzhmZjVmNGRhYjFmNjE5MSJ9fX0="),
	VENUS(2, "Venus", 100, 100, "eyJ0ZXh0dXJlc"
			+ "yI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1"
			+ "cmUvMGVmMTQ3ZGRjOTA4ZTY4MjVjMjI5OTk3YWE1Mjk3NjFmNTE2OTFhMTFjOTU1MTI5YTIz"
			+ "MzYzMmQ1NTQ4NzVlIn19fQ=="),
	EARTH(3, "Earth", 20000, 10000, "eyJ0ZX"
			+ "h0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV"
			+ "0L3RleHR1cmUvYjFkZDRmZTRhNDI5YWJkNjY1ZGZkYjNlMjEzMjFkNmVmYTZhNmI1ZTdiO"
			+ "TU2ZGI5YzVkNTljOWVmYWIyNSJ9fX0="),
	MOON(4, "Moon", 26000, 12000, "eyJ0ZXh0dXJ"
			+ "lcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM"
			+ "jdhZDk2Y2IyYTRiMTRkMDE2MWYyODc5OTRkM2RiMTg3YWZkODcxNjRmMzk4N2I4N2U3ZjExOGQxZjU4N"
			+ "zkifX19"), // ~ 26000 * 12000
	MARS(5, "Mars", 6142, 6142, "eyJ0ZXh0dX"
			+ "JlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cm"
			+ "UvNzc3ZDYxNmJjNDRhYzliMzczMGZlZDQ3ZjI5YTM3OGY4OGExNjcyOGM2NzA0OGMxYTM4N2QyMjl"
			+ "lMWNiYSJ9fX0="), // 6142 * 6142
	JUPITER(6, "Jupiter", 26000, 13800,
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5"
			+ "lY3JhZnQubmV0L3RleHR1cmUvYjhhYWE4YTM1NjFlODBlZjFmOTU2MWYxNzIxMWU3NzBkZTE4YT"
			+ "lmOThjMjY5MWVjZjlkNjk2NTU5YTFiOTE4YyJ9fX0="), // ~26000 * ~13800
	SATURN(7, "Saturn", 100, 100, "eyJ0ZXh0dXJlc"
			+ "yI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1"
			+ "cmUvNjY1Y2QzYTI0ZjE5MzM3MWVlYmFjOWE3MWM0OGY0MDhhOTM1YWZjNGI0MzVmMWZiN2I5"
			+ "ODQzZTY1ODcyOThmIn19fQ=="),
	URANUS(8, "Uranus", 100, 100, "eyJ0ZXh0dXJl"
			+ "cyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1"
			+ "cmUvYWE2ZGQ3NWY0MWU0MjY4ZTBhMTI2OTA1MDkwN2FhNjc0NmZmZDM3YTRhOTI5ZTczMjUy"
			+ "NDY0MmMzMzZiYyJ9fX0="),
	NEPTUNE(9, "Neptune", 100, 100, "eyJ0ZXh0dXJ"
			+ "lcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleH"
			+ "R1cmUvODdkNjZmOTM5MDlhNmQ0NjQxYzY1MzA4MmUwNDc0OTY5MWRlODJjZjc3MjMyYmQyMG"
			+ "FiMzJhZGY0ZiJ9fX0=");
	
	/**
	 * Value used internally to calculate distance between planets
	 */
	int value;
	String worldName;
	int northSouth;
	int eastWest;
	String skullValue;

//	private Planet(String worldName, int northSouth, int eastWest, String skullValue) {
//		this.worldName = worldName;
//		this.northSouth = northSouth;
//		this.eastWest = eastWest;
//		this.skullValue = skullValue;
//	}

	public static Planet getPlanet(Player p) {
		String world = p.getWorld().getName();
		for (Planet planet : Planet.values()) {
			if (world.equalsIgnoreCase(planet.getWorldName())) {
				try {
					return Planet.valueOf(world.toUpperCase());
				} catch (NullPointerException e) {
					return null;
				}
			}
		}
		return null;
	}
	
	public int getDistance(Planet to) {
		if (to.equals(this)) {
			throw new IllegalArgumentException("Given planet is the same as the object");
		}
		Planet max, min;
		if (this.getValue() > to.getValue()) {
			max = this;
			min = to;
		} else {
			max = to;
			min = this;
		}
		
		return max.getValue() - min.getValue();
	}
	
	public static int getDistance(Planet from, Planet to) {
		if (from.equals(to)) {
			throw new IllegalArgumentException("Both planets are equal!");
		}
		
		Planet max, min;
		if (from.getValue() > to.getValue()) {
			max = from;
			min = to;
		} else {
			max = to;
			min = from;
		}
		
		return max.getValue() - min.getValue();
	}

	public Location getRandomLocation() {
		int sizeX = getNorthSouth() / 2;
		int sizeZ = getEastWest() / 2;

//		int x = ThreadLocalRandom.current().nextInt(sizeX) - (sizeX / 2);
//		int z = ThreadLocalRandom.current().nextInt(sizeZ) - (sizeZ / 2);
		int x = ThreadLocalRandom.current().nextInt(-sizeX, sizeX);
		int z = ThreadLocalRandom.current().nextInt(-sizeZ, sizeZ);
		int y = Bukkit.getWorld(getWorldName()).getHighestBlockYAt(x, z);
		return new Location(Bukkit.getWorld(getWorldName()), x, y, z);
	}

	public static String getLocked() {
		return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5t"
				+ "aW5lY3JhZnQubmV0L3RleHR1cmUvZDhjNTNiY2U4YWU1OGRjNjkyNDkzNDgxOTA"
				+ "5YjcwZTExYWI3ZTk0MjJkOWQ4NzYzNTEyM2QwNzZjNzEzM2UifX19";
	}

}
