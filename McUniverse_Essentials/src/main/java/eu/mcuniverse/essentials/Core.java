package eu.mcuniverse.essentials;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.command.TabExecutor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.dependency.LoadBefore;
import org.bukkit.plugin.java.annotation.permission.ChildPermission;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import eu.mcuniverse.essentials.command.FlyCommand;
import eu.mcuniverse.essentials.command.InvseeCommand;
import eu.mcuniverse.essentials.command.MapCommand;
import eu.mcuniverse.essentials.command.MuteCommand;
import eu.mcuniverse.essentials.command.TpCommand;
import eu.mcuniverse.essentials.command.VanishCommand;
import eu.mcuniverse.essentials.command.tpa.TpaCommand;
import eu.mcuniverse.essentials.command.tpa.TpacceptCommand;
import eu.mcuniverse.essentials.listener.ChatListener;
import eu.mcuniverse.essentials.listener.JoinLeaveListener;
import eu.mcuniverse.essentials.listener.VanishListener;
import eu.mcuniverse.essentials.tablist.Rank;
import eu.mcuniverse.essentials.tablist.RankManager;
import eu.mcuniverse.essentials.tablist.TablistTeam;
import eu.mcuniverse.universeapi.api.UniverseAPI;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

@Plugin(name = "UniverseEssentials", version = "0.0.1-SNAPSHOT")
@Website("mcuniverse.eu")
@Author("JayReturns")
@LoadBefore(value = "UniverseAPI")
@Dependency(value = "UniverseAPI")
@Command(name = "mute", desc = "Mute someone", permission = "mcuniverse.mute")
@Command(name = "invsee", desc = "See into inventory of player", permission = "mcuniverse.invsee")
@Command(name = "vanish", desc = "Vanish for other players", permission = "mcuniverse.vanish")
@Command(name = "fly", desc = "Enabled/Disable fly", permission = "mcuniverse.fly")
@Command(name = "map", desc = "Show the map", aliases = { "livemap" })
@Command(name = "tp", desc = "Teleport in the world", aliases = { "teleport" })
@Command(name = "tpa", desc = "Simple tpa command")
@Command(name = "tpaccept", desc = "Tpaccept command")
@Permission(name = "mcuniverse.chatcolor", desc = "ChatColor in Chat", defaultValue = PermissionDefault.OP)
@Permission(name = "mcuniverse.*", desc = "Wildcard permission", children = {
		@ChildPermission(name = "mcuniverse.chatcolor"), @ChildPermission(name = "mcuniverse.fly.other"),
		@ChildPermission(name = "mcuniverse.tpa.bypasscooldown") })
public class Core extends JavaPlugin {

	@Getter
	private static Core instance;

	@Override
	public void onEnable() {
		instance = this;

		Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[Essentials] Enabled!");

		UniverseAPI.initialize("Essentials");

		registerEvents();
		registerCommands();
		registerTeams();
	}

	private void registerTeams() {
		TablistTeam teams = new TablistTeam();

		for (int i = 0; i < Rank.values().length; i++) {
			Rank rank = Rank.values()[i];

			if (rank == Rank.DEFAULT) {
				teams.create(rank.getName(), 99, ChatColor.GRAY + "", null, "mcuniverse.tablist.default");
				continue;
			}

			teams.create(rank.getName(), 10 + i, rank.getPrefix(), null,
					"mcuniverse.tablist." + rank.toString().toLowerCase());
		}

		teams.update();

		Bukkit.getPluginManager().registerEvents(new Listener() {
			@EventHandler
			public void onTest(PlayerCommandPreprocessEvent e) {
				if (e.getPlayer().getName().contains("Jay")) {
					if (e.getMessage().startsWith("/tab")) {
						if (e.getMessage().contains("update")) {
							teams.update();
							e.getPlayer().sendMessage("Updated");
							return;
						}
						RankManager.getInstance().getScoreboard().getTeams().forEach(team -> {
							e.getPlayer().sendMessage(team.getName() + " --- " + team.getSize() + " --- " + team.getPrefix());
							if (team.getSize() > 0) {
								team.getEntries().forEach(entry -> {
									e.getPlayer().sendMessage(" - " + entry);
								});
							}
						});
					}
				}
			}
		}, this);

	}

	private void registerEvents() {
		new ChatListener(this);
		new VanishListener(this);
		new JoinLeaveListener(this);
	}

	private void registerCommands() {
		getCommand("mute").setExecutor(new MuteCommand());
		getCommand("mute").setTabCompleter(new MuteCommand());

		getCommand("invsee").setExecutor(new InvseeCommand());
		getCommand("invsee").setTabCompleter(new InvseeCommand());

		getCommand("vanish").setExecutor(new VanishCommand());
		getCommand("vanish").setTabCompleter(new VanishCommand());

		getCommand("fly").setExecutor(new FlyCommand());
		getCommand("fly").setTabCompleter(new FlyCommand());

		getCommand("map").setExecutor(new MapCommand());
		getCommand("map").setTabCompleter(new MapCommand());

		getCommand("tp").setExecutor(new TpCommand());
		getCommand("tp").setTabCompleter(new TpCommand());

		registerCommand("tpa", TpaCommand.class);
		registerCommand("tpaccept", TpacceptCommand.class);
	}

	private void registerCommand(String commandName, Class<? extends TabExecutor> clazz) {
		try {
			TabExecutor tabExecutor = clazz.getConstructor().newInstance();
			getCommand(commandName).setExecutor(tabExecutor);
			getCommand(commandName).setTabCompleter(tabExecutor);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			getLogger().severe("Unable to register command " + commandName + " with Class " + clazz + ". Stacktrace:");
			e.printStackTrace();
		}
	}
	
}
