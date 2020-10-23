package eu.mcuniverse.supplyrockets.boss;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import eu.mcuniverse.supplyrockets.Core;
import eu.mcuniverse.supplyrockets.customentity.EntityManager;
import lombok.NonNull;

public class BossListener implements Listener {

	private static final double MAX_DISTANCE = 30;
	
	public BossListener(final Core plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onAttack(EntityDamageByEntityEvent e) {
		if (EntityManager.hasDoubleDamage(e.getDamager())) {
			e.setDamage(e.getDamage() * 2);
		}
	}

	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		if (EntityManager.isBoss(e.getEntity())) {
			e.setDroppedExp(e.getDroppedExp() * 100);
			e.getDrops().add(new ItemStack(Material.DIAMOND));
		}
	}

	@EventHandler
	public void onTarget(EntityTargetEvent e) {
		if (e.getTarget() == null) {
			return;
		}

		if (EntityManager.isBoss(e.getTarget())) {
			e.setCancelled(true);
		}
		if (e.getTarget().getType() != EntityType.PLAYER) {
			e.setCancelled(true);
		}
		// Cancel target if more than MAX_DISTANCE blocks (radius)
		if (testDistance(EntityManager.getMeteoriteLocation(e.getEntity()), e.getEntity().getLocation(), MAX_DISTANCE)) {
			e.setCancelled(true);
		}
	}
	
	private boolean testDistance(Location from, @NonNull Location to, double threshold) {
		AtomicBoolean result = new AtomicBoolean(false);
		Optional.ofNullable(from)
		.map(to::distanceSquared)
		.ifPresent(distance -> {
			if (distance > threshold) {
				result.set(true);
			}
		});
		return result.get();
	}

/*	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		new BukkitRunnable() {

			@Override
			public void run() {
				if (e.getDamager() instanceof Player) {
					Player p = (Player) e.getDamager();
					if (e.getEntity() instanceof Attributable && e.getEntity() instanceof Damageable) {
						Attributable attributable = (Attributable) e.getEntity();
						Damageable damageable = (Damageable) e.getEntity();
						double maxHealth = attributable.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
						double current = damageable.getHealth();
						if (maxHealth > 20) {
							TextComponent currentMsg = new TextComponent(roundToHalf(current) + "");
							currentMsg.setColor(ChatColor.RED);
							TextComponent slashMsg = new TextComponent("/");
							slashMsg.setColor(ChatColor.DARK_GRAY);
							TextComponent maxMsg = new TextComponent(((int) maxHealth) + "");
							maxMsg.setColor(ChatColor.DARK_RED);
							currentMsg.addExtra(slashMsg);
							currentMsg.addExtra(maxMsg);
							p.spigot().sendMessage(ChatMessageType.ACTION_BAR, currentMsg);
						} else {
							String msg = "";
							for (double d = 0; d < current; d++) {
								msg += ChatColor.DARK_RED + "\u2764";
							}
							for (double d = 0; d < maxHealth - current; d++) {
								msg += ChatColor.GRAY + "\u2764";
							}
							p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));
							p.sendMessage(current + " / " + maxHealth);
						}
					}
				}
			}
		}.runTaskLater(Core.getInstance(), 1L);

	}*/

	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent e) {
		if (e.getEntity().getShooter() == null) {
			return;
		}
		Entity shooter = (Entity) e.getEntity().getShooter();
		if (EntityManager.hasArrowParticles(shooter)) {
			Particle particle = EntityManager.getArrowParticle(shooter);
			new BukkitRunnable() {

				@Override
				public void run() {
					e.getEntity().getWorld().spawnParticle(particle, e.getEntity().getLocation(), 5);
					if (e.getEntity() == null || e.getEntity().isOnGround() || e.getEntity().isDead()) {
						this.cancel();
					}
				}
			}.runTaskTimer(Core.getInstance(), 0L, 5L);
		}
	}
	
	private double roundToHalf(double d) {
		return Math.round(d * 2) / 2.0;
	}

}
