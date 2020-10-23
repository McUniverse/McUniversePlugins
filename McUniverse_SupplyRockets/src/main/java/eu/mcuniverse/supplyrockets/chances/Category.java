package eu.mcuniverse.supplyrockets.chances;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {

	BASIC(2, 4),
	NETHER(0, 3),
	END(0, 3),
	PRISMARINE(0, 3),
	ORES_MOB(0, 3),
	PLANTS(1, 4),
	EGGS(1, 2),
	FIREARM(1, 2),
	HEAVY(1, 2);
	
	int min, max;
	
}
