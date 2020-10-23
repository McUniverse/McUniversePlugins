package eu.mcuniverse.weapons.weapon.util;

import eu.mcuniverse.weapons.graphics.Skin;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter()
@AllArgsConstructor
public enum AmmunitionType {

	NORMAL(Skin.AMMUNITION),
	ROCKET(Skin.ROCKET_AMMUNITION),
	LASER(Skin.LASER_AMMUNITION);
	
	private Skin skin;
	
}
