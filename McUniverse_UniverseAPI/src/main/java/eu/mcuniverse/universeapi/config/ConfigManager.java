package eu.mcuniverse.universeapi.config;

import org.bukkit.configuration.file.FileConfiguration;

import eu.mcuniverse.universeapi.main.APIMain;
import lombok.Getter;

public class ConfigManager {

	@Getter
	private FileConfiguration config;
	
	public ConfigManager(APIMain plugin) {
		try {
			plugin.getConfig().options().copyDefaults(true);
			plugin.saveDefaultConfig();
			config = plugin.getConfig();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
}
