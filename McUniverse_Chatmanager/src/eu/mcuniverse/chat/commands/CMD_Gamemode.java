package eu.mcuniverse.chat.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.mcuniverse.chat.main.main;



public class CMD_Gamemode
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(main.noperm);
            return true;
        }
        Player p = (Player)sender;
        if (!p.hasPermission("mcuniverse.gamemode")) {
            p.sendMessage(main.noperm);
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("gm")) {
            if (args.length == 2) {
                Player target = Bukkit.getPlayer((String)args[1]);
                if (target == null) {
                    p.sendMessage(String.valueOf(main.prefix) + "§cThe Player is not online!.");
                    return true;
                }
                if (args[0].equalsIgnoreCase("0")) {
                    target.setGameMode(GameMode.SURVIVAL);
                    target.sendMessage(String.valueOf(main.prefix) + "§7You are now in GameMode §8» §eSURVIVAL");
                    p.sendMessage(String.valueOf(main.prefix) + "§3" + target.getName() + " §7is now in §eSURVIVAL §7Mode!");
                } else if (args[0].equalsIgnoreCase("1")) {
                    target.setGameMode(GameMode.CREATIVE);
                    target.sendMessage(String.valueOf(main.prefix) + "§7You are now in GameMode §8» §eKREATIVE");
                    p.sendMessage(String.valueOf(main.prefix) + "§3" + target.getName() + " §7is now in §eCREATIVE §7Mode!");
                } else if (args[0].equalsIgnoreCase("2")) {
                    target.setGameMode(GameMode.ADVENTURE);
                    target.sendMessage(String.valueOf(main.prefix) + "§7You are now in GameMode §8» §eADVENTURE");
                    p.sendMessage(String.valueOf(main.prefix) + "§3" + target.getName() + " §7is now in §eADVENTURE §7Mode!");
                } else if (args[0].startsWith("s")) {
                    target.setGameMode(GameMode.SURVIVAL);
                    target.sendMessage(String.valueOf(main.prefix) + "§7You are now in GameMode §8» §eSURVIVAL");
                    p.sendMessage(String.valueOf(main.prefix) + "§3" + target.getName() + " §7is now in §eSURVIVAL §7Mode!");
                } else if (args[0].startsWith("c")) {
                    target.setGameMode(GameMode.CREATIVE);
                    target.sendMessage(String.valueOf(main.prefix) + "§7You are now in GameMode §8» §eKREATIVE");
                    p.sendMessage(String.valueOf(main.prefix) + "§3" + target.getName() + " §7is now in §eCREATIVE §7Mode!");
                } else if (args[0].startsWith("a")) {
                    target.setGameMode(GameMode.ADVENTURE);
                    target.sendMessage(String.valueOf(main.prefix) + "§7You are now in GameMode §8» §eADVENTURE");
                    p.sendMessage(String.valueOf(main.prefix) + "§3" + target.getName() + " §7is now in §eADVENTURE §7Mode!");
                } else {
                    p.sendMessage(String.valueOf(main.prefix) + "Use: /gm <1,2,3> <Name>");
                }
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("0")) {
                    p.setGameMode(GameMode.SURVIVAL);
                    p.sendMessage(String.valueOf(main.prefix) + "§7You are now in  §eSURVIVAL §7Mode!");
                } else if (args[0].equalsIgnoreCase("1")) {
                    p.setGameMode(GameMode.CREATIVE);
                    p.sendMessage(String.valueOf(main.prefix) + "§7You are now in  §eCREATIVE §7Mode!");
                } else if (args[0].equalsIgnoreCase("2")) {
                    p.setGameMode(GameMode.ADVENTURE);
                    p.sendMessage(String.valueOf(main.prefix) + "§7You are now in  §eADVENTURE §7Mode!");
                } else if (args[0].startsWith("s")) {
                    p.setGameMode(GameMode.SURVIVAL);
                    p.sendMessage(String.valueOf(main.prefix) + "§7You are now in  §eSURVIVAL §7Mode!");
                } else if (args[0].startsWith("c")) {
                    p.setGameMode(GameMode.CREATIVE);
                    p.sendMessage(String.valueOf(main.prefix) + "§7You are now in  §eCREATIVE §7Mode!");
                } else if (args[0].startsWith("a")) {
                    p.setGameMode(GameMode.ADVENTURE);
                    p.sendMessage(String.valueOf(main.prefix) + "§7You are now in  §eADVENTURE §7Mode!");
                } else if (args[0].startsWith("3")) {
                    p.setGameMode(GameMode.SPECTATOR);
                    p.sendMessage(String.valueOf(main.prefix) + "§7You are now in  §eSPECTATOR §7Mode!");
                } else {
                    p.sendMessage(String.valueOf(main.prefix) + "§7Use: /gm <1,2,3> <Name>");
                }
            } else {
                p.sendMessage(String.valueOf(main.prefix) + "Use: /gm <1,2,3> <Name>");
            }
        }
        return true;
    }
}

