package eu.mcuniverse.weapons.weapon;

import org.bukkit.entity.Player;

import eu.mcuniverse.weapons.sound.SoundEffect;
import eu.mcuniverse.weapons.weapon.util.AmmunitionType;
import eu.mcuniverse.weapons.weapon.util.ShootingManager;
import eu.mcuniverse.weapons.weapon.util.Weapon;
import eu.mcuniverse.weapons.weapon.util.WeaponType;

public class LaserSniper implements Weapon {

	@Override
	public double getDamage() {
		return 20;
	}

	@Override
	public double getRange() {
		return 500;
	}

	@Override
	public long getShootingSpeed() {
		return 20L * 2;
	}
	
	@Override
	public long getReloadingTime() {
		return 20L * 5;
	}
	
	@Override
	public int getMagazineSize() {
		return 10;
	}

	@Override
	public WeaponType getWeaponType() {
		return WeaponType.LASER_SNIPER;
	}

	@Override
	public AmmunitionType getAmmunitionType() {
		return AmmunitionType.LASER;
	}
	@Override
	public SoundEffect getSoundEffect() {
		return new SoundEffect();
	}
	@Override
	public boolean shoot(Player player) {
		ShootingManager.shootRayTrace(player, this);
		return true;
	}

}
