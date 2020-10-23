package eu.mcuniverse.supplyrockets.thread;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import eu.mcuniverse.supplyrockets.Core;
import eu.mcuniverse.supplyrockets.chances.MeteoriteSize;
import eu.mcuniverse.supplyrockets.meteorite.IMeteorite;
import eu.mcuniverse.supplyrockets.utils.dynmap.DynmapManager;
import eu.mcuniverse.universeapi.rockets.Planet;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.AccessLevel;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

@Getter
public class MeteoriteTimer extends BukkitRunnable {

	private Object2ObjectOpenHashMap<Planet, IMeteorite> meteorites = new Object2ObjectOpenHashMap<Planet, IMeteorite>();
	private Planet planet;
	private Class<? extends IMeteorite> clazz;
	@Getter(value = AccessLevel.NONE)
	private int minuteCount = 0;

	public MeteoriteTimer(Planet planet, MeteoriteSize size) {
		this.planet = planet;
		this.clazz = size.getClazz();
	}

	public void start() {
		if (meteorites.get(planet) == null) {
			try {
				meteorites.put(planet, clazz.getConstructor(Planet.class).newInstance(planet));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				Bukkit.getLogger().severe("MeteoriteTimer.start(): " + e.toString());
			}
		}
		runTaskTimer(Core.getInstance(), 0L, TimeUnit.MINUTES.toSeconds(60) * 20L);
	}

	public void stop() {
		this.cancel();
	}

	/*
	 * -1h: Dynmap 0h: Sphere animation +30min: remove Dynmap
	 * 
	 * every 15min: Chat announcement
	 * 
	 */

	public void run() {

		if (minuteCount == 0) {
			// Add to dynmap#
			DynmapManager.getInstance().createMarker(meteorites.get(planet));
		} else if (minuteCount == 30) {
			// Remove from dynmap
			DynmapManager.getInstance().deleteMarker(meteorites.get(planet));
		}

		if (IntStream.of(15, 30, 45)/*.map(x -> x + 60)*/.map(this::addOneHour).anyMatch(x -> x == minuteCount)) { // Add 60 (min) to take affect after "touchdown"
			// Announce in chat
			Bukkit.broadcastMessage(ChatColor.GOLD + "A meteorite will land in " + minuteCount + " minutes");
		}

		Bukkit.getOnlinePlayers().forEach(player -> {
			if (player.getWorld().getName().equalsIgnoreCase(planet.getWorldName())) {
				player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "(!) " + ChatColor.DARK_GREEN
						+ meteorites.get(planet).getMeteoriteSize().getName() + " has hit your planet! Go find it.");
				meteorites.get(planet).addToDynmap();
			}
		});
		meteorites.get(planet).spawn();

		minuteCount++;

//		new DestroyTimer(meteorites.get(planet).getArmorStand()).runTaskLater(Core.getInstance(), 5 * 20L);
	}
	
	private int addOneHour(int in) {
		return in + 60;
	}

}
