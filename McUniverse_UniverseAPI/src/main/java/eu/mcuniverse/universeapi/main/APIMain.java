package eu.mcuniverse.universeapi.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.dependency.SoftDependency;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion.Target;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.LogPrefix;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import eu.mcuniverse.universeapi.api.UniverseAPI;
import eu.mcuniverse.universeapi.sql.MySQL;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

@Plugin(name = "UniverseAPI", version = "0.0.4-SNAPSHOT")
@Description("API for McUniverse.eu")
@ApiVersion(Target.v1_15)
@Author("JayReturns")
@SoftDependency("ProtocolLib")
@SoftDependency("Factions")
@SoftDependency("LuckPerms")
@Website("mcuniverse.eu")
@LogPrefix("UniverseAPI")
public class APIMain extends JavaPlugin {

	/**
	 * All found softDepends
	 */
	@Getter
	private static ObjectSet<String> plugins = new ObjectOpenHashSet<String>();

	@Getter
	private static APIMain instance;

	@Override
	public void onEnable() {
		instance = this;

		UniverseAPI.initialize("UniverseAPI");

		saveDefaultConfig();
		checkSoftDepends();
		System.out
				.println(String.format(ChatColor.DARK_AQUA + "[%s]" + ChatColor.GREEN + " Enabled with version %s. Made by %s",
						getDescription().getName(), getDescription().getVersion(), getDescription().getAuthors()));

	}

	@Override
	public void onDisable() {
		super.onDisable();

		UniverseAPI.terminate();

		if (MySQL.isConnected()) {
			MySQL.disconnect();
		}

		System.out
				.println(String.format(ChatColor.DARK_AQUA + "[%s]" + ChatColor.GREEN + " Disabled with version %s. Made by %s",
						getDescription().getName(), getDescription().getVersion(), getDescription().getAuthors()));
	}

	private void checkSoftDepends() {
		getDescription().getSoftDepend().forEach(softdepend -> {
			if (Bukkit.getPluginManager().getPlugin(softdepend) != null) {
				plugins.add(softdepend);
				Bukkit.getConsoleSender().sendMessage(String.format(ChatColor.DARK_AQUA + "[%s]" + ChatColor.GOLD + " %s"
						+ ChatColor.GREEN + " found as softdepend! Enabeling it", getDescription().getPrefix(), softdepend));
			} else {
				Bukkit.getConsoleSender().sendMessage(String.format(ChatColor.DARK_AQUA + "[%s]" + ChatColor.GOLD + " %s "
						+ ChatColor.RED + "not found as softdepend! Ignoring it", getDescription().getPrefix(), softdepend));
			}
		});
	}

}
