package eu.mcuniverse.rocket.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import eu.mcuniverse.rocket.data.Messages;
import eu.mcuniverse.rocket.main.Core;
import lombok.Getter;
import lombok.SneakyThrows;

public class ConfigManager {

	@Getter
	private FileConfiguration settings;
	
	@SneakyThrows(IOException.class)
	public ConfigManager() {
		if (!Core.getInstance().getDataFolder().exists()) {
			if (Core.getInstance().getDataFolder().mkdir()) {
				System.out.println(Messages.CONSOLE_PREFIX + Core.getInstance().getDataFolder().getPath() + " successfully created!");
			} else {
				System.err.println(Messages.ERROR + "An error occured during creation of " + Core.getInstance().getDataFolder().getPath());
			}
		}
		File f = new File(Core.getInstance().getDataFolder(), "settings.yml");
		if (!f.exists()) {
				f.createNewFile();
		}
		settings = YamlConfiguration.loadConfiguration(f);
		
		setDefaults();
	}
	
	private void setDefaults() {
		settings.options().copyDefaults(true);
		settings.addDefault("distanceToRocket", 20);
		settings.addDefault("mysql.host", "localhost");
		settings.addDefault("mysql.database", "rocket");
		settings.addDefault("mysql.username", "user");
		settings.addDefault("mysql.password", "password");
		settings.addDefault("mysql.port", 3306);
		saveSettingsFile();
	}
	
	@SneakyThrows(IOException.class)
	public void saveSettingsFile() {
		File f = new File(Core.getInstance().getDataFolder(), "settings.yml");
		if (!f.exists()) {
			throw new FileNotFoundException();
		}
		settings.save(f);
	}
	
}
