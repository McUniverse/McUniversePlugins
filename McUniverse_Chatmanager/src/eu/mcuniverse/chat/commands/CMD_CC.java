package eu.mcuniverse.chat.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.mcuniverse.chat.main.main;


public class CMD_CC
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player)sender;
        if (p instanceof Player && p.hasPermission("mcuniverse.cc")) {
            int i = 0;
            while (i < 100) {
                Bukkit.broadcastMessage((String)" ");
                ++i;
            }
            Bukkit.broadcastMessage(main.prefix + "�7The chat was �c" + p.getName() + " �7cleared.");
        }
        return false;
    }
}

