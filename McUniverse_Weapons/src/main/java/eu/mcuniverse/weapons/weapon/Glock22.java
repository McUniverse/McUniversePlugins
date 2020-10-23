package eu.mcuniverse.weapons.weapon;

import org.bukkit.Particle;
import org.bukkit.entity.Player;

import eu.mcuniverse.weapons.particle.ParticleEffect;
import eu.mcuniverse.weapons.sound.SoundEffect;
import eu.mcuniverse.weapons.weapon.util.AmmunitionType;
import eu.mcuniverse.weapons.weapon.util.ShootingManager;
import eu.mcuniverse.weapons.weapon.util.Weapon;
import eu.mcuniverse.weapons.weapon.util.WeaponType;

public class Glock22 implements Weapon {

	@Override
	public double getDamage() {
		return 6;
	}

	@Override
	public double getRange() {
		return 100;
	}
	
	@Override
	public long getShootingSpeed() {
		return 10;
	}
	
	@Override
	public long getReloadingTime() {
		return 40;
	}

	@Override
	public int getMagazineSize() {
		return 15;
	}

	@Override
	public WeaponType getWeaponType() {
		return WeaponType.GLOCK_22;
	}

	@Override
	public AmmunitionType getAmmunitionType() {
		return AmmunitionType.NORMAL;
	}
	
	@Override
	public SoundEffect getSoundEffect() {
		return new SoundEffect();
	}

	@Override
	public ParticleEffect getParticleEffect() {
		return ParticleEffect.builder().particle(Particle.SMOKE_NORMAL).count(10).build();
	}
	
	@Override
	public boolean shoot(Player player) {
		ShootingManager.shootRayTrace(player, this);
		return true;
	}

}
