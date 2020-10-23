package eu.mcuniverse.essentials.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import eu.mcuniverse.essentials.Core;
import eu.mcuniverse.essentials.tablist.RankManager;
import eu.mcuniverse.essentials.tablist.TablistTeam;
import eu.mcuniverse.universeapi.api.UniverseAPI;
import net.md_5.bungee.api.ChatColor;

public class JoinLeaveListener implements Listener {

	public JoinLeaveListener(final Core plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		e.getPlayer().setCustomNameVisible(true);
		e.getPlayer().setCustomName(UniverseAPI.getLuckPermsUtil().getPrefix(e.getPlayer().getUniqueId()) + ChatColor.GRAY + e.getPlayer().getName());
		e.getPlayer().setPlayerListName(e.getPlayer().getCustomName().replace('&', ChatColor.COLOR_CHAR));
		
		new TablistTeam().addPlayer(e.getPlayer());
		Bukkit.getOnlinePlayers().forEach(p -> p.setScoreboard(RankManager.getInstance().getScoreboard()));
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onLeave(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		new TablistTeam().removePlayer(e.getPlayer());
	}

}
