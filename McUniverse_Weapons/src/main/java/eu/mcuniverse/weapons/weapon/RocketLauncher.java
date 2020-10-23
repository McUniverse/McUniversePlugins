package eu.mcuniverse.weapons.weapon;

import org.bukkit.Particle;
import org.bukkit.entity.Player;

import eu.mcuniverse.weapons.particle.ParticleEffect;
import eu.mcuniverse.weapons.sound.SoundEffect;
import eu.mcuniverse.weapons.weapon.util.AmmunitionType;
import eu.mcuniverse.weapons.weapon.util.ShootingManager;
import eu.mcuniverse.weapons.weapon.util.Weapon;
import eu.mcuniverse.weapons.weapon.util.WeaponType;

public class RocketLauncher implements Weapon {

	@Override
	public double getDamage() {
		return 10;
	}

	@Override
	public double getRange() {
		return 10;
	}

	@Override
	public long getShootingSpeed() {
		return 20L * 3;
	}

	@Override
	public long getReloadingTime() {
		return 20L * 5;
	}

	@Override
	public int getMagazineSize() {
		return 1;
	}

	@Override
	public WeaponType getWeaponType() {
		return WeaponType.ROCKET_LAUNCHER;
	}

	@Override
	public AmmunitionType getAmmunitionType() {
		return AmmunitionType.ROCKET;
	}

	@Override
	public SoundEffect getSoundEffect() {
		return new SoundEffect();
	}

	@Override
	public ParticleEffect getParticleEffect() {
		return ParticleEffect.builder().particle(Particle.SMOKE_NORMAL).count(10).speed(0).build();
//		return new ParticleEffect(Particle.SMOKE_LARGE);
	}
	
	@Override
	public boolean shoot(Player player) {
		ShootingManager.shootWithSnowball(player, this);
		return true;
	}

}
