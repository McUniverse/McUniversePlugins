package eu.mcuniverse.rocket.rocket;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Maps;

import eu.mcuniverse.rocket.util.ItemUtil;
import eu.mcuniverse.universeapi.rockets.Planet;
import lombok.AccessLevel;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

public class RocketPart {

	public interface Part {
		public ItemStack getItem();

		public int getTier();

		public int getMaxTier();

		public default boolean isAtLeast(Part part) {
			return getTier() >= part.getTier();
		}

		public default String getName() {
			if (getTier() == 0) {
				return "None";
			} else {
				return "Tier " + getTier();
			}
		}

		public default String getClassName() {
			return getClass().toString().split("\\$")[1];
		}
		
	}

	public enum Parts {
		AIR_TANK(1, ItemUtil.createSkinItem(Material.GLASS_BOTTLE, ChatColor.YELLOW + "Air Tank", 10000010)/*ItemUtil.createItem(Material.GLASS_BOTTLE, ChatColor.YELLOW + "Air Tank")*/),
		WATER_TANK(2, ItemUtil.createSkinItem(Material.GLASS_BOTTLE, ChatColor.YELLOW + "Water Tank", 10000020)/*ItemUtil.createWaterBottle(ChatColor.YELLOW + "Water Tank")*/),
//		ENERGY_TANK(3, ItemUtil.createItem(Material.SEA_PICKLE, ChatColor.YELLOW + "Energy Tank")),
		STORAGE(3, ItemUtil.createItem(Material.BARREL, ChatColor.YELLOW + "Storage")),
		HULL_ARMOR(4, ItemUtil.createItem(Material.IRON_BLOCK, ChatColor.YELLOW + "Hull Armor")),
		HEAT_SHIELD(5, ItemUtil.createItem(Material.MAGMA_BLOCK, ChatColor.YELLOW + "Heat Shield")),
		FROST_SHIELD(6, ItemUtil.createItem(Material.PACKED_ICE, ChatColor.YELLOW + "Frost Shield")),
		FUEL_TANK(7, ItemUtil.createItem(Material.CAULDRON, ChatColor.YELLOW + "Fuel Tank")),
		BOARDCOMPUTER(8, ItemUtil.createItem(Material.OBSERVER, ChatColor.YELLOW + "Boardcomputer"));
//		THRUSTER(9, ItemUtil.createItem(Material.HOPPER, ChatColor.YELLOW + "Thruster")),
//		SOLAR_PANEL(10, ItemUtil.createItem(Material.DAYLIGHT_DETECTOR, ChatColor.YELLOW + "Solarpanel"));

		private static final Map<Integer, RocketPart.Parts> BY_INDEX = Maps.newHashMap();

		static {
			for (RocketPart.Parts part : RocketPart.Parts.values()) {
				BY_INDEX.put(part.getIndex(), part);
			}
		}

		@Getter
		int index;
		@Getter
		ItemStack item;

		Parts(int index, ItemStack item) {
			this.index = index;
			this.item = item;
		}

		public static RocketPart.Parts getPart(int index) {
			return BY_INDEX.get(index);
		}
	}

	@Getter
	public enum AirTank implements Part {
		NONE(0),
		TIER_ONE(1);

		int tier;
		final int maxTier = 1;

		@Getter(value = AccessLevel.NONE)
		static final Map<Integer, AirTank> BY_TIER = Maps.newHashMap();

		static {
			for (RocketPart.AirTank x : RocketPart.AirTank.values()) {
				BY_TIER.put(x.getTier(), x);
			}
		}

		public static AirTank getByTier(int tier) {
			return BY_TIER.get(tier);
		}

		AirTank(int tier) {
			this.tier = tier;
		}

		@Override
		public String toString() {
			return getName();
//			if (super.toString().toLowerCase().contains("none")) {
//				return "None";
//			} else {
//				return WordUtils.capitalize(super.toString().toLowerCase().replace('_', ' '));
//			}
		}

		@Override
		public ItemStack getItem() {
			return RocketPart.Parts.AIR_TANK.getItem();
		}

	}

	@Getter
	public enum WaterTank implements Part {
		NONE(0, 0, null),
		TIER_ONE(1, 3, Arrays.asList(12, 13, 14)),
		TIER_TWO(2, 5, Arrays.asList(11, 12, 13, 14, 15)),
		TIER_THREE(3, 10, Arrays.asList(3, 4, 5, 12, 13, 14, 21, 22, 23));

		int tier;
		int bucketStorage;
		final int maxTier = 3;
		List<Integer> slotIndices;

		@Getter(value = AccessLevel.NONE)
		static final Map<Integer, WaterTank> BY_TIER = Maps.newHashMap();

