package eu.mcuniverse.weapons.data;

import java.util.regex.Pattern;

import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.Sound;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GunData {

	public final String WEAPON_NAME = "%s (%d/%d)";
	
	public final String AMMO_KEY = "ammunition";

	public final String AMMO_TYPE_KEY = "ammunition_type";
	
	public final String RELOAD_KEY = "reloading";
	
//	public final Pattern AMMO_PATTERN = Pattern.compile("\\w{1,100}\\s[(]\\d{1,3}[/]\\d{1,3}[)]");
	public final Pattern AMMO_PATTERN = Pattern.compile("[A-Za-z0-9_ ]+\\s[(]\\d+[/]\\d+[)]");
	
//	public final Particle RAY_PARTICLE = Particle.REDSTONE;
//	public final DustOptions DUST_OPTIONS = new Particle.DustOptions(Color.ORANGE, 1);

	public final Particle RAY_PARTICLE = Particle.DOLPHIN;
	public final DustOptions DUST_OPTIONS = null;
	
	public final Sound SHOOTING_SOUND = Sound.ENTITY_EGG_THROW;
	
}
