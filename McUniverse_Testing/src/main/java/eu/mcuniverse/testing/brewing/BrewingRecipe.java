package eu.mcuniverse.testing.brewing;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.block.BrewingStand;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import eu.mcuniverse.testing.main.Main;

public class BrewingRecipe {
	private static List<BrewingRecipe> recipes = new ArrayList<BrewingRecipe>();
	private ItemStack ingredient;
	private BrewAction action;
	private boolean perfect;

	public BrewingRecipe(ItemStack ingredient, BrewAction action, boolean perfect) {
		this.ingredient = ingredient;
		this.action = action;
		this.perfect = perfect;
		recipes.add(this);
	}

	public BrewingRecipe(Material ingredient, BrewAction action) {
		this(new ItemStack(ingredient), action, false);
	}

	public ItemStack getingredient() {
		return ingredient;
	}

	public BrewAction getAction() {
		return action;
	}

	public boolean isPerfect() {
		return perfect;
	}

	/**
	 * Get the BrewRecipe of the given recipe , will return null if no recipe is
	 * found
	 * 
	 * @param inventory The inventory
	 * @return The recipe
	 */
	@Nullable
	public static BrewingRecipe getRecipe(BrewerInventory inventory) {
		boolean notAllAir = false;
		for (int i = 0; i < 3 && !notAllAir; i++) {
			if (inventory.getItem(i) == null)
				continue;
			if (inventory.getItem(i).getType() == Material.AIR)
				continue;
			notAllAir = true;
		}
		if (!notAllAir)
			return null;
		for (BrewingRecipe recipe : recipes) {
			if (!recipe.isPerfect() && inventory.getIngredient().getType() == recipe.getingredient().getType()) {
				return recipe;
			}
			if (recipe.isPerfect() && inventory.getIngredient().isSimilar(recipe.getingredient())) {
				return recipe;
			}
		}
		return null;
	}

//	/**
//	 * Get the BrewRecipe of the given recipe , will return null if no recipe is
//	 * found
//	 * 
//	 * @param inventory The inventory
//	 * @return The recipe
//	 */
//	@Nullable
//	public static BrewingRecipe getRecipe(BrewerInventory inventory) {
//		for (BrewingRecipe recipe : recipes) {
//			if (!recipe.isPerfect() && inventory.getIngredient().getType() == recipe.getingredient().getType()) {
//				return recipe;
//			}
//			if (recipe.isPerfect() && inventory.getIngredient().isSimilar(recipe.getingredient())) {
//				return recipe;
//			}
//		}
//		return null;
//	}

	public void startBrewing(BrewerInventory inventory) {
		new BrewClock(this, inventory);
	}

	private class BrewClock extends BukkitRunnable {
		private BrewerInventory inventory;
		private BrewingRecipe recipe;
		private ItemStack ingredient;
		private BrewingStand stand;
		private int time = 400; // Like I said the starting time is 400

		public BrewClock(BrewingRecipe recipe, BrewerInventory inventory) {
			this.recipe = recipe;
			this.inventory = inventory;
			this.ingredient = inventory.getIngredient();
			this.stand = inventory.getHolder();
			runTaskTimer(Main.instance, 0L, 1L);
		}

		@Override
		public void run() {
			if (time == 0) {
				inventory.setIngredient(new ItemStack(Material.AIR));
				for (int i = 0; i < 3; i++) {
					if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR)
						continue;
					try {
						recipe.getAction().brew(inventory, inventory.getItem(i), ingredient);
					} catch (Exception exe) {
						this.cancel();
					}
				}
				cancel();
				return;
			}
			try {
			if (inventory.getIngredient().isSimilar(ingredient)) {
				stand.setBrewingTime(400); // Reseting everything
				cancel();
				return;
			}
			} catch (Exception e) {}
			// You should also add here a check to make sure that there are still items to
			// brew
			time--;
			stand.setBrewingTime(time);
			stand.update(true, false);
		}
	}
}