		static {
			for (RocketPart.WaterTank x : RocketPart.WaterTank.values()) {
				BY_TIER.put(x.getTier(), x);
			}
		}

		public static WaterTank getByTier(int tier) {
			return BY_TIER.get(tier);
		}

		WaterTank(int tier, int bucketStorage, List<Integer> slotIndicies) {
			this.tier = tier;
			this.bucketStorage = bucketStorage;
			this.slotIndices = slotIndicies;
		}

		@Override
		public String toString() {
			return getName();
//		if (super.toString().toLowerCase().contains("none")) {
//			return "None";
//		} else {
//			return WordUtils.capitalize(super.toString().toLowerCase().replace('_', ' '));
//		}
		}

		@Override
		public ItemStack getItem() {
			return RocketPart.Parts.WATER_TANK.getItem();
		}
	}

	@Getter
	public enum Hullarmor implements Part {
		NONE(0, 0),
		TIER_ONE(1, 1),
		TIER_TWO(2, 3),
		TIER_THREE(3, 5);

		int tier;
		final int maxTier = 3;
		int maxCollisions;

		@Getter(value = AccessLevel.NONE)
		static final Map<Integer, Hullarmor> BY_TIER = Maps.newHashMap();

		static {
			for (RocketPart.Hullarmor x : RocketPart.Hullarmor.values()) {
				BY_TIER.put(x.getTier(), x);
			}
		}

		public static Hullarmor getByTier(int tier) {
			return BY_TIER.get(tier);
		}

		Hullarmor(int tier, int maxCollisions) {
			this.tier = tier;
			this.maxCollisions = maxCollisions;
		}

		@Override
		public String toString() {
			return getName();
//		if (super.toString().toLowerCase().contains("none")) {
//			return "None";
//		} else {
//			return WordUtils.capitalize(super.toString().toLowerCase().replace('_', ' '));
//		}
		}

		@Override
		public ItemStack getItem() {
			return RocketPart.Parts.HULL_ARMOR.getItem();
		}
	}

	@Getter
	public enum Heatshield implements Part {
		NONE(0, null),
		TIER_ONE(1, Planet.MERCURY),
		TIER_TWO(2, Planet.VENUS);

		int tier;
		final int maxTier = 2;
		Planet toFly;

		@Getter(value = AccessLevel.NONE)
		static final Map<Integer, Heatshield> BY_TIER = Maps.newHashMap();

		static {
			for (RocketPart.Heatshield x : RocketPart.Heatshield.values()) {
				BY_TIER.put(x.getTier(), x);
			}
		}

		public static Heatshield getByTier(int tier) {
			return BY_TIER.get(tier);
		}

		Heatshield(int tier, Planet toFly) {
			this.tier = tier;
			this.toFly = toFly;
		}

		@Override
		public String toString() {
			return getName();
//		if (super.toString().toLowerCase().contains("none")) {
//			return "None";
//		} else {
//			return WordUtils.capitalize(super.toString().toLowerCase().replace('_', ' '));
//		}
		}

		@Override
		public ItemStack getItem() {
			return RocketPart.Parts.HEAT_SHIELD.getItem();
		}
	}

	@Getter
	public enum Frostshield implements Part {
		NONE(0, null),
		TIER_ONE(1, Planet.MARS),
		TIER_TWO(2, Planet.JUPITER),
		TIER_THREE(3, Planet.SATURN),
		TIER_FOUR(4, Planet.URANUS),
		TIER_FIVE(5, Planet.NEPTUNE);

		int tier;
		final int maxTier = 5;
		Planet toFly;

		Frostshield(int tier, Planet toFly) {
			this.tier = tier;
			this.toFly = toFly;
		}

		@Getter(value = AccessLevel.NONE)
		static final Map<Integer, Frostshield> BY_TIER = Maps.newHashMap();

		static {
			for (RocketPart.Frostshield x : RocketPart.Frostshield.values()) {
				BY_TIER.put(x.getTier(), x);
			}
		}

		public static Frostshield getByTier(int tier) {
			return BY_TIER.get(tier);
		}

		@Override
		public String toString() {
			return getName();
//		if (super.toString().toLowerCase().contains("none")) {
//			return "None";
//		} else {
//			return WordUtils.capitalize(super.toString().toLowerCase().replace('_', ' '));
//		}
		}

		@Override
		public ItemStack getItem() {
			return RocketPart.Parts.FROST_SHIELD.getItem();
		}
	}

	@Getter
	public enum Storage implements Part {

