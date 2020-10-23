package eu.mcuniverse.rocket.inventories;

import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.mcuniverse.rocket.rocket.Rocket;
import eu.mcuniverse.rocket.rocket.RocketManager;
import eu.mcuniverse.universeapi.api.UniverseAPI;
import eu.mcuniverse.universeapi.inventory.IGUI;
import eu.mcuniverse.universeapi.item.CustomItem;
import eu.mcuniverse.universeapi.util.ItemUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;

@RequiredArgsConstructor
public class HullGUI implements IGUI {

	@NonNull
	private Player player;
	private Rocket rocket = null;
	private Inventory inv = null;

	@Override
	public Inventory getInventory() {
		inv = Bukkit.createInventory(this, 27, "Hull armor");
		IntStream.range(0, inv.getSize()).forEach(i -> inv.setItem(i, ItemUtil.getFiller()));

		rocket = RocketManager.getRocket(player);

		inv.setItem(10, ItemUtil.createItem(Material.IRON_BLOCK, ChatColor.GRAY + "Hull"));
		double percent = (double) rocket.getHullHits() / (double) rocket.getHullarmor().getMaxCollisions();
		percent *= 100;
		String damage = ChatColor.LIGHT_PURPLE + "" + ((int) percent) + "%" + ChatColor.GRAY + " Damage";
		inv.setItem(11, ItemUtil.createItem(Material.NAME_TAG, damage));

		inv.setItem(14, ItemUtil.createItem(Material.PAPER, ChatColor.RED + "Repair cost",
				ChatColor.GRAY + " - " + ChatColor.YELLOW + " 1\u00D7 " + ChatColor.GOLD + "Enhanced Iron"));
		inv.setItem(15, null);
		inv.setItem(16, ItemUtil.createItem(Material.LIME_CONCRETE, ChatColor.GREEN + "Repair"));

		return inv;
	}

	@Override
	public void onGUIClick(@NonNull Player whoClicked, int slot, ItemStack clickedItem, ItemStack cursor) {
		if (clickedItem == null || clickedItem.getType() == Material.AIR) {
			return;
		}

		if (clickedItem.getType() == Material.LIME_CONCRETE) {
			if (rocket.getHullHits() == 0) {
				whoClicked.sendMessage(UniverseAPI.getWarning() + "Your rocket is in top condition!");
				return;
			}
			if (!whoClicked.getInventory().containsAtLeast(CustomItem.enhancedIron, 1)) {
				whoClicked.playSound(whoClicked.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, SoundCategory.MASTER, 1f, 1f);
				whoClicked.sendMessage(UniverseAPI.getWarning() + "You don't have the required items!");
				return;
			}
			rocket.setHullHits(rocket.getHullHits() - 1);
			if (rocket.getHullHits() < 0) {
				rocket.setHullHits(0);
			}
			rocket.saveRocket();
			
			rocket = RocketManager.getRocket(whoClicked);
			double percent = (double) rocket.getHullHits() / (double) rocket.getHullarmor().getMaxCollisions();
			percent *= 100;
			String damage = ChatColor.LIGHT_PURPLE + "" + ((int) percent) + "%" + ChatColor.GRAY + " Damage";
			inv.setItem(11, ItemUtil.createItem(Material.NAME_TAG, damage));

			whoClicked.playSound(whoClicked.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 1f, 2f);
			whoClicked.sendMessage(UniverseAPI.getPrefix() + "Successfully repaired your rocket");
		}

	}

}
