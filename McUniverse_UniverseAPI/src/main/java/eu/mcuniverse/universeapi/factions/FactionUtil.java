package eu.mcuniverse.universeapi.factions;

import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Factions;

import eu.mcuniverse.universeapi.java.exception.DependencyNotFoundException;
import eu.mcuniverse.universeapi.main.APIMain;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.md_5.bungee.api.ChatColor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FactionUtil {

	private static FactionUtil instance;
	
	public static FactionUtil getInstance() {
		if (!APIMain.getPlugins().contains("Factions")) {
			throw new DependencyNotFoundException("Factions");
		}
		if (instance == null) {
			instance = new FactionUtil();
		}
		return instance;
	}
	
	public ObjectList<String> getAllFactionTags() {
		return Factions.getInstance().getAllFactions().stream()
				.filter(f -> f.isNormal())
				.map(f -> f.getTag())
				.map(ChatColor::stripColor)
				.collect(Collectors.toCollection(ObjectArrayList::new));
	}
	
	public boolean hasFaction(UUID uuid) {
		return FPlayers.getInstance().getById(uuid.toString()).hasFaction();
	}
	
	public boolean hasFaction(Player player) {
		return hasFaction(player.getUniqueId());
	}
	
	public String getFactionTag(UUID uuid) {
		return hasFaction(uuid) ? FPlayers.getInstance().getById(uuid.toString()).getTag() : null;
	}
	
	public String getFactionTag(Player player) {
		return getFactionTag(player.getUniqueId());
	}
	
}
