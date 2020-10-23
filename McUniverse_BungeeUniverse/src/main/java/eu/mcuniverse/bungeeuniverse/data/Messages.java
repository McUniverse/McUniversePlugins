package eu.mcuniverse.bungeeuniverse.data;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class Messages {

	public final String ARROW = "\u00BB";

	public final String PREFIX = ChatColor.GOLD + "McUniverse" + ChatColor.DARK_GRAY + ARROW + " " + ChatColor.GRAY;
	public final String WARNING = PREFIX + ChatColor.RED;
	public final String NO_PERMS = WARNING + "You don't have permissions for thad command!";

	public final String MAINTENANCE_KICK = ChatColor.RED + "You were kicked from the McUniverse Network!\n\n"
			+ ChatColor.RED + "Reason: Maintenance\n\n"
			+ ChatColor.RED + "Discord: " + ChatColor.GOLD + Data.DISCORD_URL;

}
