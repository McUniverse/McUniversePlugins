package eu.mcuniverse.supplyrockets.boss;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.WitherSkeleton;

import eu.mcuniverse.supplyrockets.chances.MeteoriteSize;
import eu.mcuniverse.supplyrockets.customentity.EntityManager;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class BossManger {

	public Entity[] spawnMob(Location loc, MeteoriteSize size) {
		int len = size.getRangeCount() + size.getMeleeCount();
		Entity[] result = new Entity[len];
		
		int index = 0;
		for (int i = 0; i < size.getRangeCount(); i++) {
			Skeleton skeleton = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
			skeleton.setCustomName(ChatColor.RED + "Ranged Boss");
			skeleton.setCustomNameVisible(true);
			skeleton.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40.0d);
			EntityManager.setDoubleDamage(skeleton);
			EntityManager.setArrowParticle(skeleton, Particle.BARRIER);
			result[index] = skeleton;
			index++;
		}
		
		for (int i = 0; i < size.getMeleeCount(); i++) {
			WitherSkeleton witherSkeleton = (WitherSkeleton) loc.getWorld().spawnEntity(loc, EntityType.WITHER_SKELETON);
			witherSkeleton.setCustomName(ChatColor.DARK_RED + "Melee Boss");
			witherSkeleton.setCustomNameVisible(true);
			witherSkeleton.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40.0d);
			result[index] = witherSkeleton;
			index++;
		}
		
		Arrays.stream(result).forEach(boss -> {
			EntityManager.setBoss(boss);
			if (boss instanceof LivingEntity) {
				((LivingEntity) boss).setRemoveWhenFarAway(false);
				EntityManager.Equipment.setSpaceHelmet((LivingEntity) boss);
			}
			// Set health to max health
			if (boss instanceof Attributable && boss instanceof Damageable) {
				((Damageable) boss).setHealth(((Attributable) boss).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
			}
		});
		
		return result;
	}
	
}
