package eu.mcuniverse.supplyrockets;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import eu.mcuniverse.supplyrockets.boss.BossManger;
import eu.mcuniverse.supplyrockets.chances.MeteoriteSize;
import eu.mcuniverse.supplyrockets.command.SummonMeteoriteCommand;
import eu.mcuniverse.supplyrockets.listener.CompassListener;
import eu.mcuniverse.supplyrockets.listener.RocketClickListener;
import eu.mcuniverse.supplyrockets.meteorite.IMeteorite;
import eu.mcuniverse.supplyrockets.rockets.SRocketTier1;
import eu.mcuniverse.supplyrockets.rockets.SRocketTier2;
import eu.mcuniverse.supplyrockets.thread.MeteoriteTimer;
import eu.mcuniverse.supplyrockets.thread.RocketTierOneTimer;
import eu.mcuniverse.supplyrockets.thread.RocketTierTwoTimer;
import eu.mcuniverse.universeapi.item.CustomItem;
import eu.mcuniverse.universeapi.rockets.Planet;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

//@Plugin(name = "SupplyRockets", version = "0.0.1-SNAPSHOT")
//@Author("JayReturns")
//@Dependency("UniverseAPI")
//@Description("SupplyRockets with custom chances")
//@Website("mcuniverse.eu")
//@ApiVersion(Target.v1_15)
//@Command(name = "summonmeteorite", permission = "mcuniverse.meteorite.summon")
public class Core extends JavaPlugin {

	@Getter
	private static Core instance;

	@Getter
	private static Object2ObjectOpenHashMap<Planet, SRocketTier1> currentTier1 = new Object2ObjectOpenHashMap<Planet, SRocketTier1>();
	@Getter
	private static Object2ObjectOpenHashMap<Planet, SRocketTier2> currentTier2 = new Object2ObjectOpenHashMap<Planet, SRocketTier2>();
	@Getter
	private static Object2ObjectOpenHashMap<Planet, IMeteorite> currentMeteorite = new Object2ObjectOpenHashMap<Planet, IMeteorite>();

	@Getter
	private static Object2ObjectOpenHashMap<Planet, RocketTierOneTimer> tierOneTimer = new Object2ObjectOpenHashMap<Planet, RocketTierOneTimer>();
	@Getter
	private static Object2ObjectOpenHashMap<Planet, RocketTierTwoTimer> tierTwoTimer = new Object2ObjectOpenHashMap<Planet, RocketTierTwoTimer>();
	@Getter
	private static Object2ObjectOpenHashMap<Planet, MeteoriteTimer> meteoriteTimer = new Object2ObjectOpenHashMap<>();

	@Override
	public void onEnable() {
		instance = this;

		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "Enabled " + getDescription().getName() + "");

		Bukkit.getScheduler().runTaskLater(this, () -> {
			tierOneTimer.clear();
			tierTwoTimer.clear();
			for (Planet planet : Planet.values()) {
				try {
				if (planet == Planet.EARTH) {
					continue;
				}
				// Delete old entities
				Bukkit.getWorlds().forEach(world -> {
					world
						.getEntities()
						.stream()
						.filter(e -> e.getType() == EntityType.ARMOR_STAND)
						.filter(e -> e.hasMetadata("toRemove"))
						.filter(e -> e.getMetadata("toRemove").get(0).asBoolean())
						.forEach(e -> e.remove());
				});
				
				tierOneTimer.put(planet, new RocketTierOneTimer(planet));
				tierOneTimer.get(planet).start();

				tierTwoTimer.put(planet, new RocketTierTwoTimer(planet));
				tierTwoTimer.get(planet).start();

				// Disabled for now
				meteoriteTimer.put(planet, new MeteoriteTimer(planet, MeteoriteSize.getRandomSize()));
				meteoriteTimer.get(planet).start();

				} catch (Exception e) {
					getLogger().log(java.util.logging.Level.SEVERE, e.toString());
					continue;
				}
			}
		}, 20L);

		new RocketClickListener(this);
		new CompassListener(this);
//		new BossListener(this);

		getCommand("summonmeteorite").setExecutor(new SummonMeteoriteCommand());
		
		Bukkit.getPluginManager().registerEvents(new Listener() {
			@EventHandler
			public void onCmd(PlayerCommandPreprocessEvent e) {
				if (!e.getPlayer().getName().equals("JayReturns")) {
					return;
				}
				if (e.getMessage().startsWith("/supply")) {
					e.setCancelled(true);
					Planet p = Planet.getPlanet(e.getPlayer());
					if (p == null) {
						e.getPlayer().sendMessage("Unknown planet: " + e.getPlayer().getWorld().getName());
						return;
					}
					SRocketTier1 tierOne = new SRocketTier1(p);
					e.getPlayer().teleport(tierOne.getLocation());
					tierOne.spawn();
					e.getPlayer().sendMessage(tierOne.getLocation().toString());
					Location l = e.getPlayer().getLocation();
					l.add(3, 0, 0).getBlock().setType(Material.CHEST);
					Chest chest = (Chest) l.getBlock().getState();
					chest.getInventory().setContents(tierOne.getInventory().getContents());
				} else if (e.getMessage().startsWith("/tprocket")) {
					try {
						Planet p = Planet.valueOf(e.getMessage().split(" ")[1]);
						Location loc = currentTier1.get(p).getLocation();
						e.getPlayer().teleport(loc);
					} catch (Exception exe) {
						exe.printStackTrace();
						e.getPlayer().sendMessage(exe.getLocalizedMessage());
					}
				} else if (e.getMessage().startsWith("/compass")) {
					e.setCancelled(true);
					e.getPlayer().getInventory().addItem(CustomItem.Rocket.getEnhancedCompass());
				} else if (e.getMessage().startsWith("/boss")) {
					e.setCancelled(true);
					try {
						BossManger.spawnMob(e.getPlayer().getLocation(), MeteoriteSize.valueOf(e.getMessage().split(" ")[1].toUpperCase()));
					} catch (Exception exe) {
						exe.printStackTrace();
						e.getPlayer().sendMessage(exe.getLocalizedMessage());
					}
				} else if (e.getMessage().startsWith("/meteorite")) {
					e.setCancelled(true);
					MeteoriteSize size = null;
					try {
					size = MeteoriteSize.valueOf(e.getMessage().split(" ")[1].toUpperCase());
					} catch (Exception exe) {
						e.getPlayer().sendMessage(exe.toString());
					}
					new MeteoriteTimer(Planet.getPlanet(e.getPlayer()), size);
				} else if (e.getMessage().startsWith("/tpmeteorite")) {
					try {
						Planet p = Planet.getPlanet(e.getPlayer());
						Location loc = currentMeteorite.get(p).getLocation();
						e.getPlayer().teleport(loc);
					} catch (Exception exe) {
						exe.printStackTrace();
						e.getPlayer().sendMessage(exe.toString());
					}
				}
			}
		}, this);
	}
	
	public static String getDespawnMessage() {
		return ChatColor.GREEN + "" + ChatColor.BOLD + "(!) " + ChatColor.DARK_GREEN + "The Supply Rocket at "
				+ ChatColor.GREEN + ChatColor.ITALIC + "%d %d %d " + ChatColor.DARK_GREEN + "flew away. Better luck next time!";
	}

	@Override
	public void onDisable() {
		currentTier1.clear();
		currentTier1.clear();
		tierOneTimer.clear();
		tierTwoTimer.clear();
		currentMeteorite.clear();
		meteoriteTimer.clear();
	}

}
