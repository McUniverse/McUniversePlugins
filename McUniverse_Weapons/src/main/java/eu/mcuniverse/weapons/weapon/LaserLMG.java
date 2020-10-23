package eu.mcuniverse.weapons.weapon;

import org.bukkit.entity.Player;

import eu.mcuniverse.weapons.sound.SoundEffect;
import eu.mcuniverse.weapons.weapon.util.AmmunitionType;
import eu.mcuniverse.weapons.weapon.util.ShootingManager;
import eu.mcuniverse.weapons.weapon.util.Weapon;
import eu.mcuniverse.weapons.weapon.util.WeaponType;

public class LaserLMG implements Weapon {

	@Override
	public double getDamage() {
		return 1;
	}

	@Override
	public double getRange() {
		return 200;
	}
	
	@Override
	public long getShootingSpeed() {
		return 1;
	}
	
	@Override
	public long getReloadingTime() {
		return 20 * 10;
	}
	
	@Override
	public int getMagazineSize() {
		return 50;
	}

	@Override
	public WeaponType getWeaponType() {
		return WeaponType.LASER_LMG;
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
