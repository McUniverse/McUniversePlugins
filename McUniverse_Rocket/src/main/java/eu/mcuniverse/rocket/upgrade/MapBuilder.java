package eu.mcuniverse.rocket.upgrade;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

public final class MapBuilder {

	private static Object2IntOpenHashMap<ItemStack> map;
	
	public MapBuilder() {
		map = new Object2IntOpenHashMap<ItemStack>();
	}
	
	public MapBuilder put(ItemStack key, int value) {
		map.put(key, value);
		return this;
	}
	
	public MapBuilder put(Material mat, int value) {
		map.put(new ItemStack(mat), value);
		return this;
	}
	
	public Object2IntOpenHashMap<ItemStack> build() {
		return map;
	}
	
}
