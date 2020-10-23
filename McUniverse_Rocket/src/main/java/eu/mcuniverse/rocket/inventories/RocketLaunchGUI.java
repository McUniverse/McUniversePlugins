package eu.mcuniverse.rocket.inventories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import eu.mcuniverse.rocket.data.Messages;
import eu.mcuniverse.rocket.main.Core;
import eu.mcuniverse.rocket.planet.PlanetTeleporter;
import eu.mcuniverse.rocket.rocket.InRocketManager;
import eu.mcuniverse.rocket.rocket.Rocket;
import eu.mcuniverse.rocket.rocket.RocketManager;
import eu.mcuniverse.rocket.rocket.Skin;
import eu.mcuniverse.rocket.upgrade.FancyUpgradeFormatter;
import eu.mcuniverse.rocket.upgrade.PlanetRequirements;
import eu.mcuniverse.rocket.util.ItemUtil;
import eu.mcuniverse.rocket.util.StringUtil;
import eu.mcuniverse.universeapi.api.UniverseAPI;
import eu.mcuniverse.universeapi.inventory.IGUI;
import eu.mcuniverse.universeapi.rockets.Planet;
import net.md_5.bungee.api.ChatColor;

public class RocketLaunchGUI implements IGUI {

	private Player p;
	private Planet selected;
	private Inventory inv;
	private final String NOT_ACCESSIBLE = ChatColor.RED + "Not accessible with your rocket";

	private final Material ON_MATERIAL = Material.LIME_CONCRETE;
	private final Material OFF_MATERIAL = Material.RED_CONCRETE;
	private final String LANDING_TITEL = ChatColor.GRAY + "Rocket Landing Pad: %s%s"; 
	private final int LANDING_SLOT = 22;
	
	public RocketLaunchGUI(Player player, Planet selected) {
		this.p = player;
		this.selected = selected;
	}

