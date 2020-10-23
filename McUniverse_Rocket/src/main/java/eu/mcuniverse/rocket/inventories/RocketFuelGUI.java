package eu.mcuniverse.rocket.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.mcuniverse.rocket.rocket.Rocket;
import eu.mcuniverse.rocket.rocket.RocketManager;
import eu.mcuniverse.rocket.rocket.RocketPart.FuelTank;
import eu.mcuniverse.rocket.util.ItemUtil;
import eu.mcuniverse.universeapi.api.UniverseAPI;
import eu.mcuniverse.universeapi.inventory.IGUI;
import eu.mcuniverse.universeapi.item.CustomItem;
import lombok.NonNull;
import net.md_5.bungee.api.ChatColor;

public class RocketFuelGUI implements IGUI {

	private Player p;

	private final ItemStack empty = ItemUtil.createItem(Material.WHITE_STAINED_GLASS_PANE, "§fEmpty fuel slot");
	private final ItemStack low = ItemUtil.createItem(Material.RED_STAINED_GLASS_PANE, "§cLow fuel slot");
	private final ItemStack mid = ItemUtil.createItem(Material.ORANGE_STAINED_GLASS_PANE, "§6Almost low fuel slot");
	private final ItemStack fuel = ItemUtil.createItem(Material.YELLOW_STAINED_GLASS_PANE, "§eFuel slot");

	public RocketFuelGUI(Player player) {
		this.p = player;
	}

	@Override
	public Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(this, 27, "");
		
		// fill inventory
		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, i < 18 ? empty : ItemUtil.getFiller());
		}
		
		Rocket r = RocketManager.getRocket(p);
		
		// fill fuled slots
//		int slots = (int) (18 * (r.getFuelLevel() / 100.0));
		int slots = (int) r.getFuelLevel();
		slots = slots > 18 ? 18 : slots;
		for (int i = 0; i < slots; i++) {
			if (i < 3) {
				inv.setItem(i, low);
			} else if (i < 9) {
				inv.setItem(i, mid);
			} else {
				inv.setItem(i, fuel);
			}
		}
		inv.setItem(22, ItemUtil.createItem(Material.BLAZE_POWDER, ChatColor.GOLD + "" + ChatColor.ITALIC + "Insert fuel here"));
		
		return inv;
	}

	@Override
	public void onGUIClick(@NonNull Player whoClicked, int slot, ItemStack clickedItem, ItemStack cursor) {
		if (clickedItem == null || clickedItem.getType() == Material.AIR) {
			return;
		}
		
		if (clickedItem.getType() == Material.BLAZE_POWDER) {
			if (cursor.isSimilar(CustomItem.Rocket.getRocketFuel())) {
				if (!RocketManager.getRocket(whoClicked).getFuelTank().isAtLeast(FuelTank.TIER_ONE)) {
					whoClicked.sendMessage(UniverseAPI.getWarning() + "You don't have a fuel tank");
					return;
				}
				if (!RocketManager.isFuelFull(whoClicked)) {
					whoClicked.sendMessage(UniverseAPI.getPrefix() + "Fuel added");
					RocketManager.addFuel(whoClicked);
					cursor.setAmount(0);
					whoClicked.closeInventory();
					whoClicked.openInventory(new RocketFuelGUI(whoClicked).getInventory());
				} else {
					whoClicked.sendMessage(UniverseAPI.getWarning() + "Your rocket is already filled to the top!");
				}
			}
		}
		
	}
	
}
