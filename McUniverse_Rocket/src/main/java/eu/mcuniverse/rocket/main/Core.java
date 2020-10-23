package eu.mcuniverse.rocket.main;

import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import eu.mcuniverse.rocket.brewing.BrewListener;
import eu.mcuniverse.rocket.brewing.BrewingRecipe;
import eu.mcuniverse.rocket.commands.rocketadmin.RocketAdminCommandManager;
import eu.mcuniverse.rocket.config.ConfigManager;
import eu.mcuniverse.rocket.data.Messages;
import eu.mcuniverse.rocket.items.Items;
import eu.mcuniverse.rocket.listener.BlockPlaceListener;
import eu.mcuniverse.rocket.listener.CancelDamageListener;
import eu.mcuniverse.rocket.listener.ConsumeListener;
import eu.mcuniverse.rocket.listener.ExplosionListener;
import eu.mcuniverse.rocket.listener.InRocketListener;
import eu.mcuniverse.rocket.listener.JoinListener;
import eu.mcuniverse.rocket.listener.RespawnListener;
import eu.mcuniverse.rocket.listener.RocketInventoryPreventListener;
import eu.mcuniverse.rocket.listener.RocketMenus;
import eu.mcuniverse.rocket.listener.RocketPlaceListener;
import eu.mcuniverse.rocket.oil.RefineryListener;
import eu.mcuniverse.rocket.oil.RefineryManager;
import eu.mcuniverse.rocket.oil.RefineryStorageManager;
import eu.mcuniverse.rocket.rocket.InRocketManager;
import eu.mcuniverse.rocket.sql.MySQL;
import eu.mcuniverse.universeapi.api.UniverseAPI;
import eu.mcuniverse.universeapi.inventory.IGUI;
import lombok.Getter;

public class Core extends JavaPlugin {

	@Getter
	private static Core instance;

	private static ConfigManager configManager;

	private static ProtocolManager protocolManager;
	
	@Override
	public void onEnable() {
		instance = this;
		protocolManager = ProtocolLibrary.getProtocolManager();
		
		UniverseAPI.initialize("Rockets");
		
		MySQL.connect();
		MySQL.setup();

//		RefineryStorageManager.connect();
		RefineryStorageManager.setup();

		RefineryManager.locations = RefineryStorageManager.getLocations();
		
		register();

		if (getServer().getPluginManager().getPlugin("ProtocolLib") == null) {
			Bukkit
					.broadcastMessage(Messages.ERROR + "ProtocolLib is not installed! Disabeling " + getDescription().getName());
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		
		Bukkit.getRecipesFor(new ItemStack(Material.END_CRYSTAL)).stream()
			.filter(r -> r instanceof Keyed)
			.map(r -> (Keyed) r)
			.map(k -> k.getKey())
			.forEach(Bukkit::removeRecipe);

		System.out.println(Messages.CONSOLE_PREFIX + "Plugin successfully enabled! Version: " + getDescription().getVersion());
		
	}
	
	@Override
	public void onDisable() {
		
		MySQL.disconnect();
//		RefineryStorageManager.disconnect();
		
		Bukkit.getOnlinePlayers().forEach(player -> {
			if (InRocketManager.isInRocket(player)) {
				InRocketManager.managePlayer(player);
			}
			if (player.getOpenInventory().getTopInventory().getHolder() instanceof IGUI) {
				player.closeInventory();
			}
		});

		super.onDisable();
	}

	private void register() {
		registerBrewing();
		registerCommands();
		registerListeners();
		startTimer();
	}
	
	private void startTimer() {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				RefineryManager.tick();
			}
		}.runTaskTimer(this, 0L, 60L * 20);
	}

	private void registerListeners() {
		new RocketMenus(this);
		new RocketPlaceListener(this);
		new JoinListener(this);
		new CancelDamageListener(this);
		new InRocketListener(this);
		new RefineryListener(this);
		new RespawnListener(this);
		new ConsumeListener(this);
		new RocketInventoryPreventListener(this);
		new BlockPlaceListener(this);
		new ExplosionListener(this);
		
//		new CustomSkyListener(this);
		
		// Disable Esel
		Bukkit.getPluginManager().registerEvents(new Listener() {
			@EventHandler
			public void onSpawn(CreatureSpawnEvent e) {
				if (e.getEntityType() == EntityType.MULE) {
					e.setCancelled(true);
				}
			}
		}, this);
	}

	private void registerCommands() {
//		getCommand("getrocketgear").setExecutor(new GetRocketGear());
//		getCommand("getrocketgear").setTabCompleter(new GetRocketGear());
		getCommand("rocketadmin").setExecutor(new RocketAdminCommandManager());
	}

	private void registerBrewing() {
		Bukkit.getPluginManager().registerEvents(new BrewListener(), this);
		
		new BrewingRecipe(Items.getOil(), (inventory, item, ingredient) -> {
			if (item.getType() != Material.GLASS_BOTTLE) {
				return;
			}
			if (inventory.getItem(0).isSimilar(Items.getRocketFuel())) {
				item.setType(Items.getWaterBottle().getType());
				item.setData(Items.getWaterBottle().getData());
				item.setItemMeta(Items.getWaterBottle().getItemMeta());
				item.setAmount(1);
			} else {
				item.setType(Items.getRocketFuel().getType());
				item.setData(Items.getRocketFuel().getData());
				item.setItemMeta(Items.getRocketFuel().getItemMeta());
				item.setAmount(1);
			}
		}, false);
	}

	public static ConfigManager getConfigManager() {
		if (configManager == null) {
			configManager = new ConfigManager();
		}
		return configManager;
	}
	
	public static ProtocolManager getProtocolManager() {
		if (protocolManager == null) {
			protocolManager = ProtocolLibrary.getProtocolManager();
		}
		return protocolManager;
	}

}