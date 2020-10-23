package eu.mcuniverse.supplyrockets.chances;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;

import eu.mcuniverse.supplyrockets.meteorite.IMeteorite;
import eu.mcuniverse.supplyrockets.meteorite.MeteoriteExtraLarge;
import eu.mcuniverse.supplyrockets.meteorite.MeteoriteLarge;
import eu.mcuniverse.supplyrockets.meteorite.MeteoriteMedium;
import eu.mcuniverse.supplyrockets.meteorite.MeteoriteSmall;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MeteoriteSize {

	SMALL(10000001, 3, 1, 1, MeteoriteSmall.class),
	MEDIUM(10000002, 6, 1, 1, MeteoriteMedium.class),
	LARGE(10000003, 10, 1, 1, MeteoriteLarge.class),
	EXTRA_LARGE(10000004, 13, 1, 1, MeteoriteExtraLarge.class);

	private int customModelData;
	private int craterSize;
	private int rangeCount; // Ranged Mobs
	private int meleeCount; // Melee mobs
	private Class<? extends IMeteorite> clazz;
	public static final Material BASE_ITEM = Material.BAMBOO;
	
	public String getName() {
		switch (this) {
		case SMALL:
			return "A small Meteorite";
		case MEDIUM:
			return "A medium Meteorite";
		case LARGE:
			return "A large Meteorite";
		case EXTRA_LARGE:
			return "An extra large Meteorite";
		default:
			return "null";
		}
	}
	
	@Override
	public String toString() {
		return WordUtils.capitalize(super.toString().toLowerCase());
	}
	
	public static MeteoriteSize getRandomSize() {
		double chance = ThreadLocalRandom.current().nextDouble();
		if (chance <= 0.25) {
			return MeteoriteSize.SMALL;
		} else if (chance > 0.25 && chance <= 0.5) {
			return MeteoriteSize.MEDIUM;
		} else if (chance > 0.5 && chance <= 0.75) {
			return MeteoriteSize.LARGE;
		} else if (chance > 0.75) {
			return MeteoriteSize.EXTRA_LARGE;
		} else {
			throw new Error("The ThreadLocalRandom#nextDouble() yields a number greather than 1! Generated double: " + chance);
		}
	}

}
