package eu.mcuniverse.weapons.data;

import eu.mcuniverse.universeapi.api.UniverseAPI;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class Messages {

	public final ChatColor TEXT_COLOR = ChatColor.GRAY;

	public final String PREFIX = UniverseAPI.getPrefix();
	public final String ERROR = UniverseAPI.getError();
	public final String WARNING = UniverseAPI.getWarning();
	public final String NO_PERMS = UniverseAPI.getNoPermissions();
	
//	public final String PREFIX = ChatColor.GRAY + "[" + ChatColor.BLUE + "Weapon" + ChatColor.GRAY + "] " + TEXT_COLOR;
//	public final String ERROR = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "Error" + ChatColor.DARK_GRAY
//			+ "] " + ChatColor.RED;
//	public final String WARNING = PREFIX + ChatColor.RED;
//	public final String NO_PERMS = PREFIX + ChatColor.RED + "You don't have permissions for that!";
	public final String DEBUG = ChatColor.GRAY + "[" + ChatColor.YELLOW + "DEBUG" + ChatColor.GRAY + "] " + ChatColor.YELLOW;
	public final String CONSOLE_PREFIX = ChatColor.stripColor(PREFIX);
	
	public final String NO_PLAYER = ChatColor.RED + "You're not a player!";
	
}