		NONE(0, null),
		TIER_ONE(1, Arrays.asList(13)),
		TIER_TWO(2, Arrays.asList(12, 13, 14)),
		TIER_THREE(3, Arrays.asList(11, 12, 13, 14, 15)),
		TIER_FOUR(4, Arrays.asList(10, 11, 12, 13, 14, 15, 16)),
		TIER_FIVE(5, Arrays.asList(3, 4, 5, 12, 13, 14, 21, 22, 23)),
		TIER_SIX(6, Arrays.asList(3, 4, 5, 12, 13, 14, 21, 22, 23, 11, 15)),
		TIER_SEVEN(7, Arrays.asList(2, 3, 4, 5, 6, 11, 12, 13, 14, 15, 20, 21, 22, 23, 24));

		int tier;
		final int maxTier = 7;
		int invSize;
		List<Integer> slotIndices;

		Storage(int tier, List<Integer> slotIndices) {
			this.tier = tier;
			this.invSize = slotIndices == null ? 0 : slotIndices.size();
			this.slotIndices = slotIndices;
		}

		@Getter(value = AccessLevel.NONE)
		static final Map<Integer, Storage> BY_TIER = Maps.newHashMap();

		static {
			for (RocketPart.Storage x : RocketPart.Storage.values()) {
				BY_TIER.put(x.getTier(), x);
			}
		}

		public static Storage getByTier(int tier) {
			return BY_TIER.get(tier);
		}

		@Override
		public String toString() {
			return getName();
//		if (super.toString().toLowerCase().contains("none")) {
//			return "None";
//		} else {
//			return WordUtils.capitalize(super.toString().toLowerCase().replace('_', ' '));
//		}
		}

		@Override
		public ItemStack getItem() {
			return RocketPart.Parts.STORAGE.getItem();
		}

	}

	@Getter
	public enum Boardcomputer implements Part {
		NONE(0, new Planet[] {}),
		TIER_ONE(1, Planet.MOON),
		TIER_TWO(2, Planet.MARS),
		TIER_THREE(3, Planet.JUPITER, Planet.VENUS),
		TIER_FOUR(4, Planet.SATURN, Planet.MERCURY),
		TIER_FIVE(5, Planet.URANUS),
		TIER_SIX(6, Planet.NEPTUNE);

		int tier;
		final int maxTier = 6;
		Planet[] toFly;

		@Getter(value = AccessLevel.NONE)
		static final Map<Integer, Boardcomputer> BY_TIER = Maps.newHashMap();

		static {
			for (RocketPart.Boardcomputer x : RocketPart.Boardcomputer.values()) {
				BY_TIER.put(x.getTier(), x);
			}
		}

		public static Boardcomputer getByTier(int tier) {
			return BY_TIER.get(tier);
		}

		Boardcomputer(int tier, Planet... toFly) {
			this.tier = tier;
			this.toFly = toFly;
		}

		@Override
		public String toString() {
			return getName();
//		if (super.toString().toLowerCase().contains("none")) {
//			return "None";
//		} else {
//			return WordUtils.capitalize(super.toString().toLowerCase().replace('_', ' '));
//		}
		}

		@Override
		public ItemStack getItem() {
			return RocketPart.Parts.BOARDCOMPUTER.getItem();
		}
	}

	@Getter
	public enum FuelTank implements Part {
//		NONE(0, 0),
//		TIER_ONE(1, 9),
//		TIER_TWO(2, 9),
//		TIER_THREE(3, 18),
//		TIER_FOUR(4, 18),
//		TIER_FIVE(5, 27),
//		TIER_SIX(6, 27);
//
//		int fuelInvSize;
		
		NONE(0, 0),
		TIER_ONE(1, 1),
		TIER_TWO(2, 1.2),
		TIER_THREE(3, 1.5),
		TIER_FOUR(4, 2),
		TIER_FIVE(5, 2.5),
		TIER_SIX(6, 3);
		
		int tier;
		double slotsPerFuel;
		final int maxTier = 6;

		@Getter(value = AccessLevel.NONE)
		static final Map<Integer, FuelTank> BY_TIER = Maps.newHashMap();

		static {
			for (RocketPart.FuelTank x : RocketPart.FuelTank.values()) {
				BY_TIER.put(x.getTier(), x);
			}
		}

		public static FuelTank getByTier(int tier) {
			return BY_TIER.get(tier);
		}

		FuelTank(int tier, double slotsPerFuel) {
			this.tier = tier;
			this.slotsPerFuel = slotsPerFuel;
		}

		public int getRequiredBottels() {
			return (int) (18 / getSlotsPerFuel());
		}
		
		@Override
		public String toString() {
			return getName();
		}

		@Override
		public ItemStack getItem() {
			return RocketPart.Parts.FUEL_TANK.getItem();
		}
	}

}
