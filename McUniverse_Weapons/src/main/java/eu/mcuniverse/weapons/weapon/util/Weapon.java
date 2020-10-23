package eu.mcuniverse.weapons.weapon.util;

import org.bukkit.entity.Player;

import eu.mcuniverse.weapons.particle.ParticleEffect;
import eu.mcuniverse.weapons.sound.SoundEffect;

public interface Weapon {

	public double getDamage();
	
	public double getRange();
	
	public long getShootingSpeed();
	
	public long getReloadingTime();
	
	public int getMagazineSize();
	
	public WeaponType getWeaponType();
	
	public AmmunitionType getAmmunitionType();
	
	default public SoundEffect getSoundEffect() {
		return new SoundEffect();
	}
	
	default public ParticleEffect getParticleEffect() {
		return new ParticleEffect();
	}
	
	/**
	 * 
	 * @param player
	 * @return True if ammunition should be removed
	 */
	public boolean shoot(Player player);
	
}
