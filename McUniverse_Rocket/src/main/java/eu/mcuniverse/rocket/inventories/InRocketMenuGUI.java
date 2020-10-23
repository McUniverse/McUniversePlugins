package eu.mcuniverse.rocket.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.mcuniverse.rocket.rocket.InRocketManager;
import eu.mcuniverse.rocket.util.ItemUtil;
import eu.mcuniverse.universeapi.inventory.IGUI;
import eu.mcuniverse.universeapi.rockets.Planet;
import net.md_5.bungee.api.ChatColor;

public class InRocketMenuGUI implements IGUI {

	@Override
	public Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(this, 9, ChatColor.AQUA + "Rocket Menu");
		
		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, ItemUtil.getFiller());
		}
		
		inv.setItem(3, ItemUtil.createItem(Material.MINECART, ChatColor.AQUA + "Leave your rocket"));
		inv.setItem(5, ItemUtil.createItem(Material.LIME_CONCRETE, ChatColor.DARK_GREEN + "Start"));
		
		return inv;
	}

	@Override
	public void onGUIClick(Player whoClicked, int slot, ItemStack clickedItem, ItemStack cursor) {
		if (clickedItem == null || clickedItem.getType() == Material.AIR) {
			return;
		}
		
		if (clickedItem.getType() == Material.MINECART) {
			InRocketManager.managePlayer(whoClicked);
		} else if (clickedItem.getType() == Material.LIME_CONCRETE) {
			whoClicked.openInventory(new RocketLaunchGUI(whoClicked, Planet.getPlanet(whoClicked)).getInventory());
		}
		
	}

	
	
}
