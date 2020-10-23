package eu.mcuniverse.factionextension.listener;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.massivecraft.factions.event.LandClaimEvent;

import net.md_5.bungee.api.ChatColor;

public class ClaimListener implements Listener {

	@EventHandler
	public void onClaim(LandClaimEvent e) {
		Player p = e.getfPlayer().getPlayer();
		List<Chunk> chunks = e.getFaction().getAllClaims().stream().filter(c -> c.getWorld().equals(p.getWorld())).map(c -> c.getChunk())
				.collect(Collectors.toList());
		if (chunks.size() == 0) {
			return;
		}
		Chunk playerChunk = p.getLocation().getChunk();
		boolean neighbour = false;
		for (Chunk c : chunks) {
			if (playerChunk.getX() == c.getX() && playerChunk.getZ() == c.getZ()) {
				continue;
			}
			if (isChunkNeighbour(c, playerChunk)) {
				neighbour = true;
				break;
			}
		}
		if (!neighbour) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.GREEN + "Your faction" + ChatColor.YELLOW + " does not own an adjacent chunk!");
			return;
		}
	}
	
	private boolean isChunkNeighbour(Chunk c1, Chunk c2) {
		int x1 = c1.getX();
		int z1 = c1.getZ();
		int x2 = c2.getX();
		int z2 = c2.getZ();
		
		boolean north = z1 - 1 == z2 && x1 == x2;
		boolean east = x1 + 1 == x2 && z1 == z2;
		boolean south = z1 + 1 == z2 && x1 == x2;
		boolean west = x1 - 1 == x2 && z1 == z2;
		
		return north || east || south || west;
	}
	
}
