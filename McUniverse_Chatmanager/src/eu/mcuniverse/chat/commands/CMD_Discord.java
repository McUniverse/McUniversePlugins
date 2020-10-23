package eu.mcuniverse.chat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CMD_Discord implements CommandExecutor {

    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            cs.sendMessage("§7[]§8§m------------------§7[] §6McUniverse §7[]§8§m------------------§7[]");
            cs.sendMessage("");
            cs.sendMessage("§7Discord Server: §ehttps://discord.gg/tAumCdV");
            cs.sendMessage("");
            cs.sendMessage("§7[]§8§m------------------§7[] §6McUniverse §7[]§8§m------------------§7[]");
        }
        return true;
    }
}
