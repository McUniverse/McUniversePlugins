package de.jayreturns.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.jayreturns.util.Messages;

public class CMD_PvP
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("mcuniverse.pvp")) {
            if (args.length == 0) {
                sender.sendMessage(Messages.prefix+ " �7Use: /pvp <on | off>");
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("on")) {
                    for (World w : Bukkit.getWorlds()) {
                        w.setPVP(true);
                    }
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage(Messages.prefix + "�aThe PvP area has been activated!");
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage("");
                }
                if (args[0].equalsIgnoreCase("off")) {
                    for (World w : Bukkit.getWorlds()) {
                        w.setPVP(false);
                    }
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage(Messages.prefix + " �cThe PvP area has been deactivated!");
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage("");
                }
            }
        } else {
            sender.sendMessage(Messages.noPerms);
        }
        return false;
    }
}

