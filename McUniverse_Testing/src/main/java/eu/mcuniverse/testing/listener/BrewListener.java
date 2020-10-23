package eu.mcuniverse.testing.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;

import eu.mcuniverse.testing.brewing.BrewingRecipe;
import eu.mcuniverse.testing.main.Main;

@SuppressWarnings("deprecation")
public class BrewListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void potionItemPlacer(final InventoryClickEvent e) {
		if (e.getClickedInventory() == null)
			return;
		if (e.getClickedInventory().getType() != InventoryType.BREWING)
			return;
		if (e.getClick() != ClickType.LEFT) // Make sure we are placing an item
			return;
		final ItemStack current = e.getCurrentItem(); // We want to get the item in the slot

		// Check if slot is empty to prevent duplication
		if (current.getType() != Material.AIR)
			return;

		final ItemStack cursor = e.getCursor().clone(); // And the item in the cursor
		if (cursor == null) // We make sure we got something in the cursor
			return;
		if (cursor.getType() == Material.AIR)
			return;
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
			@Override
			public void run() {
				e.setCursor(current);// Now we make the switch
				e.getClickedInventory().setItem(e.getSlot(), cursor);
				PotionListener(e);
			}
		}, 1L);// (Delay in 1 tick)
		((Player) e.getWhoClicked()).updateInventory();
	}

//	@EventHandler(priority = EventPriority.NORMAL)
	public void PotionListener(InventoryClickEvent e) {
		if (e.getClickedInventory() == null)
			return;
		if (e.getClickedInventory().getType() != InventoryType.BREWING)
			return;
		if (((BrewerInventory) e.getInventory()).getIngredient() == null)
			return;

		BrewingRecipe recipe = BrewingRecipe.getRecipe((BrewerInventory) e.getClickedInventory());
		if (recipe == null) {
			return;
		}
		e.getClickedInventory().setItem(e.getSlot(), e.getCursor());
		e.setCursor(null);
		recipe.startBrewing((BrewerInventory) e.getClickedInventory());
	}

}
