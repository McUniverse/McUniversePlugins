package eu.mcuniverse.supplyrockets.meteorite;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.mcuniverse.supplyrockets.chances.MeteoriteSize;
import eu.mcuniverse.supplyrockets.chances.Tier;
import eu.mcuniverse.universeapi.rockets.Planet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class MeteoriteLarge implements IMeteorite {

	private World world;
	private Location location;
	private ObjectOpenHashSet<ItemStack> items;
	private Inventory inventory;
	private Tier tier = Tier.METEORITE_LARGE;
	@Setter
	private ArmorStand armorStand;
	private Planet planet;
	private MeteoriteSize meteoriteSize = MeteoriteSize.LARGE;
	@Setter
	private Entity[] bosses;

	public MeteoriteLarge(Planet planet) {
		this.planet = planet;
		this.world = Bukkit.getWorld(planet.getWorldName());
		if (this.world == null) {
			throw new IllegalArgumentException(world + " is not valid world!");
		}
		location = planet.getRandomLocation();
	}

	@Override
	public Inventory getInventory() {
		if (inventory == null) {
			inventory = Bukkit.createInventory(this, InventoryType.CHEST, "Small Meteorite");
			fillInventory();
		}
		return inventory;
	}

}
