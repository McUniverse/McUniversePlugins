package eu.mcuniverse.weapons.weapon.util;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

import eu.mcuniverse.weapons.main.Core;
import eu.mcuniverse.weapons.util.ParticleUtil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ShootingManager {

	public void shootRayTrace(Player player, Weapon weapon) {
		World w = player.getWorld();
		Location loc = player.getEyeLocation();
		Location loc2 = null;
		Predicate<Entity> filter = e -> e.getUniqueId() != player.getUniqueId();
		RayTraceResult result = w.rayTrace(loc, loc.getDirection(), weapon.getRange(), FluidCollisionMode.NEVER, true, 0,
				filter);
		if (result == null) {
			loc2 = player.getEyeLocation().add(loc.getDirection().multiply(weapon.getRange()));
		} else {
			if (result.getHitPosition() != null) {
				loc2 = result.getHitPosition().toLocation(w);
			}
			if (result.getHitEntity() != null) {
				if (result.getHitEntity() instanceof LivingEntity) {
					LivingEntity le = (LivingEntity) result.getHitEntity();
					le.damage(weapon.getDamage(), player);
					if (result.getHitPosition() != null) {
						double rayY = result.getHitPosition().getY();
						double entityY = result.getHitEntity().getLocation().getY();
						boolean headshot = rayY - entityY > 1.4D;
						if (headshot) {
							player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 1f, 2f);
						}
					}
				}
			}
		}
		playShootingSound(player, weapon);
		loc = player.getEyeLocation().add(player.getEyeLocation().getDirection());
		ParticleUtil.drawLine(loc, loc2, 0.5, weapon.getParticleEffect().getParticle(),
				weapon.getParticleEffect().getOffset(), weapon.getParticleEffect().getCount(),
				weapon.getParticleEffect().getSpeed(), weapon.getParticleEffect().getData());
	}

	public void shootWithSnowball(Player player, Weapon weapon) {
		ShootingManager.shootWithSnowballWithVelocity(player, weapon, player.getEyeLocation().getDirection().multiply(2),
				true);
	}

	public void shoowWithSNowballWithLocationAndVelocity(Player player, Weapon weapon, Vector vector, Location location,
			boolean sound) {
		Snowball s = (Snowball) location.getWorld().spawnEntity(location, EntityType.SNOWBALL);
		s.setCustomName(weapon.getDamage() + "");
		s.setCustomNameVisible(true);
		s.setMetadata("weapon", new FixedMetadataValue(Core.getInstance(), weapon.getWeaponType().toString()));
		s.setVelocity(vector);
		s.setShooter(player);

		playShootingSound(player, weapon);

		// Destroy snowball
		PacketContainer packet = Core.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_DESTROY);
		packet.getModifier().writeDefaults();
		packet.getIntegerArrays().write(0, new int[] { s.getEntityId() });

		Bukkit.getOnlinePlayers().forEach(all -> {
			try {
				Core.getProtocolManager().sendServerPacket(all, packet);
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		});

		long millis = System.currentTimeMillis();

		new BukkitRunnable() {
			Location from = s.getLocation().add(location.getDirection());

			@Override
			public void run() {
				Location to = s.getLocation();
				ParticleUtil.drawLine(from, to, 0.1, weapon.getParticleEffect().getParticle(),
						weapon.getParticleEffect().getOffset(), weapon.getParticleEffect().getCount(),
						weapon.getParticleEffect().getSpeed(), weapon.getParticleEffect().getData());

				from = to.clone();
				if (s.isOnGround() || System.currentTimeMillis() - millis >= 1000 * 10 || s.isDead()) {
					cancel();
				}
			}
		}.runTaskTimerAsynchronously(Core.getInstance(), 1L, 1L);
	}

	public void shootWithSnowballWithVelocity(Player player, Weapon weapon, Vector vector, boolean sound) {
		shoowWithSNowballWithLocationAndVelocity(player, weapon, vector, player.getEyeLocation(), sound);
	}

	public void shootShotgun(Player player, Weapon weapon) {
		Location location = player.getEyeLocation().clone();
		Vector dir = location.getDirection().clone();

		playShootingSound(player, weapon);
		for (int i = 0; i < 8; i++) {
			double x = ThreadLocalRandom.current().nextDouble(-0.5, 0.5);
			double y = ThreadLocalRandom.current().nextDouble(-0.5, 0.5);
			double z = ThreadLocalRandom.current().nextDouble(-0.5, 0.5);
			Vector v = dir.clone().add(new Vector(x, y, z));
//			Location loc = location.clone().add(x, y, z);
			Location loc = location.clone();
			ShootingManager.shoowWithSNowballWithLocationAndVelocity(player, weapon, v, loc, false);
		}
	}

	public void playShootingSound(Player player, Weapon weapon) {
		Sound sound = weapon.getSoundEffect().getSound();
		float pitch = weapon.getSoundEffect().getPitch();
		float volume = weapon.getSoundEffect().getVolume();
		player.playSound(player.getLocation(), sound, SoundCategory.MASTER, volume, pitch);
	}

}
