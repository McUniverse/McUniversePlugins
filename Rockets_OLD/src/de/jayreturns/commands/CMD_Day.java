package de.jayreturns.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.jayreturns.util.Messages;


public class CMD_Day
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("mcuniverse.day")) {
            if (sender instanceof Player) {
                sender.sendMessage(Messages.prefix+ "�7It's day now!");
                ((Player)sender).getLocation().getWorld().setTime(0);
            } else {
                sender.sendMessage("No Player");
            }
        } else {
            sender.sendMessage(Messages.noPerms);
        }
        return true;
    }
}

