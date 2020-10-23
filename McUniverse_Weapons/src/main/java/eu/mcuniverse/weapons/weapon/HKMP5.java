package eu.mcuniverse.weapons.weapon;

import org.bukkit.entity.Player;

import eu.mcuniverse.weapons.sound.SoundEffect;
import eu.mcuniverse.weapons.weapon.util.AmmunitionType;
import eu.mcuniverse.weapons.weapon.util.ShootingManager;
import eu.mcuniverse.weapons.weapon.util.Weapon;
import eu.mcuniverse.weapons.weapon.util.WeaponType;

public class HKMP5 implements Weapon {

	@Override
	public double getDamage() {
		return 5;
	}

	@Override
	public double getRange() {
		return 100;
	}

	@Override
	public long getShootingSpeed() {
		return 1;
	}
	
	@Override
	public long getReloadingTime() {
		return 20 * 2;
	}
	
	@Override
	public int getMagazineSize() {
		return 30;
	}

	@Override
	public WeaponType getWeaponType() {
		return WeaponType.HK_MP5;
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
	public boolean shoot(Player player) {
		ShootingManager.shootRayTrace(player, this);
		return true;
	}

}
