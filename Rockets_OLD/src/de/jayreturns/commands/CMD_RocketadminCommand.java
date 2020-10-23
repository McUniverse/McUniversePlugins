package de.jayreturns.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.jayreturns.main.Main;
import de.jayreturns.rocket.Rocket;
import de.jayreturns.util.Messages;

public class CMD_RocketadminCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Messages.prefix + "Du bist kein Spieler!");
			return true;
		}
		Player p = (Player) sender;
		if (!p.hasPermission("rocket.admin")) {
			p.sendMessage(Messages.noPerms);
			return true;
		}
		if (args.length == 0) {
			p.sendMessage(Messages.prefix + "§7Benutze §6/rocketadmin §7<§6config§7>");
			p.sendMessage(Messages.prefix + "§7Benutze §6/rocketadmin §7<§6setoffset§7/§6setspawnoffset§7> §7<§6tier§7> <§6x§7> <§6y§7> <§6z§7>");
			p.sendMessage(Messages.prefix + "§7Benutze §6/rocketadmin §7<§6hasrocket§7/§6deleterocket> <§ePlayer§7>");
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("config")) {
				Main.getInstance().reloadConfig();
				p.sendMessage(Messages.prefix + "§7Config wurde erfolgreich neu geladen!");
			}
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("hasrocket")) {
				String name = args[1];
				boolean b = Rocket.hasPlayerRocket(Bukkit.getOfflinePlayer(name).getUniqueId());
				p.sendMessage(Messages.prefix + "§7Der Spieler §6" + name + "§7 hat " + (b ? "eine" : "keine") + " Rakete!");
			} else if (args[0].equalsIgnoreCase("deleterocket")) {
				if (p.hasPermission("rocket.delete")) {
					String name = args[1];
					Rocket.deleteRocket(Bukkit.getPlayer(name));
					p.sendMessage(Messages.prefix + "Die Rakete von §6" + name + "§7 wurde entfernt");
				} else {
					p.sendMessage(Messages.noPerms);
				}
			}
		} else if (args.length == 5) {
			if (args[0].equalsIgnoreCase("setoffset")) {
				int tier;
				double x, y, z;
				tier = Integer.MIN_VALUE;
				x = y = z = Integer.MIN_VALUE;
				try {
					tier = Integer.parseInt(args[1]);
				} catch (Exception e) {
					p.sendMessage(Messages.error + "Tier nicht gefunden");
				}
				try {
					x = Double.parseDouble(args[2]);
					y = Double.parseDouble(args[3]);
					z = Double.parseDouble(args[4]);
				} catch (Exception e) {
					p.sendMessage(Messages.error + "Bitte gebe valide Koordinaten an!");
				}
				if (!Arrays.asList(tier, x, y, z).contains(Integer.MIN_VALUE)) { // Check multiple variables at once
					Main.getInstance().getConfig().set("RocketTier" + tier + ".XOffset", x);
					Main.getInstance().getConfig().set("RocketTier" + tier + ".YOffset", y);
					Main.getInstance().getConfig().set("RocketTier" + tier + ".ZOffset", z);
					Main.getInstance().saveConfig();
					p.sendMessage(Messages.prefix + "§7Offset for Rocket tier " + tier + " updatet to (" + x + ", " + y + ", " + z + ")");
				}
			} else if (args[0].contentEquals("setspawnoffset")) {
				int tier;
				double x, y, z;
				tier = Integer.MIN_VALUE;
				x = y = z = Integer.MIN_VALUE;
				try {
					tier = Integer.parseInt(args[1]);
				} catch (Exception e) {
					p.sendMessage(Messages.error + "Tier nicht gefunden");
				}
				try {
					x = Double.parseDouble(args[2]);
					y = Double.parseDouble(args[3]);
					z = Double.parseDouble(args[4]);
				} catch (Exception e) {
					p.sendMessage(Messages.error + "Bitte gebe valide Koordinaten an!");
				}
				if (!Arrays.asList(tier, x, y, z).contains(Integer.MIN_VALUE)) { // Check multiple variables at once
					Main.getInstance().getConfig().set("RocketTier" + tier + ".XSpawnOffset", x);
					Main.getInstance().getConfig().set("RocketTier" + tier + ".YSpawnOffset", y);
					Main.getInstance().getConfig().set("RocketTier" + tier + ".ZSpawnOffset", z);
					Main.getInstance().saveConfig();
					p.sendMessage(Messages.prefix + "§7SpawnOffset for Rocket tier " + tier + " updatet to (" + x + ", " + y + ", " + z + ")");
				}
			}
		}
		return true;
	}

}
