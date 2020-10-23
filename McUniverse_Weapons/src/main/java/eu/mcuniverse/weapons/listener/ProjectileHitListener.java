package eu.mcuniverse.weapons.listener;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import eu.mcuniverse.weapons.main.Core;
import eu.mcuniverse.weapons.weapon.util.WeaponType;

public class ProjectileHitListener implements Listener {

	public ProjectileHitListener(final Core plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onHit(ProjectileHitEvent e) {
		if (e.getEntityType() != EntityType.SNOWBALL) {
			return;
		}
		if (e.getEntity().hasMetadata("weapon")) {
			if (e.getHitEntity() != null) {
				if (e.getHitEntity().getType() == EntityType.SNOWBALL) {
					return;
				}
				Snowball s = (Snowball) e.getEntity();
				if (e.getHitEntity() instanceof LivingEntity) {
					LivingEntity lv = (LivingEntity) e.getHitEntity();
					String damage = e.getEntity().getCustomName();
					try {
						double dmg = Double.parseDouble(damage);
						lv.damage(dmg, (Entity) s.getShooter());
					} catch (NumberFormatException exe) {
						return;
					}
				}
			}

			String weapon = e.getEntity().getMetadata("weapon").get(0).asString();
			if (weapon.equals(WeaponType.ROCKET_LAUNCHER.toString())) {
				if (e.getHitBlock() != null) {
					Location loc = e.getHitBlock().getLocation();
					loc.getWorld().createExplosion(loc, 4, false, true);
				}
			}
		}
	}
}
