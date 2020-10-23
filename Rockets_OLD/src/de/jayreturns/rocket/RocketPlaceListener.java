package de.jayreturns.rocket;

import java.io.File;
import java.io.FileInputStream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;

import de.jayreturns.main.Main;
import de.jayreturns.util.ItemUtil;
import de.jayreturns.util.Lists;
import de.jayreturns.util.Messages;
import de.jayreturns.util.RocketUtil;

public class RocketPlaceListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getMaterial() == Material.FIREWORK_ROCKET && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ItemStack item = e.getItem();
			if (item.hasItemMeta()) {
				ItemMeta im = item.getItemMeta();
				if (im.hasDisplayName()) {
					try {
						e.setCancelled(true);
						String name = im.getDisplayName();
						String roman = name.split(" ")[3].split("§")[0];
						int tier = getNumber(roman);

						Inventory inv = Bukkit.createInventory(null, 9, "Confirm rocket placement. Tier " + tier);
						for (int i = 0; i < inv.getSize(); i++) {
							inv.setItem(i, ItemUtil.createItem(Material.BLACK_STAINED_GLASS_PANE, " "));
						}
						inv.setItem(2, ItemUtil.createItem(Material.EMERALD_BLOCK, "Confirm", "Place the rocket"));
						inv.setItem(6, ItemUtil.createItem(Material.BARRIER, "Cancel", "Cancel Rocket placement"));

						Location l = e.getClickedBlock().getLocation();
						String loc = l.getWorld().getName() + ", " + l.getBlockX() + ", " + l.getBlockY() + ", "
								+ l.getBlockZ();
						int size = Main.configManager.getConfig().getInt("RocketTier" + tier + ".size");
						size = 2 * size + 1;
						String space = "§2Required space: §a" + size + "\u00D7" + size + "\u00D720";
						inv.setItem(4, ItemUtil.createItem(Material.NAME_TAG, "Rocket info", "Tier: " + tier,
								ChatColor.BLUE + loc, space));
						p.openInventory(inv);
					} catch (NumberFormatException exe) {
//						StringWriter sw = new StringWriter();
//						PrintWriter pw = new PrintWriter(sw);
//						exe.printStackTrace(pw); TODO:GOOD CODE
//						String stackTrace = sw.toString();
//						p.sendMessage(stackTrace);
						p.sendMessage(Messages.error + "Nummer konnte nicht konvertiert werden!");
					}
				}
			}
		}
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getView().getTitle().contains("Confirm rocket placement. Tier")) {
			e.setCancelled(true);
			if (e.getCurrentItem().getType() == Material.EMERALD_BLOCK) {
				if (Rocket.hasPlayerRocket(p)) {
					p.sendMessage(Messages.prefix + "§cYou already own a rocket! Use or destroy it to place a new one!");
					p.closeInventory();
					return;
				}

				try {
					int tier = Integer.parseInt(e.getView().getTitle().split("Tier")[1].trim());
					ItemStack item = e.getInventory().all(Material.NAME_TAG).get(4); // Get NameTag with infos
					if (!item.hasItemMeta()) {
						p.closeInventory();
						p.sendMessage(Messages.error + "Es ist ein Fehler aufgetreten");
						return;
					}
					if (!item.getItemMeta().hasLore()) {
						p.closeInventory();
						p.sendMessage(Messages.error + "Es ist ein Fehler aufgetreten");
						return;
					}
					String[] infos = item.getItemMeta().getLore().get(1).split(", "); // World, x, y, z
					World w = Bukkit.getServer().getWorld(ChatColor.stripColor(infos[0]));
					int x = Integer.parseInt(infos[1]);
					int y = Integer.parseInt(infos[2]);
					int z = Integer.parseInt(infos[3]);
					Location loc = new Location(w, x, y, z);
					if (!RocketUtil.isFloorFlat(loc, tier)) {
						p.sendMessage(Messages.prefix+ "§cThe floor is not stable!");
						return;
					}
					if (!RocketUtil.canRocketBePlaced(loc, tier)) {
						p.sendMessage(Messages.prefix + "§cYour rocket cannot be placed here!");
						return;
					}
					spawnRocket(tier, p, loc);
				} catch (Exception exe) {
					p.sendMessage(Messages.error + "Inventar konnte nicht konvertiert werden! " + exe.getMessage());
					exe.printStackTrace();
				}
			} else if (e.getCurrentItem().getType() == Material.REDSTONE_BLOCK)
				p.closeInventory();
		}
	}



	public static void spawnRocket(int tier, Player p, Location loc) {
		Location temp = loc;
		String name = "RocketTier" + tier;
		String fileName = Main.configManager.getConfig().getString(name + ".FileName");
		File file = new File("./plugins/WorldEdit/schematics/" + fileName);
		if (!file.exists()) {
			p.sendMessage(Messages.error + "Die Datei " + file.getName() + " existiert nicht!");
			return;
		}
		double xoff = Main.configManager.getConfig().getDouble(name + ".XOffset");
		double yoff = Main.configManager.getConfig().getDouble(name + ".YOffset");
		double zoff = Main.configManager.getConfig().getDouble(name + ".ZOffset");
		loc = loc.add(xoff, yoff, zoff);
		World world = loc.getWorld();

		ClipboardFormat format = ClipboardFormats.findByFile(file);
		try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
			Clipboard clipboard = reader.read();

			try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
					.getEditSession(BukkitAdapter.adapt(world), -1)) {
				Operation operation = new ClipboardHolder(clipboard).createPaste(editSession)
						.to(BlockVector3.at(loc.getBlockX(), loc.getBlockY() + 1, loc.getBlockZ()))
						.ignoreAirBlocks(false).build();
				Operations.complete(operation);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		double xspawnoff = Main.configManager.getConfig().getDouble(name + ".XSpawnOffset");
		double yspawnoff = Main.configManager.getConfig().getDouble(name + ".YSpawnOffset");
		double zspawnoff = Main.configManager.getConfig().getDouble(name + ".ZSpawnOffset");
		
		loc = temp.clone().add(xspawnoff, yspawnoff, zspawnoff);
		Lists.inRocket.put(p.getUniqueId(), p.getLocation());
		p.teleport(loc);

		@SuppressWarnings("unused")
		Rocket r = new Rocket(p, tier, temp);

		p.sendMessage("Rocket tier " + tier + " placed");
	}

	// Convert roman number (I-VI) to ints
	private static int getNumber(String number) throws NumberFormatException {
		if (number.equalsIgnoreCase("I"))
			return 1;
		else if (number.equalsIgnoreCase("II"))
			return 2;
		else if (number.equalsIgnoreCase("III"))
			return 3;
		else if (number.equalsIgnoreCase("IV"))
			return 4;
		else if (number.equalsIgnoreCase("V"))
			return 5;
		else if (number.equalsIgnoreCase("VI"))
			return 6;
		else
			throw new NumberFormatException("Roman number could not be converted to integer");
	}

}
