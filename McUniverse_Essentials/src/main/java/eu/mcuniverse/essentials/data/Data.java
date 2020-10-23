package eu.mcuniverse.essentials.data;

import java.time.Instant;
import java.util.UUID;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Data {

	public ObjectOpenHashSet<UUID> mutedPlayers = new ObjectOpenHashSet<UUID>();
	
	public ObjectOpenHashSet<UUID> vanished = new ObjectOpenHashSet<UUID>();
	
	public Object2ObjectOpenHashMap<UUID, Instant> chatSlowdown = new Object2ObjectOpenHashMap<UUID, Instant>();
	
}
