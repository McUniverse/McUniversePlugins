package eu.mcuniverse.anti;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import eu.mcuniverse.universeapi.api.UniverseAPI;
import net.md_5.bungee.api.ChatColor;

public class AntiKill implements Listener {

	boolean canKill = false;

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		String command = e.getMessage().split(" ")[0].substring(1).toLowerCase();

		if (command.equalsIgnoreCase("kill")) {
			if (!e.getMessage().contains("@e")) {
				return;
			}
			if (canKill) {
				return;
			}
			e.getPlayer().sendMessage(UniverseAPI.getPrefix() + ChatColor.DARK_RED + "You cannot use " + ChatColor.YELLOW
					+ "/kill @e" + ChatColor.DARK_RED + " right now!");
			e.setCancelled(true);
		} else if (command.equalsIgnoreCase("enablekill")) {
			if (!e.getPlayer().hasPermission("mcuniverse.anti.enablekill")) {
				e.getPlayer().sendMessage(UniverseAPI.getNoPermissions());
				return;
			}
			canKill = !canKill;
			e.getPlayer().sendMessage(UniverseAPI.getPrefix() + ChatColor.YELLOW + "/kill @e" + ChatColor.GRAY + " is now "
					+ (canKill ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled"));
			e.setCancelled(true);
		}

	}

}
