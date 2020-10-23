package eu.mcuniverse.rocket.data;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Maps;

import eu.mcuniverse.rocket.rocket.InRocketManager;
import eu.mcuniverse.universeapi.rockets.Planet;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class Variables {

	//TODO REMOVE STATIC
	
	/**
	 * Get the name of the ArmorStand which represents the rocket
	 */
	public static final String ARMOR_STAND_NAME = ChatColor.MAGIC + "Rocket";

	/**
	 * This map stores all players (their name) with their previous location who
	 * are in a rocket
	 */
	public static final Map<String, Location> inRocket = Maps.newHashMap();

	/**
	 * Temporary map to store selected planet in launch menu
	 */
	public static final Map<String, Planet> selectedPlanet = Maps.newHashMap();

	/**
	 * This map stores all players (their name) with a {@link InRocketManager.Teleporter} who
	 * are in a rocket
	 */
	public static final Map<String, InRocketManager.Teleporter> rocketTeleporter = Maps.newHashMap();
	
	/**
	 * This map stores all players (their name) with an ItemStack[] who are
	 * in a rocket;
	 */
	public static final Map<String, ItemStack[]> inventories = Maps.newHashMap();
	
	/**
	 * This map stores all players (their name) with an {@link Location} who are in a rocket
	 * to store the reference location
	 */
	public static final Map<String, Location> references = Maps.newHashMap();
	
	/**
	 * This map stores all players (their name) with an {@link Location} who are in a rocket
	 * to store the location to look at
	 */
	public static final Map<String, Location> toLook = Maps.newHashMap();
	
	public static long PERIOD = 1;
	public static int RADIUS = 5;
	public static double DELTA = 0.05;
	
}
