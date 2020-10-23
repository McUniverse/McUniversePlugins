package eu.mcuniverse.supplyrockets.customentity;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.google.gson.Gson;

import eu.mcuniverse.supplyrockets.Core;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EntityManager {

	public boolean hasDoubleDamage(Entity entity) {
		if (!entity.hasMetadata("doubleDamage")) {
			return false;
		}
		return entity.getMetadata("doubleDamage").get(0).asBoolean();
	}

	public void setDoubleDamage(Entity entity) {
		entity.setMetadata("doubleDamage", new FixedMetadataValue(Core.getInstance(), true));
	}

	public boolean isBoss(Entity entity) {
		if (!entity.hasMetadata("boss")) {
			return false;
		}
		return entity.getMetadata("boss").get(0).asBoolean();
	}

	public void setBoss(Entity entity) {
		entity.setMetadata("boss", new FixedMetadataValue(Core.getInstance(), true));
	}

	public boolean hasArrowParticles(Entity entity) {
		if (!entity.hasMetadata("arrowParticle")) {
			return false;
		}
		return entity.getMetadata("arrowParticle").get(0) != null;
	}

	public Particle getArrowParticle(Entity entity) {
		if (!entity.hasMetadata("arrowParticle")) {
			return null;
		}
		return Particle.valueOf(entity.getMetadata("arrowParticle").get(0).asString());
	}

	public void setArrowParticle(Entity entity, Particle particle) {
		entity.setMetadata("arrowParticle", new FixedMetadataValue(Core.getInstance(), particle.toString()));
	}

	public void setMeteoriteLocation(Entity entity, Location location) {
		entity.setMetadata("meteoriteLocation",
				new FixedMetadataValue(Core.getInstance(), new Gson().toJson(location.serialize())));
	}

	@SuppressWarnings("unchecked")
	public Location getMeteoriteLocation(Entity entity) {
		if (!entity.hasMetadata("meteoriteLocation")) {
			return null;
		}
		return Location.deserialize((Map<String, Object>) new Gson()
				.fromJson(entity.getMetadata("meteoriteLocation").get(0).asString(), Map.class));
	}
	
	@UtilityClass
	public class Equipment {
		
		public void setSpaceHelmet(LivingEntity livingEntity) {
			ItemStack helmet = new ItemStack(Material.GLASS);
			livingEntity.getEquipment().setHelmet(helmet);
			livingEntity.getEquipment().setHelmetDropChance(0.0F);
		}
		
	}

}
