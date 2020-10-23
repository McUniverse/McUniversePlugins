package eu.mcuniverse.recipes.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import eu.mcuniverse.recipes.inventory.Item_C4;
import eu.mcuniverse.recipes.inventory.Item_Grenade;
import eu.mcuniverse.recipes.inventory.Reinforced_Leather;
import eu.mcuniverse.recipes.listener.JoinListener;
import eu.mcuniverse.recipes.recipes.Recipes_Armor;
import eu.mcuniverse.recipes.recipes.Recipes_Inventory;
import eu.mcuniverse.recipes.recipes.Recipes_Resource;

public class main extends JavaPlugin {
	
	public static String prefix = "§6McUniverse §8» §7";
	public static String noperm = "§cYou don't have the permission to use that command.";
	
	public void onEnable() {
		
		Bukkit.getConsoleSender().sendMessage("§dRecipes §8» §aPlugin Activated");
		Bukkit.getConsoleSender().sendMessage("§dRecipes §8» §7Version: §a" + getDescription().getVersion());
		Bukkit.getConsoleSender().sendMessage("§dRecipes §8» §7Author: §a" + getDescription().getAuthors());
		
		register();
		
	}
	
	public void onDisable() {
		
		Bukkit.getConsoleSender().sendMessage("§dRecipes §8» §cPlugin deactivated");		
		Bukkit.getConsoleSender().sendMessage("§dRecipes §8» §7Author: §a" + getDescription().getAuthors());
		
	}
	
	public void register() {
		
		getCommand("re").setExecutor(new Recipes());
		
		Bukkit.getServer().getPluginManager().registerEvents(new JoinListener(), this);
		
		Bukkit.getServer().getPluginManager().registerEvents(new Recipes_Inventory(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Recipes_Armor(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Recipes_Resource(), this);
		
		Bukkit.getServer().getPluginManager().registerEvents(new Item_Grenade(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Item_C4(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Reinforced_Leather(), this);
		
		
	}

}
