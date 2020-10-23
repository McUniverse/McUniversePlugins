package eu.mcuniverse.bungeeuniverse;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import eu.mcuniverse.bungeeuniverse.commands.Adminchat;
import eu.mcuniverse.bungeeuniverse.commands.Discord;
import eu.mcuniverse.bungeeuniverse.commands.Lobby;
import eu.mcuniverse.bungeeuniverse.commands.Maintenance;
import eu.mcuniverse.bungeeuniverse.commands.Motd;
import eu.mcuniverse.bungeeuniverse.commands.Status;
import eu.mcuniverse.bungeeuniverse.commands.Team;
import eu.mcuniverse.bungeeuniverse.commands.Teamchat;
import eu.mcuniverse.bungeeuniverse.commands.Universe;
import eu.mcuniverse.bungeeuniverse.listener.Blocker;
import eu.mcuniverse.bungeeuniverse.listener.KickListener;
import eu.mcuniverse.bungeeuniverse.listener.ProxyJoinListener;
import eu.mcuniverse.bungeeuniverse.listener.ProxyPingListener;
import eu.mcuniverse.bungeeuniverse.ranks.Rank;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Core extends Plugin {

	@Getter
	private static Plugin instance;

	@Getter
	@Setter
	private static Configuration config;
	@Getter
	@Setter
	private static File configFile;

	@Override
	public void onEnable() {
		instance = this;

		getLogger().info("BungeeUniverse enabled");

//		ProxyServer.getInstance().getPluginManager().getPlugins()

		try {
			if (!getDataFolder().exists()) {
				getDataFolder().mkdir();
			}
			configFile = new File(getDataFolder().getPath(), "config.yml");
			if (!configFile.exists()) {
				configFile.createNewFile();
			}
			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);

			// For Blocklin
//			String[] array = null;
//			config.set("my.path", Arrays.asList(array));
//			String[] arrayFromConfig = config.getStringList("my.path").toArray(new String[] {});

			if (!config.contains("status"))
				config.set("status", 0);

			for (Rank r : Rank.values()) {
				if (!config.contains(r.getKey()))
					config.set(r.getKey(), Arrays.asList("Not", "set"));
			}

			if (!config.contains("Maintenance.Bypass"))
				config.set("Maintenance.Bypass", Arrays.asList("Player1", "Player2"));

			if (!config.contains("motd"))
				config.set("motd", "&bExample motd! \n Change me in &4config.yml");

			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);
		} catch (Exception e) {

		}

		registerCommands();
	}

	// For BLocklin. Can be removeed
	private void test() throws IOException {
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		
		File file = new File(getDataFolder().getPath(), "config.yml");
		
		if (!file.exists()) {
			file.createNewFile();
		}
		
		net.md_5.bungee.config.Configuration config = net.md_5.bungee.config.ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(file);
		
		String[] array = new String[] {"Hello", "World"};
		config.set("path", Arrays.asList(array));
		
		net.md_5.bungee.config.ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).save(config, file);
		
		String[] arrayFromConfig = config.getStringList("path").toArray(new String[] {});
	}
	
	private void registerCommands() {
		PluginManager pm = ProxyServer.getInstance().getPluginManager();

		pm.registerCommand(this, new Adminchat());
		pm.registerCommand(this, new Discord());
		pm.registerCommand(this, new Teamchat());
		pm.registerCommand(this, new Lobby());
		pm.registerCommand(this, new Maintenance());
		pm.registerCommand(this, new Motd());
		pm.registerCommand(this, new Status());
		pm.registerCommand(this, new Team());
		pm.registerCommand(this, new Universe());

//		pm.registerListener(this, new JoinListener());
		pm.registerListener(this, new ProxyJoinListener());
		pm.registerListener(this, new ProxyPingListener());
		pm.registerListener(this, new KickListener());
		pm.registerListener(this, new Blocker());
	}

	public static void saveConfig() {
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
