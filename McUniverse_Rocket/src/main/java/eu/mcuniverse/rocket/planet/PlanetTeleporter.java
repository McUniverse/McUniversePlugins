package eu.mcuniverse.rocket.planet;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import eu.mcuniverse.rocket.main.Core;
import eu.mcuniverse.rocket.rocket.Rocket;
import eu.mcuniverse.rocket.rocket.RocketManager;
import eu.mcuniverse.universeapi.api.UniverseAPI;
import eu.mcuniverse.universeapi.rockets.Planet;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PlanetTeleporter {

	public boolean teleportToPlanet(Player player, Planet planet, boolean toFactionBase) {
		if (!RocketManager.hasRocket(player)) {
			throw new IllegalArgumentException("Player " + player.getName() + " has no rocket!");
		}
		Rocket r = RocketManager.getRocket(player);
		Location loc;
		if (toFactionBase) {
			try {
				loc = UniverseAPI.getFactionManager().getLandingLocation(UniverseAPI.getFactionUtil().getFactionTag(player), planet);
			} catch (Exception e) {
				Core.getInstance().getLogger().severe("The player" + player.getName() + " tried to fly to another Planet! An exception occured: ");
				e.printStackTrace();
				Core.getInstance().getLogger().log(Level.SEVERE, "Exception: ", e);
				loc = findNewLocation(player, planet);
				player.sendMessage(UniverseAPI.getError() + "An error occured while finding your faction's landing location. "
						+ "You'll be send to a random location insdead");
			}
		} else {
			loc = findNewLocation(player, planet);
		}
		r.teleport(loc);
		return player.teleport(loc);
	}
	
	protected Location findNewLocation(Player player, Planet planet) {
		if (Bukkit.getWorld(planet.getWorldName()) == null) {
			throw new IllegalArgumentException("World " + planet.getWorldName() + " does not exist");
		}
		World w = Bukkit.getWorld(planet.getWorldName());
		
		int sizeX = planet.getNorthSouth() / 2;
		int sizeY = planet.getEastWest() / 2;
		
		int x = ThreadLocalRandom.current().nextInt(sizeX) - (sizeX / 2);
		int z = ThreadLocalRandom.current().nextInt(sizeY) - (sizeY / 2);
		int y = w.getHighestBlockYAt(x, z);
		Location loc = new Location(w, x, y, z);
		loc = getTopBlock(loc);
		return loc;
	}
	
	/**
	 * Used for the /f setrocketlanding
	 * @param player
	 * @param location
	 * @return
	 */
	public boolean teleportToPlanet(Player player, Location location) {
		if (!RocketManager.hasRocket(player)) {
			throw new IllegalArgumentException("Player " + player.getName() + " has no rocket!");
		}
		
		Rocket r = RocketManager.getRocket(player);
		Location loc = location.getWorld().getHighestBlockAt(location.getBlockX(), location.getBlockZ()).getLocation();
		r.teleport(loc);
		return player.teleport(loc);
	}
	
	protected Location getTopBlock(Location loc) {
		return loc.getWorld().getHighestBlockAt(loc).getLocation();
	}
	
}
