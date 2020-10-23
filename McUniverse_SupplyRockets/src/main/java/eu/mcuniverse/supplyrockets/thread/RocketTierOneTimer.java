package eu.mcuniverse.supplyrockets.thread;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import eu.mcuniverse.supplyrockets.Core;
import eu.mcuniverse.supplyrockets.rockets.SRocketTier1;
import eu.mcuniverse.universeapi.rockets.Planet;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

@Getter
public class RocketTierOneTimer extends BukkitRunnable {

//	@Setter private SRocketTier1 rocket;
	private Object2ObjectOpenHashMap<Planet, SRocketTier1> rocket = new Object2ObjectOpenHashMap<Planet, SRocketTier1>();
	private Planet planet;

	public RocketTierOneTimer(Planet planet) {
		this.planet = planet;
	}

	public void start() {
		if (rocket.get(planet) == null) {
//			setRocket(new SRocketTier1(Bukkit.getPlayer("JayReturns").getLocation()));
			rocket.put(planet, new SRocketTier1(planet));
		}
		runTaskTimer(Core.getInstance(), 0L, TimeUnit.MINUTES.toSeconds(15) * 20L);
//			this.thread.start();
	}

	public void stop() {
		this.cancel();
	}

	public void run() {
//		Bukkit.broadcastMessage(
//				new Time(System.currentTimeMillis()) + " Supply Rocket Tier I spawned on " + planet.getWorldName());
		Bukkit.getOnlinePlayers().forEach(player -> {
			if (player.getWorld().getName().equalsIgnoreCase(planet.getWorldName())) {
				// &a&l(!) &2A Supply Rocket landet on this Planet. Have fun finding it!
				player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "(!)" + ChatColor.DARK_GREEN
						+ " A Supply Rocket landed on this planet. Have fun finding it!");
			}
		});
		rocket.get(planet).spawn();

		new DestroyTimer(rocket.get(planet).getArmorStand()).runTaskLater(Core.getInstance(), 60 * 60 * 20);

	}

}
