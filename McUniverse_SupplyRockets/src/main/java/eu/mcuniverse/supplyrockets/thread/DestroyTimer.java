package eu.mcuniverse.supplyrockets.thread;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import eu.mcuniverse.supplyrockets.Core;
import lombok.RequiredArgsConstructor;

//@AllArgsConstructor
@RequiredArgsConstructor
public class DestroyTimer extends BukkitRunnable {

	final public Entity entity;
	private String message = Core.getDespawnMessage();

	@Override
	public void run() {
		if (entity != null) {
			if (!entity.isDead()) {
//				entity.remove(); // Doesn't work?!

				try {
					Bukkit.getEntity(entity.getUniqueId()).remove();
				} catch (Exception e) {
				}
				if (entity instanceof Damageable) {
					((Damageable) entity).setHealth(0);
					((Damageable) entity).damage(Double.MAX_VALUE);
				}

				Location loc = entity.getLocation();
				Bukkit.getOnlinePlayers().forEach(player -> {
					if (player.getWorld().getName().equals(loc.getWorld().getName())) {
						player.sendMessage(String.format(message, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
					}
				});
//						Bukkit.broadcastMessage(String.format(message, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));

				this.cancel();
			}
		}
	}

}
