package de.jayreturns.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.jayreturns.brewing.BrewListener;
import de.jayreturns.brewing.BrewingRecipe;
import de.jayreturns.commands.CMD_Day;
import de.jayreturns.commands.CMD_Fly;
import de.jayreturns.commands.CMD_PvP;
import de.jayreturns.commands.CMD_RocketadminCommand;
import de.jayreturns.config.ConfigManager;
import de.jayreturns.items.Items;
import de.jayreturns.rocket.RocketFuelInventory;
import de.jayreturns.rocket.RocketInventory;
import de.jayreturns.rocket.RocketLauncher;
import de.jayreturns.rocket.RocketMenu;
import de.jayreturns.rocket.RocketPlaceListener;
import de.jayreturns.util.Messages;
import de.jayreturns.weapon.WeaponTests;

public class Main extends JavaPlugin implements Listener {

	private static Main instance;
	public PluginManager pm = Bukkit.getServer().getPluginManager();
	public static ConfigManager configManager;
	
//	public static String prefix = "§6McUniverse §7» ";
//	public static String noperm = "§cYou don't have the permission to use that command."; 
	
	@Override
	public void onEnable() {
		System.out.println(Messages.prefix + "Plugin Started");
		
		instance = this;
		register();
		
//		Recipe.addRecipe();
		
	}

	
	
	private void register() {
		pm.registerEvents(this, this);
		pm.registerEvents(new RocketPlaceListener(), this);
		pm.registerEvents(new RocketMenu(), this);
		pm.registerEvents(new RocketInventory(), this);
		pm.registerEvents(new RocketFuelInventory(), this);
//		pm.registerEvents(new FurnaceListener(), this);
//		pm.registerEvents(new WeaponTests(), this);
		pm.registerEvents(new BrewListener(), this);
		pm.registerEvents(new RocketLauncher(), this);
//		pm.registerEvents(new ChatEvent(), this);
//		pm.registerEvents(new JoinListener(), this);
//		pm.registerEvents(new Motd_String(), this);
//		pm.registerEvents(new Verboten(), this);
		
		getCommand("rocketadmin").setExecutor(new CMD_RocketadminCommand());
		getCommand("myrocket").setExecutor(new RocketMenu());
		getCommand("weapon").setExecutor(new WeaponTests());
		getCommand("fly").setExecutor(new CMD_Fly());
		getCommand("pvp").setExecutor(new CMD_PvP());
		getCommand("day").setExecutor(new CMD_Day());
		
		new BrewingRecipe(Items.getOil(), (inventory,  item, ingredient) -> {
			if (item.getType() != Material.GLASS_BOTTLE)
				return;
			if (inventory.getItem(0).isSimilar(Items.getRocketFuel())) {
				item.setType(Items.getWaterBottle().getType());
				item.setData(Items.getWaterBottle().getData());
				item.setItemMeta(Items.getWaterBottle().getItemMeta());
				item.setAmount(1);
			} else {
				item.setType(Items.getRocketFuel().getType());
				item.setData(Items.getRocketFuel().getData());
				item.setItemMeta(Items.getRocketFuel().getItemMeta());
				item.setAmount(1);
			}
		}, false);
	}

	public static Main getInstance() {
		return instance;
	}

	@EventHandler
	public void onCmd(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if (e.getMessage().contains("get")) {
			e.setCancelled(true);
			String[] cmd = e.getMessage().split(" ");
			String key = cmd[1];
			if (key.equalsIgnoreCase("all")) {
				p.sendMessage(Messages.prefix + "§7Alle keys:");
				for (String s : getConfig().getKeys(true)) {
					p.sendMessage("§7" + s);
				}
				return;
			} else if (key.equalsIgnoreCase("allvalues")) {
				p.sendMessage(Messages.prefix + "Alle keys mit value:");
				for (String s : getConfig().getKeys(true)) {
					p.sendMessage("§7" + s + " -> " + getConfig().get(s));
				}
			}
			p.sendMessage("§7" + getConfig().get(key));
		} else if (e.getMessage().startsWith("/fuel")) {
			e.setCancelled(true);
			p.getInventory().addItem(Items.getOil(), Items.getRocketFuel());
		}
	}	
}