package eu.mcuniverse.weapons.util;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.World;
import org.bukkit.util.Vector;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ParticleUtil {

	/**
	 * Draw a line from point1 to point2 made from particles
	 * @param point1 The starting point
	 * @param point2 The end point
	 * @param space Space between the particles
	 * @param particle The particle type to use
	 * @param offset The offset vector: V = (offsetX, offsetY, offsetZ)
	 * @param cuont The count of the particles
	 * @param speed The speed of the particles
	 * @param data Particle data e.g. {@link DustOptions}
	 */
	public void drawLine(Location point1, Location point2, double space, Particle particle, Vector offset, int count, double speed, Object data) {
		World world = point1.getWorld();
		double distance = point1.distance(point2);
		Vector p1 = point1.toVector();
		Vector p2 = point2.toVector();
		Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
		double length = 0;
		for (; length < distance; p1.add(vector)) {
			world.spawnParticle(particle, p1.toLocation(world), count, offset.getX(), offset.getY(), offset.getZ(), speed, data, true);
			length += space;
		}
	}
	
}
