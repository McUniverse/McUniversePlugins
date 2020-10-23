package de.jayreturns.supply;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import de.jayreturns.planets.Planet;

public class SupplyRocket {

	private Planet planet;
	private List<ItemStack> content;
	private Location loc;

	public SupplyRocket(Planet planet, List<ItemStack> content, Location loc) {
		this.planet = planet;
		this.content = content;
		this.loc = loc;
	}

	public Planet getPlanet() {
		return planet;
	}
	
	public List<ItemStack> getContent() {
		return content;
	}

	public Location getLoc() {
		return loc;
	}
	
	public void randomizeLocation() {
		loc = planet.getRandomLocation();
	}
	
	public void addItem(ItemStack item) {
		content.add(item);
	}

	public void spawn() {
		
	}
	
}