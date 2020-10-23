 package eu.mcuniverse.rocket.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.mcuniverse.rocket.data.Messages;
import eu.mcuniverse.rocket.items.Items;
import eu.mcuniverse.rocket.rocket.Rocket;
import eu.mcuniverse.rocket.rocket.RocketManager;
import eu.mcuniverse.rocket.util.ItemUtil;
import eu.mcuniverse.rocket.util.RocketUtil;
import eu.mcuniverse.universeapi.inventory.IGUI;
import net.md_5.bungee.api.ChatColor;

public class RocketFuelGUI_old implements IGUI {

	private Player p;
	
	private final ItemStack empty = ItemUtil.createItem(Material.WHITE_STAINED_GLASS_PANE, "§fEmpty fuel slot");
	private final ItemStack low = ItemUtil.createItem(Material.RED_STAINED_GLASS_PANE, "§cLow fuel slot");
	private final ItemStack mid = ItemUtil.createItem(Material.ORANGE_STAINED_GLASS_PANE, "§6Almost low fuel slot");
	private final ItemStack fuel = ItemUtil.createItem(Material.YELLOW_STAINED_GLASS_PANE, "§eFuel slot");

	public RocketFuelGUI_old(Player player) {
		this.p = player;
	}
	
	@Override
	public Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(this, 54, ChatColor.BLACK + "Fuel inventory");
			
		Rocket r = RocketManager.getRocket(p);
		for (int i = 0; i < inv.getSize(); i++) {
			if (i < RocketUtil.getFuelInvSize(r.getTier())) {
				inv.setItem(i, empty);
			} else {
				inv.setItem(i, ItemUtil.getFiller());;
			}
		}
		
		for (int i = 0; i < r.getFuelLevel(); i++) {
			if (r.getTier() == 1 || r.getTier() == 2) {
				if (i == 0)
					inv.setItem(i, low);
				else if (i == 1 || i == 2)
					inv.setItem(i, mid);
				else
					inv.setItem(i, fuel);
			} else if (r.getTier() == 3 || r.getTier() == 4) {
				if (i >= 0 && i < 2)
					inv.setItem(i, low);
				else if (i >= 2 && i < 6)
					inv.setItem(i, mid);
				else
					inv.setItem(i, fuel);
			} else if (r.getTier() == 5 || r.getTier() == 6) {
				if (i >= 0 && i < 3)
					inv.setItem(i, low);
				else if (i >= 3 && i < 9)
					inv.setItem(i, mid);
				else
					inv.setItem(i, fuel);
			}
		}
		inv.setItem(49, ItemUtil.createItem(Material.BLAZE_POWDER, ChatColor.ITALIC + "Insert fuel here"));
		
		return inv;
	}

	@Override
	public void onGUIClick(Player whoClicked, int slot, ItemStack clickedItem, ItemStack cursor) {
		if (clickedItem == null || clickedItem.getType() == Material.AIR) {
			return;
		}
		
		if (clickedItem.getType() == Material.BLAZE_POWDER) {
			if (cursor.isSimilar(Items.getRocketFuel())) {
				if (!RocketManager.isFuelFull(whoClicked)) {
					whoClicked.sendMessage(Messages.PREFIX + "Fuel added!");
					RocketManager.addFuel(whoClicked);
					cursor.setAmount(0);
					whoClicked.closeInventory();
					whoClicked.openInventory(new RocketFuelGUI(whoClicked).getInventory());
				} else {
					whoClicked.sendMessage(Messages.WARNING + "Your rocket is already filled to the top");
				}
			}
		} else {
			return;
		}
	}

}
