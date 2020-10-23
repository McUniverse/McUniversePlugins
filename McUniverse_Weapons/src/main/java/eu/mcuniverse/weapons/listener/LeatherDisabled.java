package eu.mcuniverse.weapons.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import eu.mcuniverse.weapons.main.Core;

public class LeatherDisabled implements Listener {

	public LeatherDisabled(final Core plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onCraft(PrepareItemCraftEvent e) {
		if (e.getInventory() == null || e.getInventory().getResult() == null) {
			return;
		}
		if (containsDye(e.getInventory()) && containsLeather(e.getInventory())) {
			e.getInventory().setResult(null);
		}
//		if (e.getInventory().getResult().getType().name().startsWith("LEATHER_")) {
//			if (e.getInventory().getResult().getType() != Material.LEATHER_HORSE_ARMOR) {
//				e.getInventory().setResult(null);
//			}
//		}
	}

	@EventHandler
	public void onCauldronClick(PlayerInteractEvent e) {
		if (!e.hasBlock() || !e.hasItem()) {
			return;
		}
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		if (e.getClickedBlock().getType() == Material.CAULDRON) {
			if (e.getMaterial() == Material.LEATHER_HELMET || e.getMaterial() == Material.LEATHER_CHESTPLATE
					|| e.getMaterial() == Material.LEATHER_LEGGINGS || e.getMaterial() == Material.LEATHER_BOOTS) {
				e.setCancelled(true);
			}
		}
	}

	private boolean containsDye(CraftingInventory inv) {
		for (ItemStack item : inv.getMatrix()) {
			if (item == null)
				continue;
			if (item.getType().toString().contains("DYE"))
				return true;
		}
		return false;
	}

	private boolean containsLeather(CraftingInventory inv) {
		for (ItemStack item : inv.getMatrix()) {
			if (item == null)
				continue;
			if (item.getType() == Material.LEATHER_HELMET || item.getType() == Material.LEATHER_CHESTPLATE
					|| item.getType() == Material.LEATHER_LEGGINGS || item.getType() == Material.LEATHER_BOOTS)
				return true;
		}
		return false;
	}

}
