package eu.mcuniverse.rocket.inventories;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.mcuniverse.rocket.data.Messages;
import eu.mcuniverse.rocket.rocket.RocketManager;
import eu.mcuniverse.rocket.util.ItemUtil;
import eu.mcuniverse.universeapi.inventory.IGUI;
import net.md_5.bungee.api.ChatColor;

public class DeleteGUI implements IGUI {

	@Override
	public Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(this, 27, ChatColor.GREEN + "Confirm rocket delete");
		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, ItemUtil.getFiller());
		}
		// Confirm with green wool
		Arrays.asList(1, 2, 3, 10, 11, 12, 19, 20, 21).forEach(slot -> {
			inv.setItem(slot, ItemUtil.createItem(Material.LIME_WOOL, ChatColor.DARK_GREEN + "Confirm",
					ChatColor.DARK_RED + "Your rocket and items will be deletetd!"));
		});
		;

		Arrays.asList(5, 6, 7, 14, 15, 16, 23, 24, 25).forEach(slot -> {
			inv.setItem(slot, ItemUtil.createItem(Material.RED_WOOL, ChatColor.DARK_RED + "Cancel"));
		});
		return inv;
	}

	@Override
	public void onGUIClick(Player whoClicked, int slot, ItemStack clickedItem, ItemStack cursor) {
		if (clickedItem == null || clickedItem.getType() == Material.AIR) {
			return;
		}

		if (clickedItem.getType() == Material.LIME_WOOL) {
			RocketManager.deleteRocket(whoClicked);
			whoClicked.sendMessage(Messages.PREFIX + "Your rocket was deleted");
			whoClicked.closeInventory();
			whoClicked.playSound(whoClicked.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, SoundCategory.MASTER, 0.5f, 0.5f);
		}

	}

}
