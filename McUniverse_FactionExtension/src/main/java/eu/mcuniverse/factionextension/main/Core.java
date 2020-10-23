package eu.mcuniverse.factionextension.main;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import eu.mcuniverse.factionextension.commands.FHome;
import eu.mcuniverse.factionextension.commands.Home;
import eu.mcuniverse.factionextension.commands.RocketLanding;
import eu.mcuniverse.factionextension.commands.SetHome;
import eu.mcuniverse.factionextension.config.ConfigManager;
import eu.mcuniverse.factionextension.data.Messages;
import eu.mcuniverse.factionextension.listener.BlockedCommandsListener;
import eu.mcuniverse.factionextension.listener.ChunkMoveListener;
import eu.mcuniverse.factionextension.listener.ClaimListener;
import eu.mcuniverse.factionextension.listener.FactionListener;
import eu.mcuniverse.factionextension.listener.UnclaimListener;
import eu.mcuniverse.factionextension.sql.MySQL2;
import eu.mcuniverse.factionextension.storage.FactionRocketManager;
import eu.mcuniverse.factionextension.storage.FactionStorageManager;
import eu.mcuniverse.factionextension.storage.HomeManager;
import lombok.Getter;

public class Core extends JavaPlugin {

	private static ConfigManager configManager;
	@Getter
	private static Core instance;
	@Getter
	private static ProtocolManager protocolManager;

	@Getter
	private static MySQL2 mysqlManager;
	
	@Override
	public void onEnable() {
		instance = this;
		mysqlManager = new MySQL2();
		System.out.println(Messages.CONSOLE_PREFIX + "Enabled");

		protocolManager = ProtocolLibrary.getProtocolManager();

//		UniverseAPI.initialize("FactionExtension");

//		MySQL.connect();
//		MySQL.setup();
//		MySQL.Home.setup();
//		MySQL.Rocketlanding.setup();
		
		mysqlManager.connect();
		
		FactionStorageManager.setup();
		FactionStorageManager.setConnection(getMysqlManager().getConnection());
		HomeManager.setup();
		HomeManager.setConnection(getMysqlManager().getConnection());
		FactionRocketManager.setup();
		FactionRocketManager.setConnection(getMysqlManager().getConnection());

//		Bukkit.getPluginManager().registerEvents(new Listener() {
//			@EventHandler
//			public void onPerform(PlayerCommandPreprocessEvent e) {
//				if (e.getMessage().startsWith("//test")) {
//					Plugin dynmap = Bukkit.getServer().getPluginManager().getPlugin("dynmap");
//					DynmapAPI dynmapAPI = (DynmapAPI) dynmap;
//					MarkerAPI markerAPI = dynmapAPI.getMarkerAPI();
//					MarkerSet set = markerAPI.createMarkerSet("Homes", "Homes", null, true);
//					Player p = e.getPlayer();
//					Location loc = p.getLocation();
//					set.createMarker("ID", "Marker", false, loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(),
//							markerAPI.getMarkerIcon("theater"), true);
//
//					p.setResourcePack("mcuniverse.eu/McUniverse.zip");
//				}
//			}
//		}, this);

		register();

		Bukkit.getPluginCommand("factions").setTabCompleter(new TabCompleter() {

			@Override
			public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
				if (args.length == 0) {
					return Arrays.asList("setrocketlanding", "delrocketlanding");
				} else if (args.length == 1) {
					if ("setrocketlanding".startsWith(args[0])) {
						return Arrays.asList("setrocketlanding");
					}
					if ("delrocketlanding".startsWith(args[0])) {
						return Arrays.asList("delrocketlanding");
					}
				}
				return null;
			}
		});
	}

	private void register() {
		// "Commands"
		Bukkit.getPluginManager().registerEvents(new RocketLanding(), this);
		Bukkit.getPluginManager().registerEvents(new FHome(), this);
		
		// Listener
		Bukkit.getPluginManager().registerEvents(new ClaimListener(), this);
		Bukkit.getPluginManager().registerEvents(new UnclaimListener(), this);
		Bukkit.getPluginManager().registerEvents(new ChunkMoveListener(), this);
		Bukkit.getPluginManager().registerEvents(new FactionListener(), this);
		Bukkit.getPluginManager().registerEvents(new BlockedCommandsListener(), this);

		// Commands
		getCommand("home").setExecutor(new Home());
		getCommand("sethome").setExecutor(new SetHome());

		registerProtocolListener();

	}

	private void registerProtocolListener() {
		// Disable Zone-change-message and update message
		getProtocolManager().addPacketListener(new ChatListener());
	}

	@Override
	public void onDisable() {
		if (mysqlManager != null) {
			mysqlManager.disconnect();
		}
//		MySQL.disconnect();
	}

	public static ConfigManager getConfigManager() {
		if (configManager == null) {
			configManager = new ConfigManager();
		}
		return configManager;
	}

}
