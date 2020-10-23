package eu.mcuniverse.universeapi.rockets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlanetTest {
	
	@Test
	public void test() {
		assertEquals(Planet.MERCURY.getDistance(Planet.NEPTUNE), 8, "Mercury -> Neptune");
		assertEquals(Planet.EARTH.getDistance(Planet.MOON), 1, "Earth -> Moon");
		assertEquals(Planet.EARTH.getDistance(Planet.MARS), 2, "Earth -> Mars");
		assertEquals(Planet.MOON.getDistance(Planet.MARS), 1, "Moon -> Mars");
	}

}
