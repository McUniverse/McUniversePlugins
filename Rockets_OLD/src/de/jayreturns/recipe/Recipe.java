package de.jayreturns.recipe;

import org.bukkit.event.Listener;

public class Recipe implements Listener {

//	public static void addRecipe() {
//		// Enchanted Iron
//		ItemStack i1 = Items.getEnchantedIron().getItemStack();
//		NamespacedKey k1 = new NamespacedKey(Main.getInstance(), "eIron");
//		ShapedRecipe r1 = new ShapedRecipe(k1, i1);
//		r1.shape("III", "III", "III");
//		r1.setIngredient('I', Material.IRON_BLOCK);
//		Bukkit.addRecipe(r1);
//	}
//
//	@EventHandler
//	public void onPreparecrafting(PrepareItemCraftEvent e) {
//		ItemStack[] content = e.getInventory().getContents();
//		if (Arrays.asList(content).contains(Items.getEnchantedIron().getItemStack())) {
//			e.getInventory().setItem(0, null);
//		}
//		// Encahtned Iron Block
//		boolean result = true;
//		for (int i = 1; i < content.length; i++) { // 0 is result
//			if (!CustomItem.isSameItem(Items.getEnchantedIron(), content[i])) {
//				result = false;
//				break;
//			}
//		}
//		if (result) e.getInventory().setItem(0, Items.getEnchantedIronBlock().getItemStack());
//
//	}

}
