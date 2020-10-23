package eu.mcuniverse.weapons.weapon;

import java.time.Instant;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import eu.mcuniverse.weapons.main.Core;
import eu.mcuniverse.weapons.sound.SoundEffect;
import eu.mcuniverse.weapons.weapon.util.AmmunitionManager;
import eu.mcuniverse.weapons.weapon.util.AmmunitionType;
import eu.mcuniverse.weapons.weapon.util.ShootingManager;
import eu.mcuniverse.weapons.weapon.util.Weapon;
import eu.mcuniverse.weapons.weapon.util.WeaponType;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public class Famas implements Weapon {

	private Famas instance;
	private Object2BooleanOpenHashMap<Player> running;
	private Object2ObjectOpenHashMap<Player, Instant> last;
	
	public Famas() {
		running = new Object2BooleanOpenHashMap<Player>();
		last = new Object2ObjectOpenHashMap<Player, Instant>();
		
		running.defaultReturnValue(false);
		last.defaultReturnValue(Instant.now());
		instance = this;
	}
	
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
		return 5;
	}

	@Override
	public long getReloadingTime() {
		return 2 * 25; // 2.5 secs
	}

	@Override
	public int getMagazineSize() {
		return 25;
	}

	@Override
	public WeaponType getWeaponType() {
		return WeaponType.FAMAS;
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
		if (running.getBoolean(player)) {
			return false;
		}
		if (AmmunitionManager.getAmmo(player.getInventory().getItemInMainHand()) < 4) {
			ShootingManager.shootRayTrace(player, instance);
			return true;
		}
		if (Instant.now().isBefore(last.get(player))) {
			last.put(player, Instant.now());
			return false;
		}
		new BukkitRunnable() {
			
			int count = 0;
			
			@Override
			public void run() {
				if (count > 1) {
					AmmunitionManager.removeAmmunition(player, player.getInventory().getItemInMainHand(), instance);
				}
				
				if (count == 5) {
					last.put(player, Instant.now().plusSeconds(10));
					running.put(player, false);
					cancel();
				} else {
					running.put(player, true);
					count++;
					ShootingManager.shootRayTrace(player, instance);
				}
			}
		}.runTaskTimer(Core.getInstance(), 0L, 5L);
		return true;
	}
}
