package eu.mcuniverse.chat.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ChatEvent implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		FPlayer fp = FPlayers.getInstance().getByPlayer(p);
		PermissionUser user = PermissionsEx.getUser(p);

		String prefix = user.getPrefix().replace("&", "§");
		String fName = fp.getFaction().getTag();

		e.setFormat(prefix + (fp.hasFaction() ? "§b" + fName + " §8- " : "") + "§7" + p.getName() + "§8 » §f"
				+ e.getMessage().replace('&', '§'));
	}
}
