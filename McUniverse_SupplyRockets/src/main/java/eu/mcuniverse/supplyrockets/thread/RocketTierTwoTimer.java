package eu.mcuniverse.supplyrockets.thread;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import eu.mcuniverse.supplyrockets.Core;
import eu.mcuniverse.supplyrockets.rockets.SRocketTier2;
import eu.mcuniverse.universeapi.rockets.Planet;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

@Getter
public class RocketTierTwoTimer extends BukkitRunnable {

	private Object2ObjectOpenHashMap<Planet, SRocketTier2> rocket = new Object2ObjectOpenHashMap<Planet, SRocketTier2>();
	private Planet planet;

	public RocketTierTwoTimer(Planet planet) {
		this.planet = planet;
	}

	public void start() {
		if (rocket.get(planet) == null) {
			rocket.put(planet, new SRocketTier2(planet));
		}
		runTaskTimer(Core.getInstance(), 0L, TimeUnit.MINUTES.toSeconds(60) * 20L);
	}

	public void stop() {
		this.cancel();
	}

	public void run() {
//		Bukkit.broadcastMessage(new Time(System.currentTimeMillis()) + " Supply rocket Tier II spawned on " + planet.getWorldName());
		Bukkit.getOnlinePlayers().forEach(player -> {
			if (player.getWorld().getName().equalsIgnoreCase(planet.getWorldName())) {
				player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "(!)" + ChatColor.DARK_GREEN
						+ " A Supply Rocket landed on this planet. Have fun finding it!");
			}
		});
		rocket.get(planet).spawn();
//		Bukkit.broadcastMessage(rocket.get(planet).getLocation().toString());

		new DestroyTimer(rocket.get(planet).getArmorStand()).runTaskLater(Core.getInstance(), 60 * 60 * 20L);
	}

}
