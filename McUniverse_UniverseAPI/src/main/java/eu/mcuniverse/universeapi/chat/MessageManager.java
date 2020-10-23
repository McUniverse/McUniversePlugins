package eu.mcuniverse.universeapi.chat;

import java.util.Optional;

import eu.mcuniverse.universeapi.api.UniverseAPI;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import net.md_5.bungee.api.ChatColor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageManager {

	private static MessageManager instance;

	public static MessageManager getInstance() {
		if (instance == null) {
			instance = new MessageManager();
		}
		return instance;
	}

	/**
	 * Get the prefix
	 * 
	 * @return The prefix
	 */
	@NonNull
	public String getPrefix() {
		
		return Optional.ofNullable(UniverseAPI.getSettingsManager().getValue("prefix"))
//				.map(prefix -> ChatColor.translateAlternateColorCodes('&', prefix))
				.map(this::format)
				.orElse(format("&6McUniverse &8»&7 "));
				
//		String prefix = UniverseAPI.getSettingsManager().getValue("prefix");
//		return format(prefix);
	}

	/**
	 * Get the warning prefix
	 * 
	 * @return The warning prefix
	 */
	@NonNull
	public String getWarning() {
		return Optional.ofNullable(UniverseAPI.getSettingsManager().getValue("warning"))
				.map(this::format)
				.map(formatted -> formatted.replace("%PREFIX%", getPrefix()))
				.orElse(format("&c"));
//		String warning = UniverseAPI.getSettingsManager().getValue("warning");
//		warning = warning.replace("%PREFIX%", getPrefix());
//		return format(warning);
	}

	/**
	 * Get the error prefix
	 * @return The prefix
	 */
	@NonNull
	public String getError() {
		return Optional.ofNullable(UniverseAPI.getSettingsManager().getValue("error"))
				.map(this::format)
				.map(formatted -> formatted.replace("%PREFIX%", getPrefix()))
				.orElse(format("&8[&4Error&8] &c"));
//		String error = UniverseAPI.getSettingsManager().getValue("error");
//		return format(error);
	}
	
	/**
	 * Get the no permission message
	 * @return The message
	 */
	@NonNull
	public String getNoPermissions() {
		return Optional.ofNullable(UniverseAPI.getSettingsManager().getValue("noperms"))
				.map(this::format)
				.map(formatted -> formatted.replace("%PREFIX%", getPrefix()))
				.orElse(format("&cYou don't have permission for that command!"));
//		String noperms = UniverseAPI.getSettingsManager().getValue("noperms");
//		noperms = noperms.replace("%PREFIX%", getPrefix());
//		return format(noperms);
	}
	
	/**
	 * Replace '&' to '§'
	 * 
	 * @param orig The original String
	 * @return The formatted String
	 */
	
	private String format(String orig) {
		return ChatColor.translateAlternateColorCodes('&', orig);
	}

}
