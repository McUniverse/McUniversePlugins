package eu.mcuniverse.universeapi.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import lombok.NonNull;

/**
 * Interface to create an Inventory / GUI
 * @author jay
 *
 */
public interface IGUI extends InventoryHolder {

	/**
	 * Method which runs on an InventoryClick
	 * @param whoClicked {@linkplain Player} who clicked
	 * @param slot Raw slot of the clicked Item
	 * @param clickedItem {@linkplain ItemStack} which got clicked
	 * @param cursor {@linkplain ItemStack} on the cursor
	 */
	public void onGUIClick(@NonNull Player whoClicked, int slot, ItemStack clickedItem, ItemStack cursor);
	
}
