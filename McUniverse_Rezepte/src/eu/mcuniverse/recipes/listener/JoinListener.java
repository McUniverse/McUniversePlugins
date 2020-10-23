package eu.mcuniverse.recipes.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.mcuniverse.recipes.recipes.Recipes_Inventory;

public class JoinListener implements Listener {

	private final String NAME = "§8-» §dRecipes §8«-";

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		ItemStack Rezept = new ItemStack(Material.STRUCTURE_BLOCK);
		ItemMeta Rezeptmeta = Rezept.getItemMeta();
		Rezeptmeta.setDisplayName(NAME);
		Rezept.setItemMeta(Rezeptmeta);

		p.getInventory().setItem(7, Rezept);

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void on(InventoryClickEvent e) {
		if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(NAME)) {
			e.setCancelled(true);
			e.setCursor(null);
		}
	}

	@EventHandler
	public void on1(PlayerDropItemEvent e) {
		if (e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(NAME)) {
			e.setCancelled(true);	
		}
	}

	@EventHandler
	public void on2(PlayerInteractEvent e) {
		if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(NAME)) {
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				Recipes_Inventory.openGUI(e.getPlayer());
			}
		}
	}
}