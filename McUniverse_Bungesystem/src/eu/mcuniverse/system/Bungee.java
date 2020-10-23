package eu.mcuniverse.system;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import eu.mcuniverse.system.commands.CMD_AddMember;
import eu.mcuniverse.system.commands.CMD_Adminchat;
import eu.mcuniverse.system.commands.CMD_Apply;
import eu.mcuniverse.system.commands.CMD_Ban;
import eu.mcuniverse.system.commands.CMD_Baninfo;
import eu.mcuniverse.system.commands.CMD_Broadcast;
import eu.mcuniverse.system.commands.CMD_BuildServer;
import eu.mcuniverse.system.commands.CMD_DevServer;
import eu.mcuniverse.system.commands.CMD_Discord;
import eu.mcuniverse.system.commands.CMD_Kick;
import eu.mcuniverse.system.commands.CMD_List;
import eu.mcuniverse.system.commands.CMD_Lobby;
import eu.mcuniverse.system.commands.CMD_Mods;
import eu.mcuniverse.system.commands.CMD_Motd;
import eu.mcuniverse.system.commands.CMD_Status;
import eu.mcuniverse.system.commands.CMD_Team;
import eu.mcuniverse.system.commands.CMD_Teamchat;
import eu.mcuniverse.system.commands.CMD_Teamspeak;
import eu.mcuniverse.system.commands.CMD_Tempban;
import eu.mcuniverse.system.commands.CMD_Unban;
import eu.mcuniverse.system.commands.CMD_Universe;
import eu.mcuniverse.system.commands.CMD_Wartung;
import eu.mcuniverse.system.listener.JoinListener;
import eu.mcuniverse.system.listener.ProxyJoinListener;
import eu.mcuniverse.system.listener.ProxyPingListener;
import eu.mcuniverse.system.mysql.MySQL;
import eu.mcuniverse.system.ranks.Rank;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Bungee extends Plugin {

	public static boolean maintenance = false;
	public static ArrayList<String> arabs = new ArrayList<String>();
	public static Bungee plugin;
	public static int MaxPlayers = 100;
	public static Bungee instance;

	public static final String ARROW = "\u00BB";
	public static String prefix = ChatColor.GOLD + "McUniverse " + ChatColor.DARK_GRAY + ARROW + " " + ChatColor.GRAY;
	public static String teamchat = "§9TeamChat §8● §7";
	public static String adminchat = "§cAdminChat §8● §7";
	public static String perms = "§cYou don't have the permission to use that command.";

	public static Configuration config;
	public static File file;

	public static Configuration cfg;

	public void onEnable() {
		plugin = this;

		instance = this;

		register();

		try {
			if (!getDataFolder().exists()) {
				getDataFolder().mkdir();
			}
			file = new File(getDataFolder().getPath(), "config.yml");
			if (!file.exists()) {
				file.createNewFile();
			}
			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

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

			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
		} catch (Exception e) {
			System.out.println("[Wartungen] DataFolder oder status.yml konnte nicht erstellt werden!");
		}
//		MySQL.connect();
//		MySQL.createTableIfNotExists();

	}

	public static void saveConfig() {
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable() {

			MySQL.disconnect();
	}

	public static void log(String s) {
		ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(s));
	}

	public static String capitalize(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}

		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}

	public void register() {

		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_Wartung());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_Wartung());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_Wartung());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_Ban());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_Baninfo());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_Tempban());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_Kick());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_Unban());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_Status());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_Team());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_AddMember());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_Teamchat());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_Motd());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_Lobby());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_DevServer());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_Adminchat());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_Broadcast());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_List());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_BuildServer());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_Teamspeak());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_Apply());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_Universe());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_Discord());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMD_Mods());

		ProxyServer.getInstance().getPluginManager().registerListener(this, new ProxyJoinListener());
		ProxyServer.getInstance().getPluginManager().registerListener(this, new ProxyPingListener());
//		ProxyServer.getInstance().getPluginManager().registerListener(this, new OnBanJoin_Listener());
		ProxyServer.getInstance().getPluginManager().registerListener(this, new JoinListener());
		ProxyServer.getInstance().getPluginManager().registerListener(this, new CMD_Mods()); //Listener in command

	}

}
