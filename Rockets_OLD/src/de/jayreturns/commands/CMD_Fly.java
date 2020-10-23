package de.jayreturns.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.jayreturns.util.Messages;

public class CMD_Fly
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("No Player");
            return true;
        }
        Player p = (Player)sender;
        if (!p.hasPermission("mcuniverse.fly")) {
            p.sendMessage(Messages.noPerms);
            return true;
        }
        if (args.length > 1) {
            p.sendMessage(Messages.prefix + "Use: /" + label + " <Name>");
        }
        if (args.length == 0) {
            if (p.getAllowFlight()) {
                p.setFlying(false);
                p.setAllowFlight(false);
                p.sendMessage(Messages.prefix + "§cYour Flight mode has been deactivated.");
            } else {
                p.setAllowFlight(true);
                p.sendMessage(Messages.prefix + "§7Your Flight mode has been activated.");
            }
            return true;
        }
        if (!p.hasPermission("cubecloud.fly.other")) {
            p.sendMessage(Messages.noPerms);
            return true;
        }
        Player target = Bukkit.getPlayer((String)args[0]);
        if (target == null) {
            p.sendMessage(Messages.prefix + "§cThis Player is not online!");
            return true;
        }
        if (target.getAllowFlight()) {
            target.setFlying(false);
            target.setAllowFlight(false);
            target.sendMessage(Messages.prefix + "§7Your Flight mode was deactivated §c" + p.getName ());
            p.sendMessage(Messages.prefix + "§7The Flight mode of §c"+ target.getName () + "§7has been deactivated.");
        } else {
            target.setAllowFlight(true);
            target.sendMessage(Messages.prefix + "§7Your Flight mode was activated §c" + p.getName());
            p.sendMessage(Messages.prefix + "§7The Flight mode of §c" + target.getName() + "§7 has been activated.");
        }
        return true;
    }
}

