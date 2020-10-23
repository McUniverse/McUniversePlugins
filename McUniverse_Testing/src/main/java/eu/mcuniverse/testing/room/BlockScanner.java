package eu.mcuniverse.testing.room;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class BlockScanner {

	private Block root;
	
	public BlockScanner(Block root) {
		this.root = root;
	}
	
	public void scan() {
		for (BlockFace face : RoomListener.getFaces()) {
			Block b = root.getRelative(face);
			if (b.getType() == Material.AIR) {
				new BlockScanner(b).scan();
			} else {
				RoomListener.wall.add(b.getLocation());
			}
		}
	}
	
}
