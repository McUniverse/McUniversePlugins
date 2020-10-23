package eu.mcuniverse.ktesting

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.Bukkit

class Testing : JavaPlugin() {

	override fun onEnable() {
		Bukkit.getLogger().info("Plugin successfully enabled");
		
		getCommand("maze").executor = DungeonCommand;
		getCommand("maze").tabCompleter = DungeonCommand;
		
		
	}

}