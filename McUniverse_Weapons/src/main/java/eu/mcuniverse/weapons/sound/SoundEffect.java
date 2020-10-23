package eu.mcuniverse.weapons.sound;

import org.bukkit.Sound;

import eu.mcuniverse.weapons.data.GunData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SoundEffect {

	@NonNull private Sound sound = GunData.SHOOTING_SOUND;
	@NonNull private Float pitch = 2f;
	private Float volume = 1f;
	
	public SoundEffect(Sound sound, Float pitch) {
		this.sound = sound;
		this.pitch = pitch;
	}
	
}
