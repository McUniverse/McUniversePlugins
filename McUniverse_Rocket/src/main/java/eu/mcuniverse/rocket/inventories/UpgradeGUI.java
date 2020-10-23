package eu.mcuniverse.rocket.inventories;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.mcuniverse.rocket.data.Messages;
import eu.mcuniverse.rocket.rocket.Rocket;
import eu.mcuniverse.rocket.rocket.RocketManager;
import eu.mcuniverse.rocket.rocket.RocketPart;
import eu.mcuniverse.rocket.rocket.RocketPart.Part;
import eu.mcuniverse.rocket.rocket.RocketPart.Parts;
import eu.mcuniverse.rocket.upgrade.FancyUpgradeFormatter;
import eu.mcuniverse.rocket.upgrade.UpgradeRequirements;
import eu.mcuniverse.rocket.util.ItemUtil;
import eu.mcuniverse.universeapi.api.UniverseAPI;
import eu.mcuniverse.universeapi.inventory.IGUI;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.md_5.bungee.api.ChatColor;

public class UpgradeGUI implements IGUI {

	@Accessors(prefix = "current")
	@Getter
	private int currentPage = 1;
	private int maxPage = 2;
	private Player p;
	private Inventory inv;

	private boolean nextPage = true;

	private final String KEY = "part";
	private final int ROCKET_SLOT = 17;

	public UpgradeGUI(Player player) {
		this.p = player;
	}

