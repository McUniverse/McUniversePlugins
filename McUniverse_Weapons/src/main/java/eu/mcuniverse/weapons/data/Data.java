package eu.mcuniverse.weapons.data;

import java.util.Map;
import java.util.UUID;

import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.collect.Maps;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Data {

	public final Map<UUID, BukkitRunnable> reloading = Maps.newHashMap();
	
//	public final Map<UUID, Map<Weapon, BukkitRunnable>> cooldown = Maps.newHashMap();
	
	public final Map<UUID, Long> cooldown = Maps.newHashMap();
	
}
