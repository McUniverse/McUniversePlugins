package eu.mcuniverse.weapons.weapon.util;

import eu.mcuniverse.weapons.graphics.*;
import lombok.*;

@Getter
@AllArgsConstructor
public enum WeaponType {

	FAMAS(Skin.FAMAS),
	GLOCK_22(Skin.GLOCK_22),
	HK_MP5(Skin.HK_MP5),
	LASER_LMG(Skin.LASER_LMG),
	LASER_RIFLE(Skin.LASER_RIFLE),
	LASER_SNIPER(Skin.LASER_SNIPER),
	ROCKET_LAUNCHER(Skin.ROCKET_LAUNCHER),
	SCAR(Skin.SCAR),
	SHOTGUN(Skin.SHOTGUN);
	
	private Skin skin;
	
}
