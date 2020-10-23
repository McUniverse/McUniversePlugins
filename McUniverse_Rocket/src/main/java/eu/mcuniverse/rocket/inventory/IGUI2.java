package eu.mcuniverse.rocket.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public interface IGUI2 extends InventoryHolder {

	public void onGUIClick(Player whoClicked, int slot, ItemStack clickedItem, ItemStack cursor);
	
}
