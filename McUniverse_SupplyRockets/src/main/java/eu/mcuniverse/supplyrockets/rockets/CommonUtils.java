package eu.mcuniverse.supplyrockets.rockets;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.EulerAngle;

import eu.mcuniverse.supplyrockets.Core;
import eu.mcuniverse.supplyrockets.chances.MeteoriteSize;
import eu.mcuniverse.supplyrockets.chances.Tier;
import eu.mcuniverse.supplyrockets.loot.LootManager;
import eu.mcuniverse.supplyrockets.utils.RandomUtils;
import eu.mcuniverse.universeapi.util.ItemUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonUtils {

	@Deprecated(forRemoval = true, since = "30.05.2020")
	public void fillInventory(Inventory inv, Tier tier) {
		ObjectSet<ItemStack> loot = LootManager.getLoot(tier);
		int[] slots = RandomUtils.getRandomSlot(inv.getSize());
		int index = 0;
		for (ItemStack item : loot) {
			inv.setItem(slots[index], item);
			index++;
		}
	}

	public ArmorStand spawn(Location location, ItemStack hand) {
		ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
		armorStand.setRightArmPose(new EulerAngle(Math.toRadians(-90), 0, 0));
		armorStand.getEquipment().setItemInMainHand(hand);
		armorStand.setInvulnerable(true);
		armorStand.setVisible(false);
		armorStand.setMetadata("toRemove", new FixedMetadataValue(Core.getInstance(), true));
		return armorStand;
	}

	public ItemStack getMeteoriteItem(MeteoriteSize meteoriteSize) {
		return ItemUtil.createSkinItem(MeteoriteSize.BASE_ITEM, meteoriteSize.getCustomModelData());
	}

	public void makeSphere(@NonNull Location center, int radius) {
		generateSphere(center, radius, false).forEach(loc -> loc.getBlock().setType(Material.AIR));
	}

	private ObjectArrayList<Location> generateSphere(Location centerBlock, int radius, boolean hollow) {
		if (centerBlock == null) {
			return new ObjectArrayList<Location>();
		}

		ObjectArrayList<Location> circleBlocks = new ObjectArrayList<Location>();

		int bx = centerBlock.getBlockX();
		int by = centerBlock.getBlockY();
		int bz = centerBlock.getBlockZ();

		for (int x = bx - radius; x <= bx + radius; x++) {
			for (int y = by - radius; y <= by + radius; y++) {
				for (int z = bz - radius; z <= bz + radius; z++) {
					double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)) + ((by - y) * (by - y)));

					if (distance < radius * radius && (!hollow && distance < ((radius - 1) * (radius - 1)))) {
						Location l = new Location(centerBlock.getWorld(), x, y, z);
						circleBlocks.add(l);
					}
				}
			}
		}
		return circleBlocks;

	}

}
