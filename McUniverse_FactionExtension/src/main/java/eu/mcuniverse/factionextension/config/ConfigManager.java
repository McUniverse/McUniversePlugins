package eu.mcuniverse.factionextension.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import eu.mcuniverse.factionextension.data.Messages;
import eu.mcuniverse.factionextension.main.Core;
import lombok.Getter;
import lombok.SneakyThrows;

public class ConfigManager {

	@Getter
	private FileConfiguration settings;

	@SneakyThrows(IOException.class)
	public ConfigManager() {
		if (!Core.getInstance().getDataFolder().exists()) {
			if (Core.getInstance().getDataFolder().mkdir()) {
				System.out
						.println(Messages.CONSOLE_PREFIX + Core.getInstance().getDataFolder().getPath() + " successfully created!");
			} else {
				System.err.println(
						Messages.ERROR + "An error occured during creation of " + Core.getInstance().getDataFolder().getPath());
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
		settings.addDefault("mysql.host", "localhost");
		settings.addDefault("mysql.database", "factions");
		settings.addDefault("mysql.username", "user");
		settings.addDefault("mysql.password", "password");
		settings.addDefault("mysql.port", 3306);
		settings.addDefault("temporary.update.relation.on.command", true);
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
