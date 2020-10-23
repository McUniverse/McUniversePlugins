package de.jayreturns.rocket;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import de.jayreturns.main.Main;
import de.jayreturns.planets.Planet;
import de.jayreturns.util.ItemUtil;
import de.jayreturns.util.Lists;
import de.jayreturns.util.Messages;
import de.jayreturns.util.RocketUtil;

public class RocketMenu implements CommandExecutor, Listener {

	private Inventory inv;
	private Inventory confirmInv;
	private String title = "§bRocket Menu";

	public RocketMenu() {
		// Initilize inventory with GUI
		inv = Bukkit.createInventory(null, 27, title);
		for (int i = 0; i < 27; i++) {
			inv.setItem(i, ItemUtil.getFiller());
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Messages.error + "Du bist kein Spieler!");
			return true;
		}
		((Player) sender).openInventory(inv);

		fillInventory((Player) sender);
		return true;
	}

	@EventHandler
	public void onStickClick(PlayerInteractEvent e) {
		if (e.getAction().toString().contains("RIGHT")) {
			if (e.getMaterial() == Material.STICK) {
				if (e.getItem().hasItemMeta()) {
					if (e.getItem().getItemMeta().getDisplayName().contains("Rocket")) {
						e.getPlayer().performCommand("myrocket");
					} else if (e.getItem().getItemMeta().getDisplayName().contains("Teleport")) {
						RocketTeleporter.findNewLocation(e.getPlayer(), Planet.MARS);
					} else if (e.getItem().getItemMeta().getDisplayName().contains("Radius")) {
						Location l = Rocket.getRocket(e.getPlayer()).getLocation();
						double r = Main.configManager.getConfig().getDouble("distanceForInventory");

						List<Location> locs = new LinkedList<>();

						for (double angle = 0; angle < 360; angle += 0.5) {
							double rad = Math.toRadians(angle);
							double x = r * Math.cos(rad);
							double z = r * Math.sin(rad);

							locs.add(new Location(l.getWorld(), l.getBlockX() + x, l.getBlockY(), l.getBlockZ() + z));
						}
//						e.getPlayer().getWorld().spawnParticle(Particle.COMPOSTER, l.getBlockX(), l.getBlockY(), l.getBlockZ(), 1);
						for (Location loc : locs) {
							e.getPlayer().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc.getBlockX(),
									loc.getBlockY(), loc.getBlockZ(), 10, 0, 10, 0, 0.1);
						}
						e.getPlayer().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, l.getBlockX() + 0.5,
								l.getBlockY(), l.getBlockZ() + 0.5, 1000, 0, 255 - l.getBlockY(), 0, 0.1);
					}
				}
			}
		}
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (e.getCurrentItem() == null)
			return;
		if (!(e.getWhoClicked() instanceof Player))
			return;

		Player p = (Player) e.getWhoClicked();
		if (e.getView().getTitle().equalsIgnoreCase(title)) {
			e.setCancelled(true);
			if (e.getCurrentItem().getType() == Material.REDSTONE_BLOCK) {
				createConfirmInv("Destroy your rocket?");
				if (!Rocket.hasPlayerRocket(p)) {
					p.sendMessage(Messages.noRocket);
					return;
				}
				p.openInventory(confirmInv);
			} else if (e.getCurrentItem().getType() == Material.BARRIER) {
				p.closeInventory();
			} else if (e.getCurrentItem().getType() == Material.BARREL) {
				if (Rocket.hasPlayerRocket(p)) {
					RocketInventory.openRocketInventory(p);
				} else {
					p.sendMessage(Messages.noRocket);
				}
			} else if (e.getCurrentItem().getType() == Material.CAMPFIRE) {
				RocketFuelInventory.openFuelInventory(p);
			} else if (e.getCurrentItem().getType() == Material.LIME_CONCRETE) {
				if (!Lists.inRocket.containsKey(p.getUniqueId())) {
					p.sendMessage(Messages.prefix + "§cYou're not in your rocket!" );
					return;
				}
				RocketLauncher.openLaunchMenu(p);
			} else if (e.getCurrentItem().getType() == Material.MINECART) {
				if (Lists.inRocket.containsKey(p.getUniqueId())) {
					p.teleport(Lists.inRocket.get(p.getUniqueId()));
					Lists.inRocket.remove(p.getUniqueId());
				} else {
					if (RocketUtil.testDistanceToRocket(p)) {
						p.sendMessage(Messages.tooFar);
						return;
					}
					Lists.inRocket.put(p.getUniqueId(), p.getLocation());
					p.teleport(Rocket.getSeat(p));
				}
			} else if (e.getCurrentItem().getType() == Material.EXPERIENCE_BOTTLE) {
				Location l = Rocket.getRocket(p).getLocation();
				double r = Main.configManager.getConfig().getDouble("distanceForInventory");

				List<Location> locs = new LinkedList<>();

				for (double angle = 0; angle < 360; angle += 0.5) {
					double rad = Math.toRadians(angle);
					double x = r * Math.cos(rad);
					double z = r * Math.sin(rad);

					locs.add(new Location(l.getWorld(), l.getBlockX() + x, l.getBlockY(), l.getBlockZ() + z));
				}
				for (Location loc : locs) {
					p.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc.getBlockX(), loc.getBlockY(),
							loc.getBlockZ(), 10, 0, 10, 0, 0.1);
				}
				p.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, l.getBlockX() + 0.5, l.getBlockY(),
						l.getBlockZ() + 0.5, 1000, 0, 255 - l.getBlockY(), 0, 0.1);
			}
		} else if (e.getView().getTitle().startsWith("§aConfirm")) {
			e.setCancelled(true);
			if (e.getView().getTitle().contains("Destroy your rocket?") && e.getCurrentItem() != null) {
				if (e.getCurrentItem().getType() == Material.LIME_WOOL) {
					Rocket.deleteRocket(p);
					p.sendMessage(Messages.prefix + "Deine Rakete wurde zerstört!");
					p.closeInventory();
				} else if (e.getCurrentItem().getType() == Material.RED_WOOL) {
					p.closeInventory();
				}
			}
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (Lists.inRocket.containsKey(p.getUniqueId())) {
			p.teleport(Lists.inRocket.get(p.getUniqueId()));
			Lists.inRocket.remove(p.getUniqueId());
		}
	}

	private void fillInventory(Player p) {
		if (!Rocket.hasPlayerRocket(p)) {
			inv.setItem(3, ItemUtil.createItem(Material.NAME_TAG, "§6Rocket Info", "§cYou don't have a rocket"));
		} else {
			Rocket r = Rocket.getRocket(p);
			String[] lore = new String[3];
			lore[0] = "§9Tier: §e" + r.getTier();
			lore[1] = "§9Location: §e" + r.getLocation().getWorld().getName() + ", " + r.getLocation().getBlockX()
					+ ", " + r.getLocation().getBlockY() + ", " + r.getLocation().getBlockZ();
			lore[2] = "§9Fuel: §e" + r.getFuel() + "%";
			inv.setItem(3, ItemUtil.createItem(Material.NAME_TAG, "§6Rocket Info", lore));
		}
		inv.setItem(26, ItemUtil.createItem(Material.BARRIER, "§cClose"));
		inv.setItem(15, ItemUtil.createItem(Material.REDSTONE_BLOCK, "§4Delete Rocket"));
		inv.setItem(11, ItemUtil.createItem(Material.BARREL, "§3Inventory"));
		inv.setItem(21, ItemUtil.createItem(Material.CAMPFIRE, "§aFuel"));
		inv.setItem(13, ItemUtil.createItem(Material.LIME_CONCRETE, "§2Start"));
		inv.setItem(5, ItemUtil.createItem(Material.MINECART,
				"§b" + (Lists.inRocket.containsKey(p.getUniqueId()) ? "Leave" : "Enter") + " your rocket"));
		inv.setItem(23, ItemUtil.createItem(Material.EXPERIENCE_BOTTLE, "§aShow radius", "§8§oParticles have to be enabled!"));
	}

	private void createConfirmInv(String title) {
		confirmInv = Bukkit.createInventory(null, 27, "§aConfirm: " + title);
		for (int i = 0; i < 27; i++) {
			confirmInv.setItem(i, ItemUtil.createItem(Material.BLACK_STAINED_GLASS_PANE, " "));
		}

		// Confirm with green wool
		Arrays.asList(1, 2, 3, 10, 11, 12, 19, 20, 21).forEach(slot -> {
			confirmInv.setItem(slot, ItemUtil.createItem(Material.LIME_WOOL, "§2Confirm"));
		});
		;

		Arrays.asList(5, 6, 7, 14, 15, 16, 23, 24, 25).forEach(slot -> {
			confirmInv.setItem(slot, ItemUtil.createItem(Material.RED_WOOL, "§4Cancel"));
		});
	}

}