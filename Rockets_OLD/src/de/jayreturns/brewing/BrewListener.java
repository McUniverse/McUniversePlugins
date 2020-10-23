package de.jayreturns.brewing;

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

import de.jayreturns.main.Main;

public class BrewListener implements Listener {

	@EventHandler
	public void potionItemPlacer(InventoryClickEvent e) {
		if (e.getClickedInventory() == null)
			return;
		if (e.getClickedInventory().getType() != InventoryType.BREWING)
			return;
		if (e.getClick() != ClickType.LEFT)
			return;
		final ItemStack is = e.getCurrentItem();
		final ItemStack is2 = e.getCursor().clone();
		if (is2 == null)
			return;
		if (is2.getType() == Material.AIR)
			return;
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				e.setCursor(is);
				e.getClickedInventory().setItem(e.getSlot(), is2);
			}
		}, 1L);
		((Player) e.getWhoClicked()).updateInventory();
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void PotionListener(InventoryClickEvent e) {
		if (e.getClickedInventory() == null) {
			return;
		}
		if (e.getClickedInventory().getType() != InventoryType.BREWING) {
			return;
		}
		if (((BrewerInventory) e.getInventory()).getIngredient() == null) {
			return;
		}
		BrewingRecipe recipe = BrewingRecipe.getRecipe((BrewerInventory) e.getClickedInventory());
		if (recipe == null) {
			return;
		}
		e.getClickedInventory().setItem(e.getSlot(), e.getCursor());
		e.setCursor(null);
		recipe.startBrewing((BrewerInventory) e.getClickedInventory());
	}

}
