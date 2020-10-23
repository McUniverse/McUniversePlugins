package de.jayreturns.rocket;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import de.jayreturns.planets.Planet;
import de.jayreturns.util.RocketUtil;

public class RocketTeleporter {

	public static Location findNewLocation(Player p, Planet planet) {
		if (Bukkit.getWorld(planet.getWorldName()) == null)
			throw new NullPointerException("World " + planet.getWorldName() + " does not exist!");
		World w = Bukkit.getWorld(planet.getWorldName());
		boolean suitable = false;
		Location loc = null;
		int i = 0; 
		
		while (!suitable) {
			int sizeX = planet.getNorthSouth() / 2;
			int sizeY = planet.getEastWest() / 2;
	
			int x = new Random().nextInt(sizeX) - (sizeX / 2);
			int y = new Random().nextInt(sizeY) - (sizeY / 2);
	
			loc = new Location(w, x, 0, y);
			loc = getTopBlock(loc);
			suitable = RocketUtil.isFloorFlat(loc, Rocket.getRocket(p).getTier());
			
			i++;
			if (i > 20) {
				p.sendMessage("No Location found!");
				break;
			}
		}
		return loc;
	}
	
	public static void teleportToNewPlanet(Player p, Planet planet) {
		Location loc = findNewLocation(p, planet);
		if (loc == null) {
			teleportToNewPlanet(p, planet);
			return;
		}
		Rocket.deleteRocket(p);
		p.teleport(loc);
		RocketPlaceListener.spawnRocket(Rocket.getRocket(p).getTier(), p, p.getLocation());
	}

	private static Location getTopBlock(Location loc) {
		return loc.getWorld().getHighestBlockAt(loc).getLocation();
	}
	
}