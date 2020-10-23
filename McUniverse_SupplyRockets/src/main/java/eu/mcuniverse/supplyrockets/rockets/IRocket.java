package eu.mcuniverse.supplyrockets.rockets;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import eu.mcuniverse.supplyrockets.chances.Tier;
import eu.mcuniverse.supplyrockets.loot.LootManager;
import eu.mcuniverse.supplyrockets.utils.RandomUtils;
import eu.mcuniverse.universeapi.rockets.Planet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;

public interface IRocket extends InventoryHolder {

	public Tier getTier();
	
	public ArmorStand getArmorStand();
	
	public ObjectOpenHashSet<ItemStack> getItems();
	
	public Location getLocation();
	
	public World getWorld();
	
	public Planet getPlanet();
	
	public void spawn();
	
	public default void fillInventory() {
		ObjectSet<ItemStack> loot = LootManager.getLoot(getTier());
		int[] slots = RandomUtils.getRandomSlot(getInventory().getSize());
		int index = 0; 
		for (ItemStack item : loot) {
			getInventory().setItem(slots[index], item);
			index++;
		}
	}
	
}
