package eu.mcuniverse.testing.crafting;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import eu.mcuniverse.testing.main.Main;
import eu.mcuniverse.universeapi.util.ItemUtil;

public class CraftingTest {

	public static void init(Main plugin) {
		
		NamespacedKey namespacedKey = new NamespacedKey(plugin, "test_craft");
		
		ShapedRecipe recipe = new ShapedRecipe(namespacedKey, new ItemStack(Material.COAL));
		System.out.println(recipe.getKey());
		
		recipe.shape("EEE", "EEE", "ESE");
		
		recipe.setIngredient('E', Material.EMERALD);
		recipe.setIngredient('S', Material.STICK);
		
		Bukkit.getServer().addRecipe(recipe);
		
		Player p = Bukkit.getPlayer("JayReturns");
		p.discoverRecipe(namespacedKey);
		
		ItemStack item = ItemUtil.createEnchantedItem(Material.STICK, "I'm a stick");
		
		p.getInventory().addItem(item);
		
	}
	
}
