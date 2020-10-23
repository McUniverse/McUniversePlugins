package eu.mcuniverse.factionextension.commands;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;

import net.md_5.bungee.api.ChatColor;

public class FHome implements Listener {

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if (!e.isCancelled() && e.getMessage().toLowerCase().startsWith("/f home")) {
			FPlayer fp = FPlayers.getInstance().getById(e.getPlayer().getUniqueId().toString());
			if (!fp.getFaction().hasHome()) {
				return;
			}
			Location home = fp.getFaction().getHome();

			if (!e.getPlayer().getWorld().equals(home.getWorld())) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(ChatColor.RED + "Sorry, you're not on the planet " + "where your home is! ("
						+ home.getWorld().getName() + ")");
			}

		}
	}

}