	@Override
	public Inventory getInventory() {
		if (inv != null && !nextPage) {
			return inv;
		}
		inv = Bukkit.createInventory(this, 54,
				ChatColor.DARK_GREEN + "Upgrade your rocket " + ChatColor.GRAY + "(" + currentPage + "/" + maxPage + ")");

		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, ItemUtil.getFiller());
		}

		Rocket r = RocketManager.getRocket(p);
		inv.setItem(ROCKET_SLOT, r.getItem());
		inv.setItem(35, ItemUtil.createItem(Material.PAINTING, ChatColor.YELLOW + "Skin",
				ChatColor.RED + "" + ChatColor.ITALIC + "WIP"));

		int start = (currentPage == 1 ? 0 : 6);
		int upper = (currentPage == 1 ? 6 : RocketPart.Parts.values().length);
		for (int i = start; i < upper; i++) {
			RocketPart.Parts part = RocketPart.Parts.getPart(i + 1);
			int j = i;
			if (currentPage == 2) {
				j -= 6;
			}
			inv.setItem(9 * j, part.getItem());
			inv.setItem(9 * j + 2, ItemUtil.createItem(Material.NAME_TAG,
					ChatColor.GOLD + "Current Tier: " + ChatColor.BLUE + "" + ChatColor.BOLD + r.getPartTier(part)));

			inv.setItem(9 * j + 4, ItemUtil.createItem(Material.PAPER, ChatColor.GOLD + "Next tier cost:", getLore(part, r)));

			// TODO: Add red block if not upgradable
			ItemStack upgrade;
			if (r.isMaxTier(part)) {
				upgrade = ItemUtil.createEnchantedItem(Material.RED_CONCRETE, ChatColor.RED + "Maxed out");
			} else {
				upgrade = ItemUtil.createEnchantedItem(Material.LIME_CONCRETE, ChatColor.GOLD + "Upgrade");
			}
			upgrade = ItemUtil.NBT.addNBTTag(upgrade, "part", part.toString());
			inv.setItem(9 * j + 6, upgrade);
		}

		String name = ChatColor.AQUA + "" + ChatColor.BOLD;
		if (currentPage == 1) {
			name += "Next Page";
		} else if (currentPage == 2) {
			name += "Previous Page";
		}
		inv.setItem(53, ItemUtil.createItem(Material.ARROW, name));

		nextPage = false;
		return inv;
	}

	@Override
	public void onGUIClick(Player whoClicked, int slot, ItemStack clickedItem, ItemStack cursor) {
		if (clickedItem == null || clickedItem.getType().equals(Material.AIR))
			return;

		if (clickedItem.getType() == Material.PAINTING) {
			whoClicked.openInventory(new SkinGUI().getInventory());
		} else if (clickedItem.getType() == Material.LIME_CONCRETE) {
			ItemStack item = clickedItem.clone();
			String part = ItemUtil.NBT.getValue(item, KEY);
			RocketPart.Parts p = Parts.valueOf(part.toUpperCase());
			Rocket r = RocketManager.getRocket(whoClicked);

			int row = slot / 9;

			Object2IntMap<ItemStack> items = UpgradeRequirements.getRequirements(getPart(p, r));
			boolean hasItems = true;
			for (Entry<ItemStack> entry : items.object2IntEntrySet()) {
				if (!whoClicked.getInventory().containsAtLeast(entry.getKey(), entry.getIntValue())) {
					hasItems = false;
					break;
				}
			}
			if (!hasItems) {
				whoClicked.sendMessage(UniverseAPI.getWarning() + "You don't have the required items to upgrade!");
				whoClicked.playSound(whoClicked.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, SoundCategory.MASTER, 1, 1);
				return;
			} else {
				items.forEach((stack, amount) -> {
					ItemStack toRemove = new ItemStack(stack);
					toRemove.setAmount(amount);
					whoClicked.getInventory().removeItem(toRemove);
				});
//				whoClicked.updateInventory();
			}

			if (!r.upgrade(p)) {
				whoClicked.playSound(whoClicked.getLocation(), Sound.ENTITY_HORSE_SADDLE, SoundCategory.MASTER, 1, 0.5f);
				whoClicked.sendMessage(Messages.WARNING + "This part is already maxed out!");
				getInventory().setItem(9 * row + 6,
						ItemUtil.createEnchantedItem(Material.RED_CONCRETE, ChatColor.RED + "Maxed out"));
				return;
			}

			r.saveRocket();
			ItemStack nameTag = getInventory().getItem(9 * row + 2);
			ItemMeta meta = nameTag.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "Current Tier: " + ChatColor.BLUE + "" + ChatColor.BOLD + r.getPartTier(p));
			nameTag.setItemMeta(meta);
			getInventory().setItem(9 * row + 2, nameTag);

			getInventory().setItem(ROCKET_SLOT, r.getItem());

			// Next tier cost
			ItemStack paper = getInventory().getItem(9 * row + 4);
			ItemMeta paperMeta = paper.getItemMeta();

			paperMeta.setLore(getLore(p, r));
			paper.setItemMeta(paperMeta);

			getInventory().setItem(9 * row + 4, paper);

			whoClicked.playSound(whoClicked.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 1, 2);
			whoClicked.sendMessage(Messages.PREFIX + "Successfully upgraded your rocket!");
		} else if (clickedItem.getType() == Material.ARROW) {
			if (currentPage == 1) {
				whoClicked.openInventory(this.setPage(this.getPage() + 1).getInventory());
			} else if (currentPage == 2) {
				whoClicked.openInventory(this.setPage(this.getPage() - 1).getInventory());
			}
		}
	}

	public UpgradeGUI setPage(int page) {
		if (page < 0) {
			currentPage = 0;
		} else if (page > maxPage) {
			currentPage = maxPage;
		} else {
			currentPage = page;
		}
		nextPage = true;
		return this;
	}

	private List<String> getLore(RocketPart.Parts part, Rocket r) {
		List<String> lore = new ArrayList<>();

		switch (part) {
		case AIR_TANK:
			lore = FancyUpgradeFormatter.getLore(UpgradeRequirements.getRequirements(r.getAirTank()));
			break;
		case WATER_TANK:
			lore = FancyUpgradeFormatter.getLore(UpgradeRequirements.getRequirements(r.getWaterTank()));
			break;
		case BOARDCOMPUTER:
			lore = FancyUpgradeFormatter.getLore(UpgradeRequirements.getRequirements(r.getBoardcomputer()));
			break;
		case FROST_SHIELD:
			lore = FancyUpgradeFormatter.getLore(UpgradeRequirements.getRequirements(r.getFrostshield()));
			break;
		case FUEL_TANK:
			lore = FancyUpgradeFormatter.getLore(UpgradeRequirements.getRequirements(r.getFuelTank()));
			break;
		case HEAT_SHIELD:
			lore = FancyUpgradeFormatter.getLore(UpgradeRequirements.getRequirements(r.getHeatshield()));
			break;
		case HULL_ARMOR:
			lore = FancyUpgradeFormatter.getLore(UpgradeRequirements.getRequirements(r.getHullarmor()));
			break;
		case STORAGE:
			lore = FancyUpgradeFormatter.getLore(UpgradeRequirements.getRequirements(r.getStorage()));
			break;
		default:
			break;
		}
		return lore;
	}

	private Part getPart(RocketPart.Parts part, Rocket r) {
		switch (part) {
		case AIR_TANK:
			return r.getAirTank();
		case WATER_TANK:
			return r.getWaterTank();
		case BOARDCOMPUTER:
			return r.getBoardcomputer();
		case FROST_SHIELD:
			return r.getFrostshield();
		case FUEL_TANK:
			return r.getFuelTank();
		case HEAT_SHIELD:
			return r.getHeatshield();
		case HULL_ARMOR:
			return r.getHullarmor();
		case STORAGE:
			return r.getStorage();
		default:
			break;
		}
		return null;
	}

}
