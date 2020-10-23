package eu.mcuniverse.rocket.inventories;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.mcuniverse.rocket.data.Messages;
import eu.mcuniverse.rocket.data.Variables;
import eu.mcuniverse.rocket.main.Core;
import eu.mcuniverse.rocket.rocket.InRocketManager;
import eu.mcuniverse.rocket.rocket.Rocket;
import eu.mcuniverse.rocket.rocket.RocketManager;
import eu.mcuniverse.rocket.rocket.RocketPart.Hullarmor;
import eu.mcuniverse.rocket.rocket.RocketPart.Storage;
import eu.mcuniverse.rocket.rocket.RocketPart.WaterTank;
import eu.mcuniverse.rocket.util.ItemUtil;
import eu.mcuniverse.universeapi.api.UniverseAPI;
import eu.mcuniverse.universeapi.inventory.IGUI;
import eu.mcuniverse.universeapi.rockets.Planet;
import net.md_5.bungee.api.ChatColor;

public class RocketGUI implements IGUI {

	private Player p;

	public RocketGUI(Player player) {
		p = player;
	}

	@Override
	public Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(this, 27, ChatColor.AQUA + "Rocket menu");
		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, ItemUtil.getFiller());
		}
		if (!RocketManager.hasRocket(p)) {
			inv.setItem(3, ItemUtil.createItem(Material.NAME_TAG, ChatColor.GOLD + "Rocket info",
					ChatColor.RED + "You don't have a rocket"));
		} else {
			Rocket r = RocketManager.getRocket(p);
			inv.setItem(3,
					ItemUtil.createItem(Material.NAME_TAG, ChatColor.GOLD + "Rocket info", r.getInfos().toArray(new String[0])));
		}
		inv.setItem(26, ItemUtil.createItem(Material.BARRIER, "§cClose"));
		inv.setItem(23, ItemUtil.createItem(Material.REDSTONE_BLOCK, "§4Delete Rocket"));
		inv.setItem(1, ItemUtil.createItem(Material.BARREL, "§3Inventory"));
		inv.setItem(13, ItemUtil.createSkinItem(Material.BLAZE_ROD, "§3Upgrade", 10000014));
		inv.setItem(21, ItemUtil.createItem(Material.CAMPFIRE, "§aFuel"));
//		inv.setItem(13, ItemUtil.createItem(Material.LIME_CONCRETE, "§2Start"));
		inv.setItem(5, ItemUtil.createItem(Material.MINECART,
				"§b" + (Variables.inRocket.containsKey(p.getName()) ? "Leave" : "Enter") + " your rocket"));
//		inv.setItem(23,
//				ItemUtil.createItem(Material.EXPERIENCE_BOTTLE, "§aShow radius", "§8§oParticles have to be enabled!"));
		inv.setItem(11, ItemUtil.createSkinItem(Material.GLASS_BOTTLE, ChatColor.AQUA + "Water Tank", 10000020));
		inv.setItem(19, ItemUtil.createItem(Material.IRON_BLOCK, ChatColor.GRAY + "Hull"));
		
		return inv;
	}

	@Override
	public void onGUIClick(Player whoClicked, int slot, ItemStack clickedItem, ItemStack cusror) {
		if (clickedItem == null || clickedItem.getType() == Material.AIR || clickedItem.isSimilar(ItemUtil.getFiller())) {
			return;
		}

		if (clickedItem.getType() == Material.BARRIER) {
			whoClicked.closeInventory();
		}
		if (!RocketManager.hasRocket(whoClicked)) {
			p.sendMessage(Messages.NO_ROCkET);
			return;
		}

		Material type = clickedItem.getType();
		if (type == Material.REDSTONE_BLOCK) {
			p.openInventory(new DeleteGUI().getInventory());
		} else if (type == Material.BARREL) {
			if (RocketManager.getRocket(whoClicked).getStorage() == Storage.NONE) {
				whoClicked.sendMessage(UniverseAPI.getWarning() + "Your rocket doesn't have any storage!");
				return;
			}
			p.openInventory(new RocketStorageGUI(p).getInventory());
		} else if (type == Material.CAMPFIRE) {
			p.openInventory(new RocketFuelGUI(p).getInventory());
		} else if (type == Material.BLAZE_ROD) {
			p.openInventory(new UpgradeGUI(p).getInventory());
		} else if (type == Material.LIME_CONCRETE) {
			// TODO: Remove
			if (!Variables.inRocket.containsKey(p.getName())) {
				p.sendMessage(Messages.WARNING + "You're not in your rocket!");
				return;
			}
			p.openInventory(new RocketLaunchGUI(p, Planet.getPlanet(p)).getInventory());
		} else if (type == Material.MINECART) {
			InRocketManager.managePlayer(whoClicked);
		} else if (type == Material.EXPERIENCE_BOTTLE) {
			//TODO: Remove
			Location loc = RocketManager.getRocket(whoClicked).getLocation();
			double r = Core.getConfigManager().getSettings().getDouble("distanceToRocket");

			List<Location> locs = new ArrayList<>();
			for (double angle = 0; angle < 360; angle += 0.5) {
				double rad = Math.toRadians(angle);
				double x = r * Math.cos(rad);
				double z = r * Math.sin(rad);

				locs.add(new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY() + 2, loc.getBlockZ() + z));
			}
			locs.forEach(l -> {
				whoClicked.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(),
						10, 0, 10, 0, 0.1);
			});
			whoClicked.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc.getBlockX() + 0.5, loc.getBlockY(),
					loc.getBlockZ() + 0.5, 1000, 0, 255 - loc.getBlockY(), 0, 0.1);
		} else if (type == Material.GLASS_BOTTLE) {
			if (RocketManager.getRocket(p).getWaterTank().isAtLeast(WaterTank.TIER_ONE)) {
				p.openInventory(new WaterTankGUI(p).getInventory());
			} else {
				p.sendMessage(UniverseAPI.getWarning() + "Please upgrade your Water tank to at least Tier 1!");
			}
		} else if (type == Material.IRON_BLOCK) {
			if (RocketManager.getRocket(p).getHullarmor().isAtLeast(Hullarmor.TIER_ONE)) {
				p.openInventory(new HullGUI(p).getInventory());
			} else {
				p.sendMessage(UniverseAPI.getWarning() + "Please upgrade your Hull armor to at least Tier 1!");
			}
		}
	}

}
