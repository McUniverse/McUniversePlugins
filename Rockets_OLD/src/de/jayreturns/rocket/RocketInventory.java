package de.jayreturns.rocket;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.jayreturns.main.Main;
import de.jayreturns.util.ItemUtil;
import de.jayreturns.util.Messages;
import de.jayreturns.util.RocketUtil;

public class RocketInventory implements Listener {

	private static final String prefix = "§0Rocket inventory of §8";

	@SuppressWarnings("unchecked")
	public static void openRocketInventory(Player p) {
		Rocket r = Rocket.getRocket(p);

		if (RocketUtil.testDistanceToRocket(p)) {
//			p.sendMessage(Messages.prefix + "§cDu bist zu weit von deiner Rakete entfernt!");
			p.sendMessage(Messages.tooFar);
			return;
		}

//		if (p.getLocation().getWorld().getName().equalsIgnoreCase(r.getLocation().getWorld().getName())
//				|| p.getLocation().distance(r.getLocation()) > Main.configManager.getConfig()
//						.getDouble("distanceForInventory")) {
//			p.sendMessage(Messages.prefix + "§cDu bist zu weit von deiner Rakete entfernt!");
//			return;
//		}

		Inventory inv = Bukkit.createInventory(null, 27, prefix + p.getName());

		for (int i = 0; i < 27; i++) {
			// Add flag to identify item in invclickevent
			ItemStack filler = ItemUtil.createItem(Material.GRAY_STAINED_GLASS_PANE, " ");
			ItemMeta fillerMeta = filler.getItemMeta();
			fillerMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			filler.setItemMeta(fillerMeta);

			inv.setItem(i, filler);
		}

		for (int i : RocketUtil.getSlotIndecies(r.getTier())) {
			inv.setItem(i, null);
		}

		List<ItemStack> content = (List<ItemStack>) Main.configManager.getRockets()
				.getList(p.getUniqueId().toString() + ".content");
		List<Integer> indecies = RocketUtil.getSlotIndecies(r.getTier());
		for (int i = 0; i < indecies.size(); i++) {
			try {
				inv.setItem(indecies.get(i), content.get(i));
			} catch (Exception exe) {
				break;
			}
		}
		p.openInventory(inv);
	}

	@EventHandler
	public void onInvClose(InventoryCloseEvent e) {
		if (e.getView().getTitle().startsWith(prefix)) {
			Player p = (Player) e.getPlayer();
			ItemStack[] con = e.getInventory().getContents();
			int tier = Rocket.getRocket(p).getTier();
			List<ItemStack> content = new ArrayList<>();
			RocketUtil.getSlotIndecies(tier).forEach(index -> {
				if (con[index] != null)
					content.add(con[index]);
			});
			Rocket.saveInventory(p, content);
		}
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (e.getView().getTitle().startsWith(prefix)) {
			if (e.getCurrentItem() != null) {
				ItemStack item = e.getCurrentItem();
				if (item.getType() == Material.GRAY_STAINED_GLASS_PANE) {
					if (item.hasItemMeta()) {
						if (item.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_UNBREAKABLE)) {
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}
}
