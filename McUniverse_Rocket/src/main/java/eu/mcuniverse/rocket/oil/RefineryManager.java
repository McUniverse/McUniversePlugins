package eu.mcuniverse.rocket.oil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlastFurnace;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dropper;
import org.bukkit.block.ShulkerBox;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Slab;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.metadata.FixedMetadataValue;

import eu.mcuniverse.rocket.main.Core;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class RefineryManager {

	public final Material BASE_BLOCK = Material.BLACK_SHULKER_BOX;
	public final int SIZE = 3;
	public final int HEIGHT = 9;
	public final int MAX_INVENTORY = 4;
	/**
	 * Time in minutes
	 */
	public final int TIME = 60 * 3;

	public final String NAME = ChatColor.RED + "" + ChatColor.BOLD + "%dmin" + ChatColor.BLUE  + "     " + ChatColor.RED + "" + ChatColor.BOLD + " %d / %d";
	
	public ObjectOpenHashSet<Location> locations;

	public final Material[] MATERIALS_USED = { BASE_BLOCK, Material.BLAST_FURNACE, Material.SMOOTH_STONE,
			Material.SMOOTH_STONE_SLAB, Material.ANDESITE_WALL, Material.DIORITE_WALL, Material.DARK_OAK_FENCE,
			Material.IRON_BLOCK, Material.STONE_BUTTON, Material.BIRCH_FENCE, Material.DROPPER, Material.BLACK_WOOL };

	public void spawnRefinery(OfflinePlayer player, Location loc) {
		loc.add(0, 1, 0);
		
		ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc.clone().add(0.5, 1, 0.5), EntityType.ARMOR_STAND);
		as.setCustomNameVisible(true);
		as.setCustomName(String.format(NAME, TIME, 0, MAX_INVENTORY));
		as.setVisible(false);
		as.setInvulnerable(true);
		as.setMetadata("refinery_display", new FixedMetadataValue(Core.getInstance(), true));
		as.setMarker(true);
		as.setGravity(false);
		RefineryStorageManager.insert(player, loc, as);
		locations.add(loc.clone());

		// Layer1
		loc.clone().add(0, 0, 0).getBlock().setType(BASE_BLOCK);
		ShulkerBox box = (ShulkerBox) loc.getBlock().getState();
		box.setLock(UUID.randomUUID().toString());
		box.update();

		loc.clone().add(1, 0, 0).getBlock().setType(Material.SMOOTH_STONE);
		loc.clone().add(-1, 0, 0).getBlock().setType(Material.SMOOTH_STONE);
		loc.clone().add(0, 0, 1).getBlock().setType(Material.SMOOTH_STONE);
		loc.clone().add(0, 0, -1).getBlock().setType(Material.SMOOTH_STONE);

		loc.clone().add(1, 0, 1).getBlock().setType(Material.BLAST_FURNACE);
		BlastFurnace furnace = (BlastFurnace) loc.clone().add(1, 0, 1).getBlock().getState();
		furnace.setLock(UUID.randomUUID().toString());
		furnace.update();
		loc.clone().add(-1, 0, 1).getBlock().setType(Material.BLAST_FURNACE);
		furnace = (BlastFurnace) loc.clone().add(-1, 0, 1).getBlock().getState();
		furnace.setLock(UUID.randomUUID().toString());
		furnace.update();
		loc.clone().add(1, 0, -1).getBlock().setType(Material.BLAST_FURNACE);
		furnace = (BlastFurnace) loc.clone().add(1, 0, -1).getBlock().getState();
		furnace.setLock(UUID.randomUUID().toString());
		furnace.update();
		loc.clone().add(-1, 0, -1).getBlock().setType(Material.BLAST_FURNACE);
		furnace = (BlastFurnace) loc.clone().add(-1, 0, -1).getBlock().getState();
		furnace.setLock(UUID.randomUUID().toString());
		furnace.update();

		// Layer2
		loc.add(0, 1, 0).getBlock().setType(Material.DARK_OAK_FENCE);
		loc.clone().add(1, 0, 1).getBlock().setType(Material.ANDESITE_WALL);
		loc.clone().add(-1, 0, -1).getBlock().setType(Material.ANDESITE_WALL);

		// Layer3
		loc.add(0, 1, 0).getBlock().setType(Material.DIORITE_WALL);
		loc.clone().add(1, 0, 1).getBlock().setType(Material.ANDESITE_WALL);
		loc.clone().add(-1, 0, -1).getBlock().setType(Material.ANDESITE_WALL);

		// Layer4
		loc.add(0, 1, 0).getBlock().setType(Material.IRON_BLOCK);
		loc.clone().add(1, 0, 1).getBlock().setType(Material.SMOOTH_STONE);
		loc.clone().add(-1, 0, -1).getBlock().setType(Material.SMOOTH_STONE);

		loc.clone().add(1, 0, 0).getBlock().setType(Material.SMOOTH_STONE_SLAB);
		loc.clone().add(-1, 0, 0).getBlock().setType(Material.SMOOTH_STONE_SLAB);
		loc.clone().add(0, 0, 1).getBlock().setType(Material.SMOOTH_STONE_SLAB);
		loc.clone().add(0, 0, -1).getBlock().setType(Material.SMOOTH_STONE_SLAB);

		Slab slab = (Slab) loc.clone().add(1, 0, 0).getBlock().getBlockData();
		slab.setType(Slab.Type.TOP);
		loc.clone().add(1, 0, 0).getBlock().setBlockData(slab);
		slab = (Slab) loc.clone().add(-1, 0, 0).getBlock().getBlockData();
		slab.setType(Slab.Type.TOP);
		loc.clone().add(-1, 0, 0).getBlock().setBlockData(slab);
		slab = (Slab) loc.clone().add(0, 0, -1).getBlock().getBlockData();
		slab.setType(Slab.Type.TOP);
		loc.clone().add(0, 0, -1).getBlock().setBlockData(slab);
		slab = (Slab) loc.clone().add(0, 0, 1).getBlock().getBlockData();
		slab.setType(Slab.Type.TOP);
		loc.clone().add(0, 0, 1).getBlock().setBlockData(slab);

		// Layer5
		loc.add(0, 1, 0).getBlock().setType(Material.IRON_BLOCK);
		loc.clone().add(1, 0, 0).getBlock().setType(Material.SMOOTH_STONE);
		loc.clone().add(-1, 0, 0).getBlock().setType(Material.SMOOTH_STONE);
		loc.clone().add(0, 0, 1).getBlock().setType(Material.SMOOTH_STONE);
		loc.clone().add(0, 0, -1).getBlock().setType(Material.SMOOTH_STONE);
		loc.clone().add(2, 0, 1).getBlock().setType(Material.STONE_BUTTON);
		loc.clone().add(-2, 0, -1).getBlock().setType(Material.STONE_BUTTON);
		loc.clone().add(-1, 0, -2).getBlock().setType(Material.STONE_BUTTON);
		loc.clone().add(1, 0, 2).getBlock().setType(Material.STONE_BUTTON);
		Directional button = (Directional) loc.clone().add(2, 0, 1).getBlock().getBlockData();
		button.setFacing(BlockFace.EAST);
		loc.clone().add(2, 0, 1).getBlock().setBlockData(button);
		button = (Directional) loc.clone().add(-2, 0, -1).getBlock().getBlockData();
		button.setFacing(BlockFace.WEST);
		loc.clone().add(-2, 0, -1).getBlock().setBlockData(button);
		button = (Directional) loc.clone().add(-1, 0, -2).getBlock().getBlockData();
		button.setFacing(BlockFace.NORTH);
		loc.clone().add(-1, 0, -2).getBlock().setBlockData(button);
		button = (Directional) loc.clone().add(1, 0, 2).getBlock().getBlockData();
		button.setFacing(BlockFace.SOUTH);
		loc.clone().add(1, 0, 2).getBlock().setBlockData(button);
		loc.clone().add(1, 0, 0).getBlock().setType(Material.BIRCH_FENCE);
		loc.clone().add(-1, 0, 0).getBlock().setType(Material.BIRCH_FENCE);
		loc.clone().add(0, 0, 1).getBlock().setType(Material.BIRCH_FENCE);
		loc.clone().add(0, 0, -1).getBlock().setType(Material.BIRCH_FENCE);
		loc.clone().add(1, 0, 1).getBlock().setType(Material.SMOOTH_STONE);
		loc.clone().add(-1, 0, -1).getBlock().setType(Material.SMOOTH_STONE);

		// Layer6
		loc.add(0, 1, 0).getBlock().setType(Material.IRON_BLOCK);
		loc.clone().add(1, 0, 0).getBlock().setType(Material.SMOOTH_STONE);
		loc.clone().add(-1, 0, 0).getBlock().setType(Material.SMOOTH_STONE);
		loc.clone().add(0, 0, 1).getBlock().setType(Material.SMOOTH_STONE);
		loc.clone().add(0, 0, -1).getBlock().setType(Material.SMOOTH_STONE);
		loc.clone().add(1, 0, 0).getBlock().setType(Material.BIRCH_FENCE);
		loc.clone().add(-1, 0, 0).getBlock().setType(Material.BIRCH_FENCE);
		loc.clone().add(0, 0, 1).getBlock().setType(Material.BIRCH_FENCE);
		loc.clone().add(0, 0, -1).getBlock().setType(Material.BIRCH_FENCE);
		loc.clone().add(1, 0, 1).getBlock().setType(Material.SMOOTH_STONE);
		loc.clone().add(-1, 0, -1).getBlock().setType(Material.SMOOTH_STONE);

		// Layer7
		loc.add(0, 1, 0).getBlock().setType(Material.IRON_BLOCK);
		loc.clone().add(1, 0, 1).getBlock().setType(Material.DROPPER);
		loc.clone().add(-1, 0, -1).getBlock().setType(Material.DROPPER);
		Dropper dropperState = (Dropper) loc.clone().add(1, 0, 1).getBlock().getState();
		dropperState.setLock(UUID.randomUUID().toString());
		dropperState.update();
		Directional dropper = (Directional) loc.clone().add(1, 0, 1).getBlock().getBlockData();
		dropper.setFacing(BlockFace.DOWN);
		loc.clone().add(1, 0, 1).getBlock().setBlockData(dropper);
		dropper = (Directional) loc.clone().add(-1, 0, -1).getBlock().getBlockData();
		dropper.setFacing(BlockFace.DOWN);
		dropperState = (Dropper) loc.clone().add(-1, 0, -1).getBlock().getState();
		dropperState.setLock(UUID.randomUUID().toString());
		dropperState.update();
		loc.clone().add(-1, 0, -1).getBlock().setBlockData(dropper);
		loc.clone().add(1, 0, 0).getBlock().setType(Material.SMOOTH_STONE_SLAB);
		loc.clone().add(-1, 0, 0).getBlock().setType(Material.SMOOTH_STONE_SLAB);
		loc.clone().add(0, 0, 1).getBlock().setType(Material.SMOOTH_STONE_SLAB);
		loc.clone().add(0, 0, -1).getBlock().setType(Material.SMOOTH_STONE_SLAB);

		// Layer8
		loc.add(0, 1, 0).getBlock().setType(Material.BLACK_WOOL);
		// Layer9
		loc.add(0, 1, 0).getBlock().setType(Material.IRON_BLOCK);
	}

	public void spawnRefinery(OfflinePlayer palyer, Block block) {
		spawnRefinery(palyer, block.getLocation());
	}

	/**
	 * Called to remove one minute from all armorstands
	 */
	@SneakyThrows(SQLException.class)
	public void tick() {
//		Bukkit.broadcastMessage("Tick. Time: " + new Time(System.currentTimeMillis()));
		Statement statement = RefineryStorageManager.getConnection().createStatement();
		ResultSet rs = statement.executeQuery("SELECT UUID,oil_amount FROM refinery");
		while (rs.next()) {
			OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(rs.getString("UUID")));
//			Bukkit.broadcastMessage("Updated time of " + player.getName());
			RefineryStorageManager.removeTime(player, 1);
			int time = RefineryStorageManager.getTime(player);
			if (time == 0) {
				if (RefineryStorageManager.getOilAmount(player) < MAX_INVENTORY) {
					RefineryStorageManager.updateOilAmount(player, rs.getInt("oil_amount") + 1);
				}
				RefineryStorageManager.resetTime(player);
			}
			updateHologram(player, getArmorstand(player));
		}
		statement.close();
		rs.close();
	}

	public void updateHologram(OfflinePlayer player, Entity entity) {
		if (entity == null) {
			Core.getInstance().getLogger().log(Level.FINEST, "RefineryManager.updateHologram() entity null");
			return;
		}
		if (!(entity instanceof ArmorStand)) {
			throw new IllegalArgumentException(
					"The entity is not an instance of an ArmorStand! Entity: " + entity.toString());
		}
		int time = RefineryStorageManager.getTime(player);
		int oil = RefineryStorageManager.getOilAmount(player);
		entity.setCustomName(String.format(NAME, time, oil, MAX_INVENTORY));
	}

	public void destroyRefinery(OfflinePlayer player) {
		// Remove blocks
		getLocations(player).forEach(loc -> {
			loc.getBlock().setType(Material.AIR);
//			loc.getBlock().breakNaturally();
//			loc.getBlock().getDrops().clear();
		});
		
		locations.remove(RefineryStorageManager.getLocation(player.getUniqueId()));
		Bukkit.getEntity(RefineryStorageManager.getArmorstandUUID(player)).remove();
		RefineryStorageManager.delete(player.getUniqueId());
	}

	public Entity getArmorstand(OfflinePlayer player) {
		return Bukkit.getEntity(RefineryStorageManager.getArmorstandUUID(player));
	}

	public boolean isRefinery(UUID uuid, Location loc) {
		Location location = RefineryStorageManager.getLocation(uuid);
		if (location == null) {
			return false;
		}
		return location.equals(loc);
	}

	public ObjectOpenHashSet<Location> getLocations() {
		ObjectOpenHashSet<Location> result = new ObjectOpenHashSet<Location>();
		ObjectOpenHashSet<UUID> uuids = RefineryStorageManager.getUUIDs();

		for (UUID uuid : uuids) {
			Location base = RefineryStorageManager.getLocation(Bukkit.getOfflinePlayer(uuid).getUniqueId());
			int length = (int) SIZE / 2;
			for (int x = -length; x <= length; x++) {
				for (int y = 0; y < HEIGHT; y++) {
					for (int z = -length; z <= length; z++) {
						result.add(base.clone().add(x, y, z));
					}
				}
			}
		}
		return result;
	}
	
	public ObjectOpenHashSet<Location> getLocations(OfflinePlayer player) {
		ObjectOpenHashSet<Location> result = new ObjectOpenHashSet<Location>();
			Location base = RefineryStorageManager.getLocation(player.getUniqueId());
			int length = (int) SIZE / 2;
			for (int x = -length; x <= length; x++) {
				for (int y = 0; y < HEIGHT; y++) {
					for (int z = -length; z <= length; z++) {
						result.add(base.clone().add(x, y, z));
					}
				}
			}
		return result;
	}

	public boolean canRefineryBePlaced(Location loc) {
		int length = (int) SIZE / 2;
		for (int x = -length; x <= length; x++) {
			for (int y = 1; y <= HEIGHT; y++) {
				for (int z = -length; z <= length; z++) {
					if (loc.getWorld().getBlockAt(loc.clone().add(x, y, z)).getType() != Material.AIR)
						return false;
				}
			}
		}
		return true;
	}

}
