package eu.mcuniverse.factionextension.listener;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.massivecraft.factions.event.LandUnclaimEvent;

import eu.mcuniverse.factionextension.storage.HomeManager;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.md_5.bungee.api.ChatColor;

public class UnclaimListener implements Listener {

	@EventHandler
	public void onUnclaim(LandUnclaimEvent e) {
		if (e.isCancelled()) {
			return;
		}
//		Object2ObjectMap<UUID, Location> homes = MySQL.Home.getAllHomesWithUUID();
		Object2ObjectMap<UUID, Location> homes = HomeManager.getallHomesWithUUID();
		homes.forEach((uuid, location) -> {
			if (e.getLocation().getChunk().equals(location.getChunk())) {
//				MySQL.Home.deleteLocation(uuid);
				HomeManager.deleteHome(uuid);
				Player homeOwner = Bukkit.getPlayer(uuid);
				if (homeOwner != null) { 
					homeOwner.sendMessage(ChatColor.RED + "Your home was deleted because your faction unclaimed the chunk.");
				}
			}
		});
	}
	
	// TODO: LandUnclaimallEvent
//	@EventHandler
//	public void onUnclaimAll(LandUnclaimAllEvent e) {
//	}
	
}
