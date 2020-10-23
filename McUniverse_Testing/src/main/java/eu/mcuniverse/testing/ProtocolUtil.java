package eu.mcuniverse.testing;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import com.comphenix.protocol.events.PacketContainer;

public class ProtocolUtil {

	public static void sendPacketInformation(PacketContainer packet, Player p) {
		List<Class<?>> classes = Arrays.asList(byte.class, boolean.class, short.class, double.class, int.class, long.class, float.class,
				String.class, String[].class, byte[].class, int[].class);

		p.sendMessage(packet.getType().toString());
		
		p.sendMessage("Modifiers: " + packet.getModifier().size());
		for (int i = 0; i < packet.getModifier().size(); i++) {
			Object o = packet.getModifier().read(i);
			p.sendMessage(" - " + o.getClass().getName() + " : " + o.toString());
		}
		
		classes.forEach(clazz -> {
			if (packet.getSpecificModifier(clazz).size() > 0) {
				p.sendMessage(clazz.getName() + ": " + packet.getSpecificModifier(clazz).size());
				for (int i = 0; i < packet.getSpecificModifier(clazz).size(); i++) {
					p.sendMessage(" - " + packet.getSpecificModifier(clazz).read(i));
				}
			}
		});
	}

}
