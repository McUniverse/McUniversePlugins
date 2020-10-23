package eu.mcuniverse.audit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.dependency.LoadBefore;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion.Target;
import org.bukkit.plugin.java.annotation.plugin.LogPrefix;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import eu.mcuniverse.audit.command.DeleteRecordCommand;
import eu.mcuniverse.audit.command.ShowDeathsCommand;
import eu.mcuniverse.audit.listener.DeathListener;
import eu.mcuniverse.audit.listener.JoinListener;
import eu.mcuniverse.audit.storage.AuditStorageManager;
import lombok.Getter;

@Plugin(name = "UniverseAudit", version = "0.2")
@ApiVersion(Target.v1_15)
@Author("JayReturns")
@LoadBefore("UniverseAPI")
@Dependency("UniverseAPI")
@Website("mcuniverse.eu")
@LogPrefix("Audit")
@Command(name = "showdeaths", desc = "Show deaths of a player", permission = "mcuniverse.audit.death")
@Command(name = "deletedeath", desc = "Delete a certain death record", permission = "mcuniverse.audit.delete")
public class Core extends JavaPlugin {

	@Getter
	private static Core instance;

	@Override
	public void onEnable() {
		instance = this;

		getConfig().options().copyDefaults(true);
		saveConfig();
		
		try {
			AuditStorageManager.connect();
		} catch (Exception e) {
			getLogger().severe("Error during MySQL-Connection: " + e.getLocalizedMessage());
			e.printStackTrace();
		}

		getLogger().info("Plugin enabled. Version: " + getDescription().getVersion());

		getCommand("showdeaths").setExecutor(new ShowDeathsCommand());
		getCommand("showdeaths").setTabCompleter(new ShowDeathsCommand());
		
		getCommand("deletedeath").setExecutor(new DeleteRecordCommand());
		getCommand("deletedeath").setTabCompleter(new DeleteRecordCommand());
		
		Bukkit.getPluginManager().registerEvents(new DeathListener(), this);
		Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
		
	}

}
