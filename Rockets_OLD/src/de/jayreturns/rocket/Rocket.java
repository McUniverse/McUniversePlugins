package de.jayreturns.rocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.jayreturns.main.Main;
import de.jayreturns.util.Messages;
import de.jayreturns.util.RocketUtil;

public class Rocket {

	/*
	 * Rockets.yml JayReturns(UUID): invSize: 10 tier: 3 loc: (10, 10, 10) fuel: 0
	 * content: - material:damage:amount:...
	 */

	private Player owner;
	private int invSize;
	private List<ItemStack> content;
	private int tier;
	private Location loc;
	private int fuelLevel; // 1 = 1 pane

	// Constructor for a new rocket
	public Rocket(Player p, int rocketTier, Location spawnLoc) {
		owner = p;
		invSize = RocketUtil.getInvSize(rocketTier);
		content = new ArrayList<ItemStack>();
		tier = rocketTier;
		loc = spawnLoc;
		fuelLevel = 0;

		String n = p.getUniqueId().toString();
		FileConfiguration c = Main.configManager.getRockets();
		c.set(n + ".invSize", RocketUtil.getInvSize(tier));
		c.set(n + ".tier", tier);
		c.set(n + ".loc", loc);
		c.set(n + ".fuel", 0);
		c.set(n + ".content", content);

		try {
			Main.configManager.saveRocketsFile();
		} catch (IOException e) {
			p.sendMessage(Messages.error + "An error occured!");
			e.printStackTrace();
		}
	}

	// Constructor to get an existing rocket
	public Rocket(Player p, int inv, List<ItemStack> inventory, int rocketTier, Location spawnLoc, int fuel) {
		owner = p;
		invSize = inv;
		content = inventory;
		tier = rocketTier;
		loc = spawnLoc;
		fuelLevel = fuel;
	}

	public Player getOwner() {
		return owner;
	}

	public int getInvSize() {
		return invSize;
	}

	public List<ItemStack> getContent() {
		return content;
	}

	public int getTier() {
		return tier;
	}

	public Location getLocation() {
		return loc.clone();
	}

	public int getFuel() {
		double ret = (double) fuelLevel / (double) RocketUtil.getMaxFuel(tier);
		return (int) (ret * 100);
	}

	public int getFuelLevel() {
		return fuelLevel;
	}

	public static void saveInventory(Player p, List<ItemStack> content) {
		Main.configManager.getRockets().set(p.getUniqueId().toString() + ".content", content);
		try {
			Main.configManager.saveRocketsFile();
		} catch (Exception e) {
			p.sendMessage(Messages.error + "An error occured!");
			e.printStackTrace();
		}
	}

	public static void saveInventory(Player p, Map<Integer, ItemStack> content) {
		content.forEach((slot, item) -> {
			Main.configManager.getRockets().set(p.getUniqueId().toString() + ".content." + slot, item.toString());
		});

		try {
			Main.configManager.saveRocketsFile();
		} catch (IOException e) {
			p.sendMessage(Messages.error + "An error occured!");
			e.printStackTrace();
		}
	}

	public static void addFuel(Player p) {
		if (hasPlayerRocket(p)) {
			int f = Main.configManager.getRockets().getInt(p.getUniqueId().toString() + ".fuel");
			Main.configManager.getRockets().set(p.getUniqueId().toString() + ".fuel", f + 3);
			try {
				Main.configManager.saveRocketsFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean isFuelFull(Player p) {
		if (hasPlayerRocket(p)) {
			Rocket r = getRocket(p);
			return r.getFuelLevel() >= RocketUtil.getMaxFuel(r.getTier());
		}
		return false;
	}

	public static Location getSeat(Player p) {
		if (!hasPlayerRocket(p))
			throw new NullPointerException("Player " + p.getName() + " doesn't habe a rocket");
		String name = "RocketTier" + getRocket(p).getTier();

		double xspawnoff = Main.configManager.getConfig().getDouble(name + ".XSpawnOffset");
		double yspawnoff = Main.configManager.getConfig().getDouble(name + ".YSpawnOffset");
		double zspawnoff = Main.configManager.getConfig().getDouble(name + ".ZSpawnOffset");

		Location ret = getRocket(p).getLocation().clone().add(xspawnoff, yspawnoff, zspawnoff);
		return ret;
	}

	@SuppressWarnings("unchecked")
	public static Rocket getRocket(Player p) throws NullPointerException {
		if (!hasPlayerRocket(p))
			throw new NullPointerException("This player does not have a rocket. Rocket#getRocket");
		FileConfiguration c = Main.configManager.getRockets();
		String n = p.getUniqueId().toString();
		return new Rocket(p, c.getInt(n + ".invSize"), (List<ItemStack>) c.getList(n + ".content"),
				c.getInt(n + ".tier"), c.getLocation(n + ".loc"), c.getInt(n + ".fuel"));
	}

	public static boolean hasPlayerRocket(Player p) {
		return Main.configManager.getRockets().getKeys(true).contains(p.getUniqueId().toString());
	}

	public static boolean hasPlayerRocket(UUID uuid) {
		return hasPlayerRocket(Bukkit.getPlayer(uuid));
	}

	public static void deleteRocket(Player p) {
		Rocket r = getRocket(p);
		int size = Main.configManager.getConfig().getInt("RocketTier" + r.getTier() + ".size");
		Location loc = r.getLocation();
		for (int x = -size; x <= size; x++) {
			for (int y = 2; y < 20; y++) {
				for (int z = -size; z <= size; z++) {
					new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z)
							.getBlock().setType(Material.AIR);
				}

			}
		}

		Main.configManager.getRockets().set(p.getUniqueId().toString(), null);
		try {
			Main.configManager.saveRocketsFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
