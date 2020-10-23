package eu.mcuniverse.supplyrockets.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import it.unimi.dsi.fastutil.objects.ObjectSet;

public class ObjectSetExtension {

	public static ObjectSet<ItemStack> insert(ObjectSet<ItemStack> in, Material mat) {
//		in.add(new ItemStack(mat));
		return in;
	}
	
	public static <T> T or(T obj, T ifNull) {
		return obj == null ? obj : ifNull;
	}
	
}
