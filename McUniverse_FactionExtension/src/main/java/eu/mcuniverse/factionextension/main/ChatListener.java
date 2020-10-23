package eu.mcuniverse.factionextension.main;

import java.util.regex.Pattern;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.ChatType;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

public class ChatListener extends PacketAdapter {

	public ChatListener() {
		super(Core.getInstance(), ListenerPriority.HIGH, PacketType.Play.Server.CHAT);
	}

	@Override
	public void onPacketSending(PacketEvent event) {
		if (event.getPacketType() == PacketType.Play.Server.CHAT) {
			try {
				PacketContainer packet = event.getPacket();
				
				// Check if it is actionbar message because in a actionbar the getChatComponents().read(0) is null!
				if (isActionbarMessage(packet)) {
					return;
				}
				
				WrappedChatComponent message = packet.getChatComponents().read(0);
				TextComponent txt = new TextComponent(ComponentSerializer.parse(message.getJson()));
				String msg = txt.toPlainText();
				
				if (msg.contains("New version of Factions available:") || Pattern
						.compile("\\bLeaving\\b\\s([A-Za-z]+),\\s\\bEntering\\b\\s([A-Za-z0-9])+").matcher(msg).matches()
						|| msg.contains("Get it at") || msg.contains("resources/factionsuuid.1035")) {
					event.setCancelled(true);
					return;
				}
			} catch (Exception e) {
//				e.printStackTrace();
			} 
		}
	}
	
	private boolean isActionbarMessage(PacketContainer packet) {
		return packet.getChatTypes().read(0) == ChatType.GAME_INFO;
	}

}
