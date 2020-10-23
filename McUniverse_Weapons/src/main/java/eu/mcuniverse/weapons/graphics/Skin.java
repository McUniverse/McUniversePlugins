package eu.mcuniverse.weapons.graphics;

import org.bukkit.Material;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Skin {
	
	C4_1(10000001, "C4"),
	C4_2(10000002, "C4"),
	FAMAS(10000003, "Famas"),
	GLOCK_22(10000004, "Glock 22"),
	GRENADE(10000005, ""),
	HK_MP5(10000006, "HK MP5"),
	LASER_LMG(10000007, "Laser LMG"),
	LASER_RIFLE(10000008, "Laser Rifle"),
	LASER_SNIPER(10000009, "Laser Sniper"),
	ROCKET_LAUNCHER(10000010, "Rocket Launcher"),
	SCAR(10000011, "SCAR"),
	SHOTGUN(10000012, "Shotgun"),
	SMOKE_GRENADE(10000013, ""),
	AMMUNITION(10000015, "Ammunition"),
	ROCKET_AMMUNITION(10000016, "Rocket Ammunition"),
	LASER_AMMUNITION(10000017, "Laser Ammunition");
	
	public static final Material baseMaterial = Material.BLAZE_ROD;
	private int customModelData;
	private String displayName;
	
}
