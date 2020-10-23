package de.jayreturns.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.jayreturns.main.Main;

public class ConfigManager {

	private FileConfiguration config;
	private FileConfiguration rockets;
//	private FileConfiguration test;
	
	public ConfigManager() {
		config = Main.getInstance().getConfig();
		File f = new File(Main.getInstance().getDataFolder(), "rockets.yml");
		if (!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		rockets = YamlConfiguration.loadConfiguration(f);
		
//		f = new File(Main.getInstance().getDataFolder(), "test.yml");
//		if (!f.exists()) {
//			try {
//				f.createNewFile();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		test = YamlConfiguration.loadConfiguration(f);
//		Map<Integer, ItemStack> t = new HashMap<>();
//		t.put(0, ItemUtil.createItem(Material.STONE, "§1Stone", "This", "Is", "a", "lore"));
//		t.put(1, ItemUtil.createEnchantedItem(Material.DIAMOND_SWORD, "Sword"));
//		t.put(7, ItemUtil.createItem(Material.ACACIA_LEAVES, "§1§lBOAT", "§bAnother lore"));
//		test.set("my.test", t);
//		try {
//			test.save(f);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		Player p = Bukkit.getServer().getPlayer("JayReturns");
//		if (p.isOnline()) {
//			@SuppressWarnings("unchecked")
//			Map<Integer, ItemStack> abc = (Map<Integer, ItemStack>) test.get("my.test");
//			p.sendMessage(abc.values().toString());
//			p.sendMessage(abc.keySet().toString());
//		}
	}
	
	public void loadConfig() {
		config.options().copyDefaults(true);
		for (int i = 0; i < 6; i++) {
			int j = i + 1;
			String name = "RocketTier" + j;
			String filename = "rocket_tier_" + j + ".schem";
			config.addDefault(name + ".FileName", filename);
			config.addDefault(name + ".XOffset", 0);
			config.addDefault(name + ".YOffset", 0);
			config.addDefault(name + ".ZOffset", 0);
			config.addDefault(name + ".XSpawnOffset", 0);
			config.addDefault(name + ".YSpawnOffset", 0);
			config.addDefault(name + ".ZSpawnOffset", 0);
			config.addDefault(name + ".size", 2);
		}
		config.addDefault("distanceForInventory", 20.0);
		Main.getInstance().saveConfig();
	}
	
	public FileConfiguration getConfig() {
		Main.getInstance().reloadConfig();
		return config;
	}
	
	public FileConfiguration getRockets() {
		return rockets;
	}
	
	public void saveRocketsFile() throws FileNotFoundException, IOException {
		File f = new File(Main.getInstance().getDataFolder(), "rockets.yml");
		if (!f.exists())
			throw new FileNotFoundException();
		rockets.save(f);
	}
	
}