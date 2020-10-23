package eu.mcuniverse.rocket.upgrade;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import eu.mcuniverse.rocket.rocket.RocketPart.AirTank;
import eu.mcuniverse.rocket.rocket.RocketPart.Boardcomputer;
import eu.mcuniverse.rocket.rocket.RocketPart.Frostshield;
import eu.mcuniverse.rocket.rocket.RocketPart.FuelTank;
import eu.mcuniverse.rocket.rocket.RocketPart.Heatshield;
import eu.mcuniverse.rocket.rocket.RocketPart.Hullarmor;
import eu.mcuniverse.rocket.rocket.RocketPart.Part;
import eu.mcuniverse.rocket.rocket.RocketPart.Storage;
import eu.mcuniverse.rocket.rocket.RocketPart.WaterTank;
import eu.mcuniverse.universeapi.item.CustomItem;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UpgradeRequirements {

	public Object2IntMap<ItemStack> getRequirements(Part part) {
		if (part instanceof AirTank) {
			switch (part.getTier()) {
			case 0:
				return new MapBuilder().put(CustomItem.airfilter, 1).put(CustomItem.enhancedGlass, 5).build();
			default:
				break;
			}
		} else if (part instanceof FuelTank) {
			switch (part.getTier()) {
			case 0:
				return new MapBuilder().put(new ItemStack(Material.IRON_BLOCK), 2).put(CustomItem.enhancedGlass, 2)
						.put(CustomItem.Rocket.getRocketFuel(), 1).build();
			case 1:
				return new MapBuilder().put(new ItemStack(Material.IRON_BLOCK), 4).put(CustomItem.enhancedGlass, 4)
						.put(CustomItem.Rocket.getRocketFuel(), 2).build();
			case 2:
				return new MapBuilder().put(new ItemStack(Material.IRON_BLOCK), 8).put(CustomItem.enhancedGlass, 6)
						.put(CustomItem.Rocket.getRocketFuel(), 4).build();
			case 3:
				return new MapBuilder().put(CustomItem.enhancedIron, 1).put(CustomItem.enhancedGlass, 8)
						.put(CustomItem.Rocket.getRocketFuel(), 6).build();
			case 4:
				return new MapBuilder().put(CustomItem.enhancedIron, 2).put(CustomItem.enhancedGlass, 12)
						.put(CustomItem.Rocket.getRocketFuel(), 8).build();
			case 5:
				return new MapBuilder().put(CustomItem.enhancedIron, 3).put(CustomItem.enhancedGlass, 16)
						.put(CustomItem.Rocket.getRocketFuel(), 12).build();
			default:
				break;
			}
		} else if (part instanceof Boardcomputer) {
			switch (part.getTier()) {
			case 0:
				return new MapBuilder().put(CustomItem.CircutBoard, 1).put(CustomItem.goldWire, 2)
						.put(new ItemStack(Material.REDSTONE), 32).put(new ItemStack(Material.ICE), 2)
						.put(CustomItem.enhancedGlass, 1).build();
			case 1:
				return new MapBuilder().put(CustomItem.CircutBoard, 2).put(CustomItem.goldWire, 4)
						.put(new ItemStack(Material.REDSTONE), 64).put(new ItemStack(Material.ICE), 4)
						.put(CustomItem.enhancedGlass, 2).build();
			case 2:
				return new MapBuilder().put(CustomItem.CircutBoard, 3).put(CustomItem.goldWire, 6)
						.put(CustomItem.enhancedredstone, 1).put(new ItemStack(Material.ICE), 8).put(CustomItem.enhancedGlass, 1)
						.build();
			case 3:
				return new MapBuilder().put(CustomItem.enhancedCircutBoard, 1).put(CustomItem.goldWire, 8)
						.put(CustomItem.enhancedredstone, 2).put(new ItemStack(Material.PACKED_ICE), 2)
						.put(CustomItem.enhancedGlass, 5).build();
			case 4:
				return new MapBuilder().put(CustomItem.enhancedCircutBoard, 2).put(CustomItem.goldWire, 10)
						.put(CustomItem.enhancedredstone, 3).put(new ItemStack(Material.PACKED_ICE), 4)
						.put(CustomItem.enhancedGlass, 7).build();
			case 5:
				return new MapBuilder().put(CustomItem.enhancedCircutBoard, 3).put(CustomItem.goldWire, 12)
						.put(CustomItem.enhancedredstone, 4).put(new ItemStack(Material.PACKED_ICE), 8)
						.put(CustomItem.enhancedGlass, 8).build();
			default:
				break;
			}
		} else if (part instanceof Storage) {
//			return new MapBuilder().put(Material.ACACIA_BOAT, 5).build();
			switch (part.getTier()) {
			case 0:
				return new MapBuilder().put(Material.CHEST, 1).put(Material.IRON_BLOCK, 1).build();
			case 1:
				return new MapBuilder().put(Material.CHEST, 8).put(Material.IRON_BLOCK, 6).build();
			case 2:
				return new MapBuilder().put(Material.CHEST, 16).put(CustomItem.enhancedIron, 1).build();
			case 3:
				return new MapBuilder().put(Material.CHEST, 32).put(CustomItem.enhancedIron, 2).build();
			case 4:
				return new MapBuilder().put(CustomItem.enhancedChest, 6).put(CustomItem.enhancedIron, 3).build();
			case 5:
				return new MapBuilder().put(CustomItem.enhancedChest, 10).put(CustomItem.enhancedIron, 4).build();
			case 6:
				return new MapBuilder().put(CustomItem.enhancedChest, 16).put(CustomItem.enhancedIron, 5).build();
			default:
				break;
			}
		} else if (part instanceof Frostshield) {
			switch (part.getTier()) {
			case 0:
				return new MapBuilder().put(Material.IRON_BLOCK, 3).put(Material.PACKED_ICE, 4).build();
			case 1:
				return new MapBuilder().put(CustomItem.enhancedIron, 1).put(Material.BLUE_ICE, 1).build();
			case 2:
				return new MapBuilder().put(CustomItem.enhancedIron, 3).put(Material.BLUE_ICE, 4).build();
			case 3:
				return new MapBuilder().put(CustomItem.enhancedIron, 6).put(Material.BLUE_ICE, 8).build();
			case 4:
				return new MapBuilder().put(CustomItem.enhancedIron, 10).put(Material.BLUE_ICE, 16).build();
			default:
				break;
			}
		} else if (part instanceof Heatshield) {
			switch (part.getTier()) {
			case 0:
				return new MapBuilder().put(CustomItem.enhancedIron, 6).put(Material.MAGMA_BLOCK, 8).build();
			case 1:
				return new MapBuilder().put(CustomItem.enhancedIron, 10).put(Material.MAGMA_BLOCK, 16).build();
			default:
				break;
			}
		} else if (part instanceof Hullarmor) {
			switch (part.getTier()) {
			case 0:
				return new MapBuilder().put(CustomItem.enhancedIron, 1).build();
			case 1:
				return new MapBuilder().put(CustomItem.enhancedIron, 5).build();
			case 2:
				return new MapBuilder().put(CustomItem.enhancedIron, 9).build();
			default:
				break;
			}
		} else if (part instanceof WaterTank) {
			switch (part.getTier()) {
			case 0:
				return new MapBuilder().put(new ItemStack(Material.WATER_BUCKET), 3).put(CustomItem.enhancedGlass, 5).build();
			case 1:
				return new MapBuilder().put(new ItemStack(Material.WATER_BUCKET), 5).put(CustomItem.enhancedGlass, 10).build();
			case 2:
				return new MapBuilder().put(new ItemStack(Material.WATER_BUCKET), 10).put(CustomItem.enhancedGlass, 20).build();
			default:
				break;
			}
		}
		return new Object2IntOpenHashMap<ItemStack>();
	}

}
