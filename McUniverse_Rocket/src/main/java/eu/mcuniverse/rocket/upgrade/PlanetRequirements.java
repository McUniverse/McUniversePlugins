package eu.mcuniverse.rocket.upgrade;

import java.util.stream.Collectors;

import eu.mcuniverse.rocket.rocket.Rocket;
import eu.mcuniverse.rocket.rocket.RocketPart;
import eu.mcuniverse.rocket.rocket.RocketPart.AirTank;
import eu.mcuniverse.rocket.rocket.RocketPart.Boardcomputer;
import eu.mcuniverse.rocket.rocket.RocketPart.Frostshield;
import eu.mcuniverse.rocket.rocket.RocketPart.FuelTank;
import eu.mcuniverse.rocket.rocket.RocketPart.Heatshield;
import eu.mcuniverse.rocket.rocket.RocketPart.Hullarmor;
import eu.mcuniverse.rocket.rocket.RocketPart.Part;
import eu.mcuniverse.rocket.rocket.RocketPart.Storage;
import eu.mcuniverse.rocket.rocket.RocketPart.WaterTank;
import eu.mcuniverse.universeapi.rockets.Planet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PlanetRequirements {

	public ObjectArrayList<RocketPart.Part> getRequirements(Planet planet) {
		ObjectArrayList<RocketPart.Part> result = new ObjectArrayList<>();
		switch (planet) {
		case MERCURY:
			result.add(Boardcomputer.TIER_FOUR);
			result.add(Heatshield.TIER_ONE);
			result.add(Hullarmor.TIER_ONE);
			result.add(Storage.TIER_ONE);
			result.add(FuelTank.TIER_ONE);
			result.add(AirTank.TIER_ONE);
			result.add(WaterTank.TIER_ONE);
			break;
		case VENUS:
			result.add(Boardcomputer.TIER_THREE);
			result.add(Heatshield.TIER_TWO);
			result.add(Hullarmor.TIER_ONE);
			result.add(Storage.TIER_ONE);
			result.add(FuelTank.TIER_ONE);
			result.add(AirTank.TIER_ONE);
			result.add(WaterTank.TIER_ONE);
			break;
		case EARTH:
			break;
		case MOON:
			result.add(Boardcomputer.TIER_ONE);
			result.add(Hullarmor.TIER_ONE);
			result.add(WaterTank.TIER_ONE);
			result.add(Storage.TIER_ONE);
			result.add(FuelTank.TIER_ONE);
			result.add(AirTank.TIER_ONE);
			break;
		case MARS:
			result.add(Boardcomputer.TIER_TWO);
			result.add(Frostshield.TIER_ONE);
			result.add(Hullarmor.TIER_ONE);
			result.add(WaterTank.TIER_ONE);
			result.add(Storage.TIER_ONE);
			result.add(FuelTank.TIER_ONE);
			result.add(AirTank.TIER_ONE);
			break;
		case JUPITER:
			result.add(Boardcomputer.TIER_THREE);
			result.add(Frostshield.TIER_TWO);
			result.add(Hullarmor.TIER_ONE);
			result.add(WaterTank.TIER_ONE);
			result.add(AirTank.TIER_ONE);
			result.add(FuelTank.TIER_ONE);
			result.add(Storage.TIER_ONE);
			break;
		case SATURN:
			result.add(Boardcomputer.TIER_FOUR);
			result.add(Frostshield.TIER_THREE);
			result.add(Hullarmor.TIER_ONE);
			result.add(WaterTank.TIER_ONE);
			result.add(AirTank.TIER_ONE);
			result.add(FuelTank.TIER_ONE);
			result.add(Storage.TIER_ONE);
			break;
		case URANUS:
			result.add(Boardcomputer.TIER_FIVE);
			result.add(Frostshield.TIER_FOUR);
			result.add(Hullarmor.TIER_ONE);
			result.add(WaterTank.TIER_ONE);
			result.add(AirTank.TIER_ONE);
			result.add(FuelTank.TIER_ONE);
			result.add(Storage.TIER_ONE);
			break;
		case NEPTUNE:
			result.add(Boardcomputer.TIER_SIX);
			result.add(Frostshield.TIER_FIVE);
			result.add(Hullarmor.TIER_ONE);
			result.add(WaterTank.TIER_ONE);
			result.add(AirTank.TIER_ONE);
			result.add(FuelTank.TIER_ONE);
			result.add(Storage.TIER_ONE);
			break;
		default:
			break;
		}
		return result.stream()
				.sorted((p1, p2) -> p1.getClassName().compareTo(p2.getClassName()))
				.collect(Collectors.toCollection(ObjectArrayList::new));
//		return result.stream().sorted().collect(Collectors.toCollection(ObjectArrayList::new));
//		return result;
	}

	public boolean isAccessible(Planet planet, Rocket rocket) {
		ObjectCollection<Part> requirement = getRequirements(planet);
		if (requirement.isEmpty()) {
			return true;
		}
		boolean access = true;
		for (Part part : requirement) {
			if (part instanceof AirTank) {
				if (!rocket.getAirTank().isAtLeast(part)) {
					access = false;
					break;
				}
			} else if (part instanceof WaterTank) {
				if (!rocket.getWaterTank().isAtLeast(part)) {
					access = false;
					break;
				}
			} else if (part instanceof Hullarmor) {
				if (!rocket.getHullarmor().isAtLeast(part)) {
					access = false;
					break;
				}
			} else if (part instanceof Boardcomputer) {
				if (!rocket.getBoardcomputer().isAtLeast(part)) {
					access = false;
					break;
				}
			} else if (part instanceof Frostshield) {
				if(!rocket.getFrostshield().isAtLeast(part)) {
					access = false;
					break;
				}
			} else if (part instanceof Heatshield) {
				if (!rocket.getHeatshield().isAtLeast(part)) {
					access = false;
					break;
				}
			} else if (part instanceof Storage) {
				if (!rocket.getStorage().isAtLeast(part)) {
					access = false;
					break;
				}
			} else if (part instanceof FuelTank) {
				if (!rocket.getFuelTank().isAtLeast(part)) {
					access = false;
					break;
				}
			}
		}
		return access;
	}

}
