package eu.mcuniverse.rocket.inventories;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.mcuniverse.rocket.rocket.Rocket;
import eu.mcuniverse.rocket.rocket.RocketManager;
import eu.mcuniverse.rocket.util.ItemUtil;
import eu.mcuniverse.universeapi.api.UniverseAPI;
import eu.mcuniverse.universeapi.inventory.IGUI;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.NonNull;
import net.md_5.bungee.api.ChatColor;

public class RocketStorageGUI implements IGUI {

	private Player p;

	public RocketStorageGUI(Player player) {
		this.p = player;
	}

	@Override
	public Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(this, 27, ChatColor.BLACK + "Rocket Inventory");

		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, ItemUtil.getFiller());
		}

		Rocket r = RocketManager.getRocket(p);
		for (int i : r.getStorage().getSlotIndices()) {
			inv.setItem(i, null);
		}

		ObjectArrayList<ItemStack> content = r.getInventory();
		List<Integer> indicies = r.getStorage().getSlotIndices();
		for (int i = 0; i < indicies.size(); i++) {
			try {
				inv.setItem(indicies.get(i), content.get(i));
			} catch (Exception e) {
				continue;
			}
		}

		return inv;
	}

	@Override
	public void onGUIClick(@NonNull Player whoClicked, int slot, ItemStack clickedItem, ItemStack cursor) {
		if (clickedItem == null || clickedItem.getType() == Material.AIR) {
			return;
		}

		if (clickedItem.isSimilar(ItemUtil.getFiller())) {
			return;
		}

	}
	
	public static boolean canItemBeAdded(Player p, ItemStack item) {
		if (item == null) {
			return true;
		}
		if (item.getType() == Material.FIREWORK_ROCKET) {
			sendMessage(p);
			return false;
		}
		if (item.hasItemMeta()) {
			ItemMeta meta = item.getItemMeta();
			if (meta.hasCustomModelData()) {
				sendMessage(p);
				return false;
			}
		}
		return true;
	}

	private static void sendMessage(Player player) {
		player.sendMessage(UniverseAPI.getWarning() + "You can't put that item into your rocket's inventory.");
	}

}