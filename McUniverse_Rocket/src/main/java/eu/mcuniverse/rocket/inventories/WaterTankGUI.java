package eu.mcuniverse.rocket.inventories;

import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.mcuniverse.rocket.rocket.Rocket;
import eu.mcuniverse.rocket.rocket.RocketManager;
import eu.mcuniverse.universeapi.inventory.IGUI;
import eu.mcuniverse.universeapi.util.ItemUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WaterTankGUI implements IGUI {

	@NonNull
	private Player player;
	@NonNull
	private Rocket rocket = null;

	@Override
	public Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(this, 27, "Water Storage");
		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, ItemUtil.getFiller());
		}

		rocket = RocketManager.getRocket(player);
		AtomicInteger count = new AtomicInteger(0);
		if (rocket.getWaterTank() != null) {
			rocket.getWaterTank().getSlotIndices().forEach(i -> {
				if (count.incrementAndGet() > rocket.getWaterLevel()) {
					inv.setItem(i, null);
				} else {
					inv.setItem(i, new ItemStack(Material.WATER_BUCKET));
				}
			});
		}

		return inv;
	}

	@Override
	public void onGUIClick(@NonNull Player whoClicked, int slot, ItemStack clickedItem, ItemStack cursor) {

	}

}