	@Override
	public Inventory getInventory() {
		if (inv != null) {
			return inv;
		}
		inv = Bukkit.createInventory(this, 54, ChatColor.DARK_PURPLE + "Launch your rocket");

		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, ItemUtil.getFiller());
		}

		Rocket r = RocketManager.getRocket(p);
		Planet playerPlanet = Planet.getPlanet(p);

		for (int i = 0; i < Planet.values().length; i++) {
			Planet planet = Planet.values()[i];
			ItemStack item;

			if (!PlanetRequirements.isAccessible(planet, r)) {
				item = ItemUtil.createSkull(ChatColor.YELLOW + "" + ChatColor.BOLD + StringUtil.capitalize(planet.toString()),
						Planet.getLocked(), NOT_ACCESSIBLE);
			} else {
				item = ItemUtil.createSkull(StringUtil.capitalize(planet.toString()), planet.getSkullValue());
			}

			ItemMeta meta = item.getItemMeta();
			List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<String>();
			lore.add(" ");

			try {
				int requiredFuel = planet.getDistance(playerPlanet);
				lore.add(
						(r.getFuelLevel() >= requiredFuel ? ChatColor.GREEN : ChatColor.RED) + "Required Fuel: " + requiredFuel);

				lore.add(" ");
			} catch (IllegalArgumentException e) {
			}

			lore.addAll(FancyUpgradeFormatter.getPlanetLoreColor(PlanetRequirements.getRequirements(planet), r));
			lore.add(ChatColor.GREEN + "Spacesuit");
			meta.setLore(lore);
			item.setItemMeta(meta);

			if (planet == selected) {
				ItemUtil.addLore(item, ChatColor.GREEN + "Currently selected");
			}

			inv.setItem(i, item);

		}
		
		inv.setItem(LANDING_SLOT, ItemUtil.createItem(OFF_MATERIAL, String.format(LANDING_TITEL, ChatColor.RED, "Off"),
				"", ChatColor.GRAY + "Enable this to land in your", ChatColor.GRAY + "faction's base"));
		
		Arrays.asList(38, 39, 40, 41, 42, 47, 48, 49, 50, 51).forEach(index -> {
			inv.setItem(index, ItemUtil.createItem(Material.LIME_WOOL, ChatColor.DARK_GREEN + "Confirm launch"));
		});

		return inv;
	}

	private int getIndex(Planet planet) {
		return Arrays.asList(Planet.values()).indexOf(planet);
	}

	@Override
	public void onGUIClick(Player whoClicked, int slot, ItemStack clickedItem, ItemStack cursor) {
		if (clickedItem == null || clickedItem.getType() == Material.AIR) {
			return;
		}

		if (clickedItem.getType() == Material.PLAYER_HEAD) {
			if (!clickedItem.hasItemMeta()) {
				return;
			}

			try {
				if (clickedItem.getItemMeta().getLore().contains(NOT_ACCESSIBLE)) {
					whoClicked.sendMessage(Messages.WARNING + "This planet is not accessible with your rocket tier!");
					whoClicked.playSound(whoClicked.getLocation(), Sound.BLOCK_ANVIL_LAND, SoundCategory.MASTER, 1, 0.5f);
					return;
				}
			} catch (NullPointerException e) {
			}

			String title = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
			Planet planet = Planet.valueOf(title.toUpperCase());

			selected = planet;
			// Remove lore
			for (int i = 0; i < 9; i++) {
				ItemStack item = getInventory().getItem(i);
				try {
					if (item.getItemMeta().getLore().contains(NOT_ACCESSIBLE)) {
						continue;
					}
				} catch (NullPointerException e) {
				}
				ItemUtil.clearLore(item);
				getInventory().setItem(i, item);
			}

			// Add lore
			int index = getIndex(planet);
			ItemStack item = getInventory().getItem(index);
			ItemUtil.addLore(item, ChatColor.GREEN + "Currently selected");
			getInventory().setItem(index, item);

			whoClicked.playSound(whoClicked.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 1, 2);
		} else if (clickedItem.getType() == Material.LIME_WOOL) {
			if (Planet.getPlanet(whoClicked) == selected) {
				whoClicked.sendMessage(Messages.WARNING + "You're already on this planet");
				return;
			}

			// Check fuel
			Rocket r = RocketManager.getRocket(whoClicked);
			Planet playerPlanet = Planet.getPlanet(whoClicked);
			double req = playerPlanet.getDistance(selected);
			if (r.getFuelLevel() < req) {
				whoClicked.sendMessage(Messages.WARNING + "Your rocket does not have enough fuel");
				whoClicked.playSound(whoClicked.getLocation(), Sound.BLOCK_ANVIL_LAND, SoundCategory.MASTER, 1, 0.5f);
				return;
			}

			p.sendTitle(ChatColor.DARK_GREEN + "Starting rocket", "", 0, 20, 0);

			InRocketManager.unpause(whoClicked);
//			InRocketManager.managePlayer(whoClicked);
			if (RocketManager.getRocket(whoClicked).getSkin().isFacingUp()) {
				new Countdown(whoClicked, RocketManager.getRocket(whoClicked));
			} else {
				new Animation(whoClicked);
			}
//			new Countdown(whoClicked, RocketManager.getRocket(whoClicked));
		} else if (clickedItem.getType() == ON_MATERIAL) {
			// Disable
			ItemStack item = getInventory().getItem(LANDING_SLOT);
			item.setType(OFF_MATERIAL);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(String.format(LANDING_TITEL, ChatColor.RED, "Off"));
			item.setItemMeta(meta);

			whoClicked.playSound(whoClicked.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 1, 2);
		} else if (clickedItem.getType() == OFF_MATERIAL) {
			// Enable
			
			if (!UniverseAPI.getFactionUtil().hasFaction(whoClicked)) {
				whoClicked.sendMessage(UniverseAPI.getWarning() + "You're not a member of any faction!");
				whoClicked.playSound(whoClicked.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, SoundCategory.MASTER, 1, 1);
				return;
			}
			if (selected == null || selected.equals(Planet.getPlanet(whoClicked))) {
				whoClicked.sendMessage(UniverseAPI.getWarning() + "Please select a planat first");
				whoClicked.playSound(whoClicked.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, SoundCategory.MASTER, 1, 1);
				return;
			}
			if (selected != null) {
				String tag = UniverseAPI.getFactionUtil().getFactionTag(whoClicked);
				if (!UniverseAPI.getFactionManager().hasLandingLocation(tag, selected)) {
					whoClicked.sendMessage(UniverseAPI.getWarning() + "Your faction has no landing location on your selected planet!");
					whoClicked.playSound(whoClicked.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, SoundCategory.MASTER, 1, 1);
					return;
				}
			}
			
			ItemStack item = getInventory().getItem(LANDING_SLOT);
			item.setType(ON_MATERIAL);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(String.format(LANDING_TITEL, ChatColor.GREEN, "On"));
			item.setItemMeta(meta);
			
			whoClicked.playSound(whoClicked.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 1, 2);
		}
	}

	private class Animation extends BukkitRunnable {

		int count = 0;
		int x = 0;
		Rocket r;
		Player player;

		public Animation(Player p) {
//			InRocketManager.pause(p);
			runTaskTimer(Core.getInstance(), 10L, 1L);
			this.r = RocketManager.getRocket(p);
			this.player = p;
		}

		@Override
		public void run() {
			if (count > 90) {
//				new Launcher(r, p);
				new Countdown(player, r);
				cancel();
			} else {
				x--;
				count++;
				((ArmorStand) Bukkit.getEntity(r.getArmorStandUUID())).setRightArmPose(new EulerAngle(Math.toRadians(x), 0, 0));
			}
		}

	}

	private class Countdown extends BukkitRunnable {

		Rocket r;
		Player p;
		int count = 10;
		int ticks = 0;

		public Countdown(Player pl, Rocket ro) {
			p = pl;
			r = ro;
			runTaskTimer(Core.getInstance(), 0L, 1L);
		}

		@Override
		public void run() {

			// Particles

			Location l = r.getLocation().clone().add(0, 1, 0);

			// Feuer -

			if (count > 6) {
				l.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, l, 50, 2, 0, 0, 1);
			} else if (count > 2) {
				l.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, l, 100, 2, 0, 0, 1);
			} else {
				l.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, l, 100, 2, 0, 0, 1);
				l.getWorld().spawnParticle(Particle.FLAME, l, 100, 2, 0, 0, 1);
			}

			if (count <= 0) {
				new Launcher(r, p);
				cancel();
			} else if (ticks % 20 == 0) {
				p.sendTitle(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + count, "", 5, 10, 5);
			}

			if (ticks % 20 == 0) {
				count--;
			}
			ticks++;
		}
	}

	private class Launcher extends BukkitRunnable {

		ArmorStand as;
		int count = 0;
		final Player player;
		Rocket r;
		boolean toFactionBase = false;

		public Launcher(Rocket r, Player p) {
			as = (ArmorStand) Bukkit.getEntity(r.getArmorStandUUID());
			this.player = p;
			this.r = r;
			
			toFactionBase = getInventory().getItem(LANDING_SLOT).getType() == ON_MATERIAL;
			
			runTaskTimer(Core.getInstance(), 5L, 1L);
		}

		@Override
		public void run() {
			if (InRocketManager.isInRocket(player)) {
				InRocketManager.setReference(player, as.getLocation());
				InRocketManager.updateToLook(player);
			}
			try {
				as.teleport(InRocketManager.getToLook(player));
			} catch (IllegalArgumentException e) {
				sendToPlanet(p, toFactionBase);
				cancel();
				return;
			}
			count++;

			spawnParticles(as.getLocation().clone(), r.getSkin());

			if (count > 200 || as.getLocation().getBlockY() > as.getWorld().getMaxHeight()) {
				if (InRocketManager.isInRocket(player)) {
					InRocketManager.managePlayer(player);
				}

				// remove fuel
				Planet playerPlanet = Planet.getPlanet(player);
				r.setFuelLevel(r.getFuelLevel() - playerPlanet.getDistance(selected));
				r.saveRocket();

				new BukkitRunnable() {

					@Override
					public void run() {
						sendToPlanet(p, toFactionBase);
					}
				}.runTaskLater(Core.getInstance(), 1L);

				cancel();
			}
		}

	}

	private void spawnParticles(Location location, Skin skin) {
		Location l = location.subtract(0, 1, 0);
		List<Location> locs;
		switch (skin) {
		case ROCKET:
			locs = Arrays.asList(l.clone().add(1.5, 0, -0.1), l.clone().add(-1.5, 0, 0.1), l.clone().add(0, 0, -0.1));
			locs.forEach(loc -> {
				loc.getWorld().spawnParticle(Particle.FLAME, loc, 10, 0.1, 1, 0.1, 0);
				loc.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc, 10, 0.1, 1, 0.1, 0);
			});
			break;
		case SATURN_V:
			locs = Arrays.asList(l.clone().add(0.7, 0, 0.7), l.clone().add(0.7, 0, -0.7), l.clone().add(-0.7, 0, 0.7),
					l.clone().add(-0.7, 0, -0.7));
			locs.forEach(loc -> {
				loc.getWorld().spawnParticle(Particle.FLAME, loc, 10, 0.05, 1, 0.05, 0);
				loc.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc, 10, 0.05, 1, 0.05, 0);
			});
			break;
		case SHUTTLE:
			locs = Arrays.asList(l.clone().add(1, 0, -0.4), l.clone().add(-1, 0, -0.4));
			locs.forEach(loc -> {
				loc.getWorld().spawnParticle(Particle.FLAME, loc, 10, 0.1, 1, 0.1, 0);
				loc.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc, 10, 0.1, 1, 0.1, 0);
			});
			break;
		default:
			break;
		}
	}

	private void sendToPlanet(Player player, boolean toFactionBase) {
		player.sendTitle(ChatColor.GREEN + "Launching...", ChatColor.ITALIC + "To be continued...", 20, 70, 40);
//		if (UniverseAPI.getFactionManager().hasFaction(player.getUniqueId()) && UniverseAPI.getFactionManager()
//				.hasLandingLocation(UniverseAPI.getFactionManager().getFactionTag(player.getUniqueId()))) {
//			if (PlanetTeleporter.teleportToPlanet(player, UniverseAPI.getFactionManager()
//					.getLandingLocation(UniverseAPI.getFactionManager().getFactionTag(player.getUniqueId())))) {
//				player.sendMessage(Messages.PREFIX + ChatColor.DARK_PURPLE + "Welcome to the" + ChatColor.RED
//						+ WordUtils.capitalize(selected.toString()));
//			} else {
//				player.sendMessage(Messages.ERROR + "An error occured during teleportation");
//			}
//
//		} else {
		if (PlanetTeleporter.teleportToPlanet(player, selected, toFactionBase)) {
			player.sendMessage(Messages.PREFIX + ChatColor.DARK_PURPLE + "Welcome to the " + ChatColor.RED
					+ WordUtils.capitalize(selected.toString()));
		} else {
			player.sendMessage(Messages.ERROR + "An error occured during teleportation");
		}
//		}
	}

}
