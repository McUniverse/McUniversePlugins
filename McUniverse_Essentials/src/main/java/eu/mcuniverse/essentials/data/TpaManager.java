package eu.mcuniverse.essentials.data;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import eu.mcuniverse.universeapi.api.UniverseAPI;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class TpaManager {

	public final Duration KEEP_ALIVE = Duration.ofSeconds(20);
	public final Duration COOLDOWN_TIME = Duration.ofSeconds(10);
	public final Duration TELEPORT_TIME = Duration.ofSeconds(5);

	public Object2ObjectOpenHashMap<UUID, Instant> tpaCooldown = new Object2ObjectOpenHashMap<>();

	public Object2ObjectOpenHashMap<UUID, UUID> currentTpa = new Object2ObjectOpenHashMap<>();

	public ObjectArrayList<UUID> waiting = new ObjectArrayList<>();

	public void sendTpaRequest(Player from, Player to) {
		from.sendMessage(UniverseAPI.getPrefix() + "Sending a teleport request to " + ChatColor.YELLOW + to.getName());
		to.sendMessage(UniverseAPI.getPrefix() + ChatColor.YELLOW + from.getName() + ChatColor.GRAY
				+ " has sent a request to teleport to you.");
		currentTpa.put(to.getUniqueId(), from.getUniqueId());
	}

	public boolean hasTpaRequest(OfflinePlayer player) {
		return currentTpa.containsKey(player.getUniqueId());
	}

	public boolean isWaiting(OfflinePlayer player) {
		return waiting.contains(player.getUniqueId());
	}

	public void killTpaRequest(OfflinePlayer player) {
		if (!currentTpa.containsKey(player.getUniqueId())) {
			return;
		}
		OfflinePlayer p = Bukkit.getOfflinePlayer(currentTpa.get(player.getUniqueId()));
		if (p.isOnline()) {
			((Player) p).sendMessage(UniverseAPI.getWarning() + "Your teleport request timed out.");
		}
		currentTpa.remove(player.getUniqueId());

	}

}
