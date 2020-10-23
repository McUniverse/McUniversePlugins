package eu.mcuniverse.essentials.listener;

import java.time.Instant;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;

import eu.mcuniverse.essentials.Core;
import eu.mcuniverse.essentials.data.Data;
import eu.mcuniverse.universeapi.api.UniverseAPI;
import eu.mcuniverse.universeapi.rockets.Planet;
import net.md_5.bungee.api.ChatColor;

public class ChatListener implements Listener {

	public ChatListener(final Core plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();

		if (Data.mutedPlayers.contains(p.getUniqueId())) {
			p.sendMessage(UniverseAPI.getWarning() + "I'm sorry but you're muted!");
			e.setCancelled(true);
			return;
		}

		if (Data.chatSlowdown.getOrDefault(p.getUniqueId(), Instant.now()).isAfter(Instant.now())) {
			e.setCancelled(true);
			p.sendMessage(UniverseAPI.getWarning() + "Please slow down!");
			return;
		}
		
		String format = "";
		try {
			format = UniverseAPI.getSettingsManager().getValue("chat_format");
		} catch (Exception exe) {
			Core.getInstance().getLogger().severe("ChatListener.onChat(): " + exe.getLocalizedMessage());
			e.setFormat(ChatColor.GRAY + "%s " + ChatColor.DARK_GRAY + "»" + ChatColor.WHITE + " %s");
			return;
		}
		
		format = ChatColor.translateAlternateColorCodes('&', format);
		FPlayer fp = FPlayers.getInstance().getById(p.getUniqueId().toString());
		boolean hasFaction = fp.hasFaction();
		//		boolean hasFaction = UniverseAPI.getFactionManager().hasFaction(p.getUniqueId());
//		format = format.replace("%FACTION%", hasFaction ? UniverseAPI.getFactionManager().getFactionTag(p.getUniqueId()) : "");

//		boolean hasFaction = false;
		if (hasFaction) {
//			format = format.replace("%FACTION%", "MyTag");
//		format = format.replace("%FACTION%", UniverseAPI.getFactionManager().getFactionTag(p.getUniqueId()));
			format = format.replace("%FACTION%", fp.getFaction().getTag());
		} else {
			format = format.replace("%FACTION%", "");
			format = format.replaceAll("\\| \\§[a-f0-9] \\§[a-f0-9]\\|", "|");
		}

		try {
			format = format.replace("%PLANET%", Planet.getPlanet(p).getWorldName());
		} catch (NullPointerException npe) {
			format = format.replace("%PLANET%", "");
			format = format.substring(7);
		}
		format = format.replace("%RANK%", ChatColor.translateAlternateColorCodes('&', UniverseAPI.getLuckPermsUtil().getPrefix(p.getUniqueId())));
		format = format.replace("%NAME%", p.getName());

		format = format.replace("%MESSAGE%", e.getMessage().replace("%", "%%"));

		if (p.hasPermission("mcuniverse.chatcolor")) {
			format = ChatColor.translateAlternateColorCodes('&', format);
		}

		// TODO: Werbung, Caps, %, erwähnung

////		if (e.getMessage().contains)
//		Pattern pattern = Pattern.compile("/@[A-Za-z0-9_]{3,16}/g");
//		
//		if (pattern.matcher(format).matches()) {
////			format = format.
//		}

		e.setFormat(format);
		if (!p.hasPermission("mcuniverse.chat")) {
			Data.chatSlowdown.put(p.getUniqueId(), Instant.now().plusSeconds(3));
		}
	}

}
