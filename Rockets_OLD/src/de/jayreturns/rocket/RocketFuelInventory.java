package de.jayreturns.rocket;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.jayreturns.items.Items;
import de.jayreturns.util.ItemUtil;
import de.jayreturns.util.Messages;
import de.jayreturns.util.RocketUtil;

public class RocketFuelInventory implements Listener {

	private static final String prefix = "§0Fuel inventory of §8";

	private static final ItemStack empty = ItemUtil.createItem(Material.WHITE_STAINED_GLASS_PANE, "§fEmpty fuel slot");
	private static final ItemStack low = ItemUtil.createItem(Material.RED_STAINED_GLASS_PANE, "§cLow fuel slot");
	private static final ItemStack mid = ItemUtil.createItem(Material.ORANGE_STAINED_GLASS_PANE,
			"§6Almost low fuel slot");
	private static final ItemStack fuel = ItemUtil.createItem(Material.YELLOW_STAINED_GLASS_PANE, "§eFuel slot");

	public static void openFuelInventory(Player p) {
		Rocket r = Rocket.getRocket(p);

		if (RocketUtil.testDistanceToRocket(p)) {
//			p.sendMessage(Messages.prefix + "§cDu bist zu weit von deiner Rakete entfernt!");
			p.sendMessage(Messages.tooFar);
			return;
		}

		Inventory inv = Bukkit.createInventory(null, 54, prefix + p.getName());

		for (int i = 0; i < 54; i++) {
			if (i < RocketUtil.getFuelInvSize(r.getTier()))
				inv.setItem(i, empty);
			else
				inv.setItem(i, ItemUtil.getFiller());
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
		inv.setItem(49, ItemUtil.createItem(Material.BLAZE_POWDER, "§oInsert fuel here"));

		p.openInventory(inv);
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getView().getTitle().startsWith(prefix) && !(e.getClickedInventory().getHolder() instanceof Player)) {
			if (e.getCurrentItem().getType() == Material.BLAZE_POWDER) {
				if (e.getCursor().hasItemMeta()) {
					if (e.getCursor().isSimilar(Items.getRocketFuel())) {
						if (!Rocket.isFuelFull(p)) {
							p.sendMessage(Messages.prefix + "Fuel added");
							Rocket.addFuel(p);
							e.getCursor().setAmount(0);
							p.closeInventory();
							openFuelInventory(p);
						} else {
							p.sendMessage(Messages.prefix + "§cYour rocket is already filled to the top");
						}
					}
				}
			}
			e.setCancelled(true);
		}
	}

}
