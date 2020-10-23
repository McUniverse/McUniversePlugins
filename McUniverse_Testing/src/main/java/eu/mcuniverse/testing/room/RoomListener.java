package eu.mcuniverse.testing.room;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import eu.mcuniverse.testing.main.Main;

public class RoomListener implements Listener {

	int count = 0;

	Set<Block> checked = new HashSet<>();
	
//	public static Set<Location> wall = Sets.newHashSet();
	public static ObjectOpenHashSet<Location> wall = new ObjectOpenHashSet<Location>();

	private BlockFace[] values = new BlockFace[] { BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST,
			BlockFace.SOUTH, BlockFace.WEST };

	final int range = 10;

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		if (e.getBlock().getType() == Material.DRIED_KELP_BLOCK) {
			new BukkitRunnable() {

				@Override
				public void run() {
					check(b, 0);
				}
			}.runTask(Main.instance);
			new BukkitRunnable() {

				@Override
				public void run() {
					p.sendMessage(count + "");
				}
			}.runTaskLater(Main.instance, 20L * 5);
		} else if (e.getBlock().getType() == Material.DIAMOND_BLOCK) {
			// for (int i = 0; i < 5; i++) {
			for (BlockFace face : BlockFace.values()) {
				b.getRelative(face).setType(Material.REDSTONE_BLOCK);
			}
//			}
		} else if (e.getBlock().getType() == Material.GOLD_BLOCK) {
//			List<BlockFace> list = Arrays.asList(BlockFace.values());
//			List<BlockFace> result = list.stream().filter(face -> (face.name().split("_").length <= 2)).filter(face -> face != BlockFace.SELF)
//					.collect(Collectors.toList());
			new BlockScanner(b).scan();
			wall.forEach(w -> {
				w.getWorld().spawnParticle(Particle.BARRIER, w, 1, 0, 0, 0, 0);
			});
		}
	}
	
	public static List<BlockFace> getFaces() {
		List<BlockFace> list = Arrays.asList(BlockFace.values());
		List<BlockFace> result = list.stream().filter(face -> (face.name().split("_").length <= 2)).filter(face -> face != BlockFace.SELF)
				.collect(Collectors.toList());
		return result;
	}

	public boolean check(Block b, int depth) {
		if (depth > range) {
			return false;
		}
		depth += 1;
		for (BlockFace face : values) {
			Block b2 = b.getRelative(face);
			if (checked.contains(b2)) {
				Bukkit.broadcastMessage("Skip");
				continue;
			}
			b2.setType(Material.DIAMOND_BLOCK);
			checked.add(b2);
			count++;
			check(b2, depth);
		}
		return false;
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getMaterial() == null) {
			return;
		}

		if (e.getMaterial() == Material.DRIED_KELP_BLOCK) {
			e.getPlayer().getLocation().subtract(0, 1, 0).getBlock().setType(Material.DRIED_KELP_BLOCK);
		}
	}

}
