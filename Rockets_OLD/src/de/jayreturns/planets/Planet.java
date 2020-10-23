package de.jayreturns.planets;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public enum Planet {

	// PLANET ("WorldName", northSouth, eastWest)
	
	// northSouth = Size from X to -X
	// eastWest = Size from Z to -Z
	
	MERCURY("", -1, -1),
	VENUS("", -1, -1),
	EARTH("", -1, -1),
	MOON("Moon", 26000, 12000), // ~ 26000 * 12000
	MARS("NewMarsMap", 6142, 6142), // 6142 * 6142
	JUPITER("", -1, -1),
	SATURN("", -1, -1),
	URANUS("", -1, -1),
	NEPTUNE("", -1, -1);
	
	String worldName;
	int northSouth;
	int eastWest;
	
	private Planet(String worldName, int northSouth, int eastWest) {
		this.worldName = worldName;
		this.northSouth = northSouth;
		this.eastWest = eastWest;
	}
	
	public int getNorthSouth() {
		return northSouth;
	}
	
	public int getEastWest() {
		return eastWest;
	}
	
	public String getWorldName() {
		return worldName;
	}
	
	public static Planet getPlanet(Player p) {
		String world = p.getWorld().getName();
		for (Planet planet : Planet.values()) {
			if (world.equalsIgnoreCase(planet.getWorldName())) {
				return Planet.valueOf(world);
			}
		}
		return null;
	}
	
	public Location getRandomLocation() {
		int sizeX = getNorthSouth() / 2;
		int sizeZ = getEastWest() / 2;

		int x = new Random().nextInt(sizeX) - (sizeX / 2);
		int z = new Random().nextInt(sizeZ) - (sizeZ / 2);
		return new Location(Bukkit.getWorld(getWorldName()), x, 0, z);
	}
	
}
