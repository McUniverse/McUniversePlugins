package eu.mcuniverse.weapons.weapon;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import eu.mcuniverse.weapons.sound.SoundEffect;
import eu.mcuniverse.weapons.weapon.util.AmmunitionType;
import eu.mcuniverse.weapons.weapon.util.ShootingManager;
import eu.mcuniverse.weapons.weapon.util.Weapon;
import eu.mcuniverse.weapons.weapon.util.WeaponType;

public class Shotgun implements Weapon {

	@Override
	public double getDamage() {
		return 5;
	}

	@Override
	public double getRange() {
		return 30;
	}

	@Override
	public long getShootingSpeed() {
		return 40L;
	}

	@Override
	public long getReloadingTime() {
		return 20L * 4;
	}

	@Override
	public int getMagazineSize() {
		return 2;
	}

	@Override
	public WeaponType getWeaponType() {
		return WeaponType.SHOTGUN;
	}

	@Override
	public AmmunitionType getAmmunitionType() {
		return AmmunitionType.NORMAL;
	}

	@Override
	public SoundEffect getSoundEffect() {
		return new SoundEffect(Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 0.5f);
	}

	@Override
	public boolean shoot(Player player) {
		ShootingManager.shootShotgun(player, this);
		return true;
	}

}
