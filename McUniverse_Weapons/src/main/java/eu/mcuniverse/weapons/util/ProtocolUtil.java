package eu.mcuniverse.weapons.util;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedParticle;

import eu.mcuniverse.weapons.main.Core;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProtocolUtil {

	public void spawnParticle(Player player, Location loc, Vector offset, int count, float speed, Particle particle, Object data) {
		PacketContainer packet = Core.getProtocolManager().createPacket(PacketType.Play.Server.WORLD_PARTICLES);

		packet.getIntegers().write(0, count); // Count
		packet.getBooleans().write(0, false); // Force/Normal
		packet.getFloat().write(0, (float) offset.getX()).write(1, (float) offset.getY()).write(2, (float) offset.getZ())
				.write(3, speed); // Offset + Speed
		packet.getDoubles().write(0, loc.getX()).write(1, loc.getY()).write(2, loc.getZ());
//		packet.getNewParticles().write(0, WrappedParticle.create(Particle.REDSTONE, new DustOptions(Color.RED, 1)));
		packet.getNewParticles().write(0, WrappedParticle.create(particle, data));

		try {
			Core.getProtocolManager().sendServerPacket(player, packet);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public void spawnParticleAll(Location loc, Vector offset, int count, float speed, Particle particle, Object data) {
		Bukkit.getOnlinePlayers().forEach(all -> {
			spawnParticle(all, loc, offset, count, speed, particle, data);
		});
	}

}
