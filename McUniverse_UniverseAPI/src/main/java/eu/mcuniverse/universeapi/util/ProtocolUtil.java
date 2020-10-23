package eu.mcuniverse.universeapi.util;

import org.bukkit.entity.Player;

import com.comphenix.protocol.events.PacketContainer;

import eu.mcuniverse.universeapi.java.exception.DependencyNotFoundException;
import eu.mcuniverse.universeapi.main.APIMain;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.md_5.bungee.api.ChatColor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProtocolUtil {
	
	private static ProtocolUtil instance;
	
	/**
	 * Get the instance of the ProtocolUtil class. Throws a {@linkplain RuntimeException} if
	 * <code>ProtocolLib</code> is not installed!
	 * @return {@linkplain ProtocolUtil}
	 */
	public static ProtocolUtil getInstance() {
		if(!APIMain.getPlugins().contains("ProtocolLib")) {
//			throw new RuntimeException("ProtocolLib not found but accessing features from it!");
			throw new DependencyNotFoundException("ProtocolLib");
		}
		if (instance == null) {
			instance = new ProtocolUtil();
		}
		return instance;
	}
	
	/**
	 * Send the information about a given {@link PacketContainer} to the player
	 * @param packet The packet to get the information from
	 * @param player The player to send the information to
	 */
	public void sendPacketInformation(PacketContainer packet, Player player) {
		player.sendMessage(ChatColor.GOLD + "PacketType: " + ChatColor.YELLOW + packet.getType().toString());
		
		player.sendMessage(ChatColor.AQUA + "Modifiers: " + ChatColor.DARK_AQUA + packet.getModifier().size());
		for (int i = 0; i < packet.getModifier().size(); i++) {
			Object o = packet.getModifier().read(i);
			player.sendMessage(ChatColor.BLUE + " - " + o.getClass().getName() + " : " + o.toString());
		}
	}
	
}
