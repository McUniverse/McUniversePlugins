package eu.mcuniverse.weapons.particle;

import org.bukkit.Particle;
import org.bukkit.util.Vector;

import eu.mcuniverse.weapons.data.GunData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticleEffect {

	@NonNull @Builder.Default private Particle particle = GunData.RAY_PARTICLE;
	private @Builder.Default Object data = GunData.DUST_OPTIONS;
	@NonNull @Builder.Default private Vector offset = new Vector(0, 0, 0);
	private @Builder.Default int speed = 0;
	private @Builder.Default int count = 1;
	
	public ParticleEffect(Particle particle) {
		this.particle = particle;
	}
	
	public ParticleEffect(Particle particle, int speed, int count) {
		this.particle = particle;
		this.speed = speed;
		this.count = count;
	}
	
	public ParticleEffect(Particle particle, Vector offset) {
		this.particle = particle;
		this.offset = offset;
	}
	
	public ParticleEffect(Particle particle, int xOff, int yOff, int zOff) {
		this.particle = particle;
		this.offset = new Vector(xOff, yOff, zOff);
	}
	
}
