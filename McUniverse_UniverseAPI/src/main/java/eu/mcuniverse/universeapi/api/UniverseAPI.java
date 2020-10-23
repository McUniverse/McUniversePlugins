package eu.mcuniverse.universeapi.api;

import eu.mcuniverse.universeapi.chat.MessageManager;
import eu.mcuniverse.universeapi.config.ConfigManager;
import eu.mcuniverse.universeapi.factions.FactionManager;
import eu.mcuniverse.universeapi.factions.FactionUtil;
import eu.mcuniverse.universeapi.luckperms.LuckPermsUtil;
import eu.mcuniverse.universeapi.main.APIMain;
import eu.mcuniverse.universeapi.settings.SettingsManager;
import eu.mcuniverse.universeapi.sql.MySQL;
import eu.mcuniverse.universeapi.util.ProtocolUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class UniverseAPI {

	/**
	 * Utility class for ProtocolLib
	 */
	@Getter
	private ProtocolUtil protocolUtil = null;

	/**
	 * Get the LuckPermsUtil
	 */
	@Getter
	private LuckPermsUtil luckPermsUtil = null;

	/**
	 * Get the ConfigManager
	 */
	@Getter
	private ConfigManager configManager = null;/* new ConfigManager(APIMain.getInstance());*/

	/**
	 * Get the SettingsManager
	 */
	@Getter
	private SettingsManager settingsManager = null; /*SettingsManager.getInstance();*/

	/**
	 * Get the MessageManager
	 */
	@Getter
	private MessageManager messageManager = null; /*MessageManager.getInstance();*/
	
	/**
	 * Get the FactionManager
	 */
	@Getter
	private FactionManager factionManager = null;

	/**
	 * Get the FactionUtil
	 */
	@Getter
	private FactionUtil factionUtil = null;
	
	@Getter
	@Setter(value = AccessLevel.PRIVATE)
	private String pluginName;

	/**
	 * Get the prefix of the server from MySQL
	 * 
	 * @return The prefix
	 */
	@NonNull
	public static String getPrefix() {
		return getMessageManager().getPrefix();
	}

	/**
	 * Get the console prefix. Just the <code>getPrefix</code> with stripped colors
	 * 
	 * @return Stripped prefix
	 */
	@NonNull
	public static String getConsolePrefix() {
		return ChatColor.stripColor(getPrefix());
	}

	/**
	 * Get the warning prefix of the server from MySQL
	 * 
	 * @return The warning prefix
	 */
	@NonNull
	public static String getWarning() {
		return getMessageManager().getWarning();
	}

	/**
	 * Get the error prefix of the server from MySQL
	 * 
	 * @return The error prefix
	 */
	@NonNull
	public static String getError() {
		return getMessageManager().getError();
	}

	/**
	 * Get the no permission message of the server from MySQL
	 * 
	 * @return The no permission message
	 */
	@NonNull
	public static String getNoPermissions() {
		return getMessageManager().getNoPermissions();
	}

	/**
	 * Initialize the API. <br>
	 * <b>IMPORTANT</b> has do be done in the <code>onEnable</code> of your plugin
	 * 
	 * @param pluginName Name of the plugin
	 */
	@Deprecated
	public static void initialize(String pluginName) {
		try {
			setPluginName(pluginName);
			
			System.out.println("Enabled for " + pluginName);
			
			configManager = new ConfigManager(APIMain.getInstance());
			settingsManager = SettingsManager.getInstance();
			messageManager = MessageManager.getInstance();
			
			if (APIMain.getPlugins().contains("ProtocolLib")) {
				protocolUtil = ProtocolUtil.getInstance();
			}
			if (APIMain.getPlugins().contains("LuckPerms")) {
				luckPermsUtil = LuckPermsUtil.getInstance();
			}
			if (APIMain.getPlugins().contains("Factions")) {
				factionManager = FactionManager.getInstance();
				factionUtil = FactionUtil.getInstance();
			}
			MySQL.connect();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Terminate the API. <br>
	 * <b>IMPORTANT</b> has do be done in the <code>onDisable</code> of your plugin
	 */
	@Deprecated
	public static void terminate() {
		MySQL.disconnect();
	}

}
