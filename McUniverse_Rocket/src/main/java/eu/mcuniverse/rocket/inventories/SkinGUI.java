package eu.mcuniverse.rocket.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.mcuniverse.rocket.rocket.Rocket;
import eu.mcuniverse.rocket.rocket.RocketManager;
import eu.mcuniverse.rocket.rocket.Skin;
import eu.mcuniverse.rocket.util.ItemUtil;
import eu.mcuniverse.universeapi.inventory.IGUI;
import net.md_5.bungee.api.ChatColor;

public class SkinGUI implements IGUI {

	@Override
	public Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(this, 9, ChatColor.GOLD + "Skin");
		
		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, ItemUtil.getFiller());
		}
		
		for (int i = 0; i < Skin.values().length; i++) {
			inv.setItem(i, Skin.values()[i].getItem());
		}
		
		return inv;
	}

	@Override
	public void onGUIClick(Player whoClicked, int slot, ItemStack clickedItem, ItemStack cursor) {
		if (clickedItem == null || clickedItem.getType() == Material.AIR || !clickedItem.hasItemMeta()) {
			return;
		}
		
		// TODO: Reduce 
		// TODO: Cleanup
		Rocket r = RocketManager.getRocket(whoClicked);
		ItemMeta meta = clickedItem.getItemMeta();
		int id = meta.getCustomModelData();
		Skin s = null;
		
		if (id == Skin.ROCKET.getCustomModelData()) {
			s = Skin.ROCKET;
			r.setFacingUp();
//			r.setLayingDown();
		} else if (id == Skin.SHUTTLE.getCustomModelData()) {
			s = Skin.SHUTTLE;
			r.setLayingDown();
		} else if (id == Skin.SATURN_V.getCustomModelData()) {
			s = Skin.SATURN_V;
//			r.setFacingUp();
			r.setArmorStandPose(0);
		}
		
		if (s != null) {
			r.setSkin(s);
			r.setItemInHand(s);
//			r.respawn();
			r.saveRocket();
		}
	}

}